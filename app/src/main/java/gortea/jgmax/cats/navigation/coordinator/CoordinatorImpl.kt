package gortea.jgmax.cats.navigation.coordinator

import androidx.navigation.NavController
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

    }

    override fun navigateToFullView(url: String) {

    }
}