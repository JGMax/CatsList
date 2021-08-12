package gortea.jgmax.catslist.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface CatsDetailView : MvpView {
    fun <T> showError(message: T)
    fun onSuccessDownload()
    fun onStartFavourites()
    fun onSuccessFavourites()
    fun onStartDownload()
    fun checkContainsResult(contains: Boolean)
}