package gortea.jgmax.cats.app

import android.content.Context
import android.util.TypedValue
import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Int.toPx(context: Context): Int {
    return TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(),
            context.resources.displayMetrics
        ).toInt()
}
