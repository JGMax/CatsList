package gortea.jgmax.cats.catslist.list.manager

import androidx.recyclerview.widget.GridLayoutManager

class FooterGridLayoutManagerImpl(
    private val layoutManager: GridLayoutManager
) : FooterGridLayoutManager {
    override var spanCount: Int
        get() = layoutManager.spanCount
        set(value) {
            layoutManager.spanCount = value
        }
    private var showFooter = true

    init {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (showFooter && position == layoutManager.itemCount - 1) {
                    layoutManager.spanCount
                } else {
                    1
                }
            }
        }
    }

    override fun getLayoutManager(): GridLayoutManager = layoutManager

    override fun showFooter() {
        showFooter = true
    }

    override fun hideFooter() {
        showFooter = false
    }
}