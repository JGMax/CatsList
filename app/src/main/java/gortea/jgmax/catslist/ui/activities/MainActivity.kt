package gortea.jgmax.catslist.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.ui.delegates.OpenFragmentDelegate
import gortea.jgmax.catslist.ui.fragments.CatsRemoteListFragment

class MainActivity : AppCompatActivity(), OpenFragmentDelegate {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openFragment(CatsRemoteListFragment(), false)
        }
    }

    override fun openFragment(fragment: Fragment, toBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if (toBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}