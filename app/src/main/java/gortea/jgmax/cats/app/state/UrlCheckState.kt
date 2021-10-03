package gortea.jgmax.cats.app.state

import androidx.annotation.StringRes

sealed class UrlCheckState {
    object Default : UrlCheckState()
    class Success(val data: String) : UrlCheckState()
    class Fail(@StringRes val msg: Int) : UrlCheckState()
}