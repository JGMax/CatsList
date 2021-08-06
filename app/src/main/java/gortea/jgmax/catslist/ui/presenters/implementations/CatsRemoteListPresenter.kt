package gortea.jgmax.catslist.ui.presenters.implementations

import gortea.jgmax.catslist.data.remote.cats.api.CatsApi
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem
import gortea.jgmax.catslist.ui.presenters.CatsListPresenterRemote
import gortea.jgmax.catslist.ui.view.CatsRemoteListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatsRemoteListPresenter : MvpPresenter<CatsRemoteListView>(), CatsListPresenterRemote {
    private val catsList: MutableList<CatsListItem> = mutableListOf()

    override fun fetchCatsList(catsApi: CatsApi, limit: Int) {
        viewState.onNewDataRequest()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newList = catsApi.getCatsList(limit)
                catsList.addAll(newList)
                launch(Dispatchers.Main) {
                    viewState.onSuccessRequest()
                    viewState.updateList(catsList)
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    viewState.onErrorRequest(e.localizedMessage)
                }
            }
        }
    }

    override fun onCatsItemSelected(catsItem: CatsListItem) {
        // todo open fragment
    }

    override fun getList(): List<CatsListItem> = catsList.toList()
}