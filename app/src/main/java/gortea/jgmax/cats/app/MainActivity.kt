package gortea.jgmax.cats.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import gortea.jgmax.cats.R
import gortea.jgmax.cats.navigation.storage.NavStorage
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navStorage: NavStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setController()
    }

    private fun setController() {
        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navStorage.navController = host.navController
    }

    override fun onDestroy() {
        super.onDestroy()
        navStorage.navController = null
    }
}
