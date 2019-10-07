package korotchenko.financemanager.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import korotchenko.financemanager.R

abstract class BaseActivity : AppCompatActivity() {

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
        fragment: BaseFragment<out BasePresenter<out BaseView>>,
        container: Int = R.id.fragment_container,
        blockFragmentRepeat: Boolean = false,
        addInBackStack: Boolean = true,
        shouldAddOrReplace: Boolean = true
    ) {
        if(blockFragmentRepeat) {
            val oldFragment = supportFragmentManager.fragments.lastOrNull() as? BaseFragment<*>
            if(oldFragment?.TAG == fragment.TAG) {
                return
            }
        }
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