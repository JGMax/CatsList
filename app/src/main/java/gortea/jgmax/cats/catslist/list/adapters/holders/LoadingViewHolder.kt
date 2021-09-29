package gortea.jgmax.cats.catslist.list.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cats.app.hide
import gortea.jgmax.cats.app.show
import gortea.jgmax.cats.catslist.list.adapters.delegate.LoadingClickDelegate
import gortea.jgmax.cats.catslist.state.LoadingState
import gortea.jgmax.cats.databinding.CatsListLoadingBinding

class LoadingViewHolder(
    private val binding: CatsListLoadingBinding,
    private val clickDelegate: LoadingClickDelegate?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(presenter: LoadingPresenter) {
        presenter.attachLoadingHolder(this)
    }

    fun setLoadingState(state: LoadingState) {
        binding.apply {
            when(state) {
                is LoadingState.Success -> {
                    loadingPB.hide()
                    tryAgainBtn.hide()
                }
                is LoadingState.Loading, LoadingState.Default -> {
                    loadingPB.show()
                    tryAgainBtn.hide()
                }
                is LoadingState.Failed -> {
                    loadingPB.hide()
                    tryAgainBtn.show()
                    tryAgainBtn.setOnClickListener {
                        clickDelegate?.onReloadClick()
                    }

                }
            }
        }
    }
}