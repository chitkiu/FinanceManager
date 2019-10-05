package korotchenko.financemanager.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.wear.widget.SwipeDismissFrameLayout
import androidx.wear.widget.drawer.WearableNavigationDrawerView
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.adapters.MainNavigationAdapter
import korotchenko.financemanager.presentation.base.BaseActivity
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.fragment.AccountsFragment
import korotchenko.financemanager.presentation.fragment.DashboardFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(),
    WearableNavigationDrawerView.OnItemSelectedListener {

    private var currentItem: Int = 0

    override val layoutID: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        top_navigation_drawer.setAdapter(MainNavigationAdapter(this))
        top_navigation_drawer.addOnItemSelectedListener(this)
        top_navigation_drawer.controller.peekDrawer()
        savedInstanceState?.let { nonNullBundle ->
            currentItem = nonNullBundle.getInt(CURRENT_MENU_POSITION_KEY, 0)
        }
        top_navigation_drawer.addOnItemSelectedListener { currentItem = it }
        top_navigation_drawer.setCurrentItem(currentItem, false)
        onItemSelected(currentItem)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_MENU_POSITION_KEY, currentItem)
    }

    override fun onItemSelected(pos: Int) {
        when(pos) {
            DASHBOARD_MENU_POSITION -> {
                showMenuFragment(DashboardFragment.newInstance())
            }
            ACCOUNT_MENU_POSITION -> {
                showMenuFragment(AccountsFragment.newInstance())
            }
            else -> {
                Log.e(TAG, "onItemSelected pos $pos")
            }
        }
    }

    private fun showMenuFragment(fragment: BaseFragment) {
        showFragment(
            fragment = fragment,
            blockFragmentRepeat = true,
            addInBackStack = true,
            shouldAddOrReplace = false
        )
    }

    companion object {
        private const val DASHBOARD_MENU_POSITION = 0
        private const val ACCOUNT_MENU_POSITION = 2

        private const val CURRENT_MENU_POSITION_KEY = "curr_pos"
    }
}
