package gortea.jgmax.catslist.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.databinding.ActivityMainBinding
import gortea.jgmax.catslist.ui.fragments.CatsListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, CatsListFragment())
        transaction.commit()
    }
}