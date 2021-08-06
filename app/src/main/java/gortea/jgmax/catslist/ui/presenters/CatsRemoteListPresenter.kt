package gortea.jgmax.catslist.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import gortea.jgmax.catslist.data.local.cats.constants.CATS_PAGE_LIMIT
import gortea.jgmax.catslist.data.remote.cats.api.CatsApi
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem
import gortea.jgmax.catslist.ui.view.CatsRemoteListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

@InjectViewState
class CatsRemoteListPresenter : MvpPresenter<CatsRemoteListView>() {
    private val catsList: MutableList<CatsListItem> = mutableListOf()

    fun fetchCatsList(catsApi: CatsApi) {
        viewState.onNewDataRequest()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newList = catsApi.getCatsList(CATS_PAGE_LIMIT)
                catsList.addAll(newList)
                launch(Dispatchers.Main) {
                    viewState.onSuccessRequest()
                    viewState.updateList(catsList)
                }
            } catch(e: Exception) {
                launch(Dispatchers.Main) {
                    viewState.onErrorRequest(e.localizedMessage)
                }
            }
        }
    }

    fun onCatsItemSelected(catsItem: CatsListItem) {
        // todo open fragment
    }
}