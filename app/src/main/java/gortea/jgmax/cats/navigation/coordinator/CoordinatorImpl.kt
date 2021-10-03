package gortea.jgmax.cats.navigation.coordinator

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import gortea.jgmax.cats.R
import gortea.jgmax.cats.fullview.fragment.FullViewFragment.Companion.IMAGE_URL_ARG
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

    override fun navigateToFullView(url: String, leftHand: Boolean) {
        val args = Bundle()
        args.putString(IMAGE_URL_ARG, url)
        val navOptions = navOptions {
            anim {
                if (leftHand) {
                    enter = R.animator.card_flip_right_in
                    exit = R.animator.card_flip_right_out
                    popEnter = R.animator.card_flip_left_in
                    popExit = R.animator.card_flip_left_out
                } else {
                    enter = R.animator.card_flip_left_in
                    exit = R.animator.card_flip_left_out
                    popEnter = R.animator.card_flip_right_in
                    popExit = R.animator.card_flip_right_out
                }
            }
        }
        navController.navigate(R.id.fullViewFragment, args, navOptions)
    }
}
