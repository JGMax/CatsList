package gortea.jgmax.cats.catslist.list.adapters.holders

import gortea.jgmax.cats.app.state.LoadingState

class LoadingPresenter {
    private var currentLoadingHolder: LoadingViewHolder? = null

    fun attachLoadingHolder(loadingViewHolder: LoadingViewHolder) {
        currentLoadingHolder = loadingViewHolder
    }

    fun detachLoadingHolder() {
        currentLoadingHolder = null
    }

    fun setLoadingState(state: LoadingState) {
        notifyLoadingHolder(state)
    }

    private fun notifyLoadingHolder(state: LoadingState) {
        currentLoadingHolder?.setLoadingState(state)
    }
}