package com.evolutio.presentation.ext

import android.content.Context
import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.evolutio.presentation.R

fun Context.generateDividerDecoration(
    insetLeft: Int = 0,
    insetTop: Int = 0,
    insetRight: Int = 0,
    insetBottom: Int = 0
): RecyclerView.ItemDecoration {
    val attrs = intArrayOf(android.R.attr.listDivider)
    val a = this.obtainStyledAttributes(attrs)
    val divider = a.getDrawable(0)

    val insetDivider = InsetDrawable(
        divider,
        insetLeft,
        insetTop,
        insetRight,
        insetBottom
    )
    a.recycle()

    val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    decoration.setDrawable(insetDivider)
    return decoration
}

fun Context.doesDeviceHaveABrowser(url: String): Boolean {
    val webAddress: Uri = Uri.parse(url)
    val intentWeb = Intent(Intent.ACTION_VIEW, webAddress)
    return intentWeb.resolveActivity(this.packageManager) != null
}

