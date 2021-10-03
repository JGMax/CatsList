package gortea.jgmax.cats.navigation.coordinator

import android.os.Bundle
import androidx.navigation.NavController
import gortea.jgmax.cats.R
import gortea.jgmax.cats.fullview.fragment.FullViewViewModel.Companion.IMAGE_URL_ARG
import gortea.jgmax.cats.navigation.storage.NavStorage
import javax.inject.Inject

class CoordinatorImpl @Inject constructor(
    private val navStorage: NavStorage
) : Coordinator {
    private val navController: NavController
        get() = requireNotNull(navStorage.navController)

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun navigateToList() {
        navController.navigate(R.id.listFragment)
    }

    override fun navigateToFullView(url: String) {
        val args = Bundle()
        args.putString(IMAGE_URL_ARG, url)
        navController.navigate(R.id.fullViewFragment, args)
    }
}