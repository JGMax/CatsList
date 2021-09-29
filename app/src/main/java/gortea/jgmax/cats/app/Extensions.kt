package gortea.jgmax.cats.app

import android.content.Context
import android.view.View
import kotlin.math.roundToInt

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Int.toPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * (metrics.density)).roundToInt()
}