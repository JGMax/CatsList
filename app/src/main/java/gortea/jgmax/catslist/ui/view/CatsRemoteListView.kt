package gortea.jgmax.catslist.ui.view

import android.content.Intent
import gortea.jgmax.catslist.data.local.cats.model.CatsListLocalItem
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem
import moxy.MvpAppCompatFragment
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface CatsRemoteListView : MvpView {
    fun onStartRequest()
    fun updateList(items: List<CatsListLocalItem?>?)
    fun openActivity(intent: Intent?)
    fun openFragment(fragment: MvpAppCompatFragment)
    fun onSuccessRequest()
    fun<T> onErrorRequest(message: T)
}