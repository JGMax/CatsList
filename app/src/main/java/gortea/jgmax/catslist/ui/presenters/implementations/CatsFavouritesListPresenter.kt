package gortea.jgmax.catslist.ui.presenters.implementations

import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.favourites.room.dao.CatsListDao
import gortea.jgmax.catslist.data.local.cats.favourites.room.entity.toItem
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.ui.fragments.CatsDetailFragment
import gortea.jgmax.catslist.ui.presenters.CatsListPresenterLocal
import gortea.jgmax.catslist.ui.view.CatsFavouritesListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatsFavouritesListPresenter : MvpPresenter<CatsFavouritesListView>(), CatsListPresenterLocal {
    private val catsList: MutableList<CatsListItem?> = mutableListOf()
    private val disposeBag = CompositeDisposable()

    override fun fetchCatsList(catsDao: CatsListDao) {
        val disposable = catsDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list -> List(list.size) { list[it].toItem() } }
            .subscribe(
                {
                    catsList.clear()
                    catsList.addAll(it)
                    viewState.updateList(catsList.toList())
                }, {
                    viewState.onError(R.string.unknown_error)
                }
            )
        disposeBag.add(disposable)
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }

    override fun onCatsItemSelected(catsItem: CatsListItem) {
        viewState.openFragment(CatsDetailFragment.getInstance(catsItem))
    }

    override fun getList(): List<CatsListItem?> = catsList.toList()

    override fun setList(list: List<CatsListItem>) {
        catsList.clear()
        catsList.addAll(list)
    }
}