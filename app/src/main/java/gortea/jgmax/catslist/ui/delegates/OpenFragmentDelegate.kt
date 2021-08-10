package gortea.jgmax.catslist.ui.delegates

import androidx.fragment.app.Fragment

interface OpenFragmentDelegate {
    fun openFragment(fragment: Fragment, toBackStack: Boolean = true)
}