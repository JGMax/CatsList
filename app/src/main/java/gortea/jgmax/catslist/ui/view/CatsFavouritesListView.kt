package gortea.jgmax.catslist.ui.view

import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import moxy.MvpAppCompatFragment
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface CatsFavouritesListView : MvpView {
    fun updateList(items: List<CatsListItem?>?)
    fun openFragment(fragment: MvpAppCompatFragment)
    fun <T> onError(message: T)
}