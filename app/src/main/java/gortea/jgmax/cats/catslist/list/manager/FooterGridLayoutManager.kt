package gortea.jgmax.cats.catslist.list.manager

import androidx.recyclerview.widget.GridLayoutManager

interface FooterGridLayoutManager {
    var spanCount: Int
    fun getLayoutManager(): GridLayoutManager
    fun showFooter()
    fun hideFooter()
}
