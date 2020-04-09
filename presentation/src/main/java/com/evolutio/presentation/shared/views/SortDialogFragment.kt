package com.evolutio.presentation.shared.views

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.evolutio.domain.service.ISharedPrefsService
import com.evolutio.domain.shared.FORK_SORT
import com.evolutio.domain.shared.SORT_KEY
import com.evolutio.domain.shared.STAR_SORT
import com.evolutio.domain.shared.UPDATED_SORT
import com.evolutio.presentation.databinding.DialogSortBinding
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.dialog_sort.*
import javax.inject.Inject

class SortDialogFragment : DialogFragment() {

    companion object {
        val TAG: String = SortDialogFragment::class.java.simpleName
    }

    private lateinit var binding: DialogSortBinding

    @Inject
    lateinit var sharedPrefsService: ISharedPrefsService

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = true
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = DialogSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (sharedPrefsService.getValue(SORT_KEY, STAR_SORT) as String) {
            STAR_SORT -> rbStarSort.isChecked = true
            FORK_SORT -> rbForksSort.isChecked = true
            UPDATED_SORT -> rbUpdatedSort.isChecked = true
        }

        rbStarSort.setOnClickListener {
            sharedPrefsService.saveValue(SORT_KEY, STAR_SORT)
            dialog?.dismiss()
        }

        rbForksSort.setOnClickListener {
            sharedPrefsService.saveValue(SORT_KEY, FORK_SORT)
            dialog?.dismiss()
        }

        rbUpdatedSort.setOnClickListener {
            sharedPrefsService.saveValue(SORT_KEY, UPDATED_SORT)
            dialog?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        if (dialog == null) return
        // Store access variables for window and blank point
        val window = dialog?.window
        val size = Point()
        // Store dimensions of the screen in `size`
        val display: Display
        if (window != null) {
            display = window.windowManager.defaultDisplay
            display.getSize(size)
            // Set the width of the dialog proportional to 70% of the screen width
            window.setLayout((size.x * 0.70).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
        }
    }
}