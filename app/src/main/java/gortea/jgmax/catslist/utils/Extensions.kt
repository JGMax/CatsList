package gortea.jgmax.catslist.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt


fun Int.toPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * (metrics.density)).roundToInt()
}

fun RecyclerView.Adapter<*>.hasPrevious(position: Int) = position > 0
fun RecyclerView.Adapter<*>.hasNext(position: Int) = position < itemCount - 1

