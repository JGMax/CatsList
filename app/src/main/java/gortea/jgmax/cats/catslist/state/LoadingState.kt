package gortea.jgmax.cats.catslist.state

import androidx.annotation.StringRes

sealed class LoadingState {
    object Success : LoadingState()
    object Loading : LoadingState()
    class Failed(@StringRes val error: Int) : LoadingState()
    object Default : LoadingState()
}
