package gortea.jgmax.catslist.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt


fun Int.toPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * (metrics.density)).roundToInt()
}

fun RecyclerView.Adapter<*>.hasPrevious(parent: RecyclerView, position: Int): Boolean {
    return if (parent.layoutManager == GridLayoutManager::class) {
        position > 1
    } else {
        position > 0
    }
}
fun RecyclerView.Adapter<*>.hasNext(parent: RecyclerView, position: Int): Boolean {
    return if (parent.layoutManager == GridLayoutManager::class && itemCount % 2 == 0) {
        position < itemCount - 2
    } else {
        position < itemCount - 1
    }
}
