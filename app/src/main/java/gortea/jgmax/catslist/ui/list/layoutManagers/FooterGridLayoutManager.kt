package gortea.jgmax.catslist.ui.list.layoutManagers

import androidx.recyclerview.widget.GridLayoutManager

interface FooterGridLayoutManager {
    var spanCount: Int
    fun getLayoutManager(): GridLayoutManager
    fun showFooter()
    fun hideFooter()
}