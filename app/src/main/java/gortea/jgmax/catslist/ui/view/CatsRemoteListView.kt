package gortea.jgmax.catslist.ui.view

import android.content.Intent
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface CatsRemoteListView : MvpView {
    fun onNewDataRequest()
    fun updateList(items: List<CatsListItem>?)
    fun openActivity(intent: Intent?)
    fun openFragment(fragment: MvpAppCompatFragment)
    fun onSuccessRequest()
    fun<T> onErrorRequest(message: T)
}