package gortea.jgmax.cats.catslist.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gortea.jgmax.cats.R
import gortea.jgmax.cats.app.CATS_PAGE_LIMIT
import gortea.jgmax.cats.catslist.data.model.CatModel
import gortea.jgmax.cats.catslist.repository.ListRepository
import gortea.jgmax.cats.catslist.state.LoadingState
import gortea.jgmax.cats.navigation.coordinator.Coordinator
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CatsListViewModel @Inject constructor(
    private val coordinator: Coordinator,
    private val repository: ListRepository
) : ViewModel() {
    private val list = MutableLiveData<List<CatModel?>>()
    private val state = MutableLiveData<LoadingState>(LoadingState.Default)
    private val disposeBag = CompositeDisposable()

    fun isEmpty() = list.value.isNullOrEmpty()

    fun getListLiveData(): LiveData<List<CatModel?>> = list

    fun getStateLiveData(): LiveData<LoadingState> = state

    fun fetchCatsList() {
        if (state.value is LoadingState.Loading) return
        state.value = LoadingState.Loading
        val disposable = repository
            .getList(CATS_PAGE_LIMIT)
            .subscribe({
                val updateList = list.value?.toMutableList() ?: mutableListOf<CatModel?>(null)
                updateList.addAll(updateList.lastIndex, it)
                list.postValue(updateList.toList())
                state.postValue(LoadingState.Success)
            }, {
                state.postValue(LoadingState.Failed(R.string.connection_error))
            })
        disposeBag.add(disposable)
    }

    fun onItemClick(item: CatModel) {
        coordinator.navigateToFullView(item.url)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
        disposeBag.clear()
    }
}