package korotchenko.financemanager.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import korotchenko.financemanager.R

abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() = if(supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }

    fun showFragment(
        fragment: BaseFragment,
        container: Int = R.id.fragment_container,
        addInBackStack: Boolean = true,
        shouldAddOrReplace: Boolean = true
    ) {
        val t = supportFragmentManager.beginTransaction()
        if (shouldAddOrReplace) {
            t.add(container, fragment)
        } else {
            t.replace(container, fragment)
        }
        if (addInBackStack) {
            t.addToBackStack(fragment.tag)
        }
        t.commit()
    }
}