package com.evolutio.presentation.shared.views

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.evolutio.presentation.R

class CircleRectView : AppCompatImageView {
    private var circleRadius = 0
    private var cornerRadius = 0f
    private val eps = 0.001f
    private var bitmapRect: RectF? = null
    private var clipPath: Path? = null

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val a =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CircleRectView, 0, 0)
        init(a)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        val a = context.theme
            .obtainStyledAttributes(attrs, R.styleable.CircleRectView, defStyleAttr, 0)
        init(a)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr) {
        val a = context.theme
            .obtainStyledAttributes(attrs, R.styleable.CircleRectView, defStyleAttr, defStyleRes)
        init(a)
    }

    private fun init(a: TypedArray) {
        if (a.hasValue(R.styleable.CircleRectView_circleRadius)) {
            circleRadius = a.getDimensionPixelSize(R.styleable.CircleRectView_circleRadius, 0)
            cornerRadius = circleRadius.toFloat()
        }
        clipPath = Path()
        a.recycle()
    }

    fun animator(rectHeight: Int, rectWidth: Int): Animator {
        return animator(measuredHeight, measuredWidth, rectHeight, rectWidth)
    }

    fun animator(
        startHeight: Int,
        startWidth: Int,
        endHeight: Int,
        endWidth: Int
    ): Animator {
        val animatorSet = AnimatorSet()
        Log.d(
            CircleRectView::class.java.simpleName, "startHeight =" + startHeight
                    + ", startWidth =" + startWidth
                    + ", endHeight = " + endHeight
                    + " endWidth =" + endWidth
        )
        val heightAnimator = ValueAnimator.ofInt(startHeight, endHeight)
        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth)
        heightAnimator.addUpdateListener { valueAnimator: ValueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.height = `val`
            Log.d(
                CircleRectView::class.java.simpleName,
                "height updated =$`val`"
            )
            setLayoutParams(layoutParams)
            requestLayoutSupport()
        }
        widthAnimator.addUpdateListener { valueAnimator: ValueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.width = `val`
            setLayoutParams(layoutParams)
            requestLayoutSupport()
        }
        val radiusAnimator: ValueAnimator
        radiusAnimator = if (startWidth < endWidth) {
            ValueAnimator.ofFloat(circleRadius.toFloat(), 0f)
        } else {
            ValueAnimator.ofFloat(cornerRadius, circleRadius.toFloat())
        }
        radiusAnimator.interpolator = AccelerateInterpolator()
        radiusAnimator.addUpdateListener { animator: ValueAnimator ->
            cornerRadius = animator.animatedValue as Float
        }
        animatorSet.playTogether(heightAnimator, widthAnimator, radiusAnimator)
        return animatorSet
    }

    /**
     * this needed because of that somehow [.onSizeChanged] NOT CALLED when requestLayout while activity transition end is running
     */
    private fun requestLayoutSupport() {
        val parent = parent as View
        val widthSpec = MeasureSpec.makeMeasureSpec(
            parent.width,
            MeasureSpec.EXACTLY
        )
        val heightSpec = MeasureSpec.makeMeasureSpec(
            parent.height,
            MeasureSpec.EXACTLY
        )
        parent.measure(widthSpec, heightSpec)
        parent.layout(parent.left, parent.top, parent.right, parent.bottom)
    }

    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //This event-method provides the real dimensions of this custom view.

//        Log.d("size changed", "w = " + w + " h = " + h);
        bitmapRect = RectF(0.0f, 0.0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable ?: return
        if (width == 0 || height == 0) {
            return
        }
        clipPath!!.reset()
        clipPath!!.addRoundRect(
            bitmapRect,
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        canvas.clipPath(clipPath!!)
        super.onDraw(canvas)
    }

    companion object {
        private val BITMAP_CONFIG =
            Bitmap.Config.ARGB_8888
    }
}