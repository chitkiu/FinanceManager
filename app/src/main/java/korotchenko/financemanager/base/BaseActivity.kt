package korotchenko.financemanager.base

import androidx.appcompat.app.AppCompatActivity
import korotchenko.financemanager.R

abstract class BaseActivity : AppCompatActivity(){

    fun showFragment(
        fragment: BaseFragment,
        container: Int = R.id.fragment_container,
        addInBackStack: Boolean = true
    ) {
        val t = supportFragmentManager.beginTransaction()
        t.replace(container, fragment)
        if(addInBackStack) {
            t.addToBackStack(fragment.tag)
        }
        t.commit()
    }
}