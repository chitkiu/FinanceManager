package korotchenko.financemanager.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import korotchenko.financemanager.R

abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

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