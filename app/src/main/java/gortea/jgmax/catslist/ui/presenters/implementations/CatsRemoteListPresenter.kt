package gortea.jgmax.catslist.ui.presenters.implementations

import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.data.remote.cats.api.CatsApi
import gortea.jgmax.catslist.ui.fragments.CatsDetailFragment
import gortea.jgmax.catslist.ui.presenters.CatsListPresenterRemote
import gortea.jgmax.catslist.ui.view.CatsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatsRemoteListPresenter : MvpPresenter<CatsListView>(), CatsListPresenterRemote {
    private val catsList: MutableList<CatsListItem?> = mutableListOf()
    private val compositeBag = CompositeDisposable()

    override fun fetchCatsList(catsApi: CatsApi, limit: Int) {
        viewState.onStartRequest()
        val singleRxDisposable: Disposable = catsApi.getCatsList(limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list -> List(list.size) { CatsListItem(list[it].id, list[it].url) } }
            .subscribe(
                { newList ->
                    if (catsList.isEmpty()) {
                        catsList.add(null)
                    }
                    catsList.addAll(catsList.lastIndex, newList)
                    viewState.updateList(catsList)
                    viewState.onSuccessRequest()
                },
                {
                    viewState.onErrorRequest(R.string.connection_error)
                    it.printStackTrace()
                }
            )
        compositeBag.add(singleRxDisposable)
    }

    override fun onCatsItemSelected(catsItem: CatsListItem) {
        viewState.openFragment(CatsDetailFragment.getInstance(catsItem))
    }

    override fun getList(): List<CatsListItem?> = catsList.toList()
    override fun setList(list: List<CatsListItem>) {
        catsList.clear()
        catsList.addAll(list)
    }

    override fun onDestroy() {
        compositeBag.dispose()
        super.onDestroy()
    }
}