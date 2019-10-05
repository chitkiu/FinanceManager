package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.util.Log
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override val layoutID: Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav_view.setOnNavigationItemSelectedListener { menuItem ->
            showFragmentByItemId(menuItem.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        nav_view.selectedItemId = savedInstanceState?.getInt(SELECTED_ITEM_ID_KEY, R.id.navigation_dashboard) ?: R.id.navigation_dashboard
        Log.e(TAG, "MainFragment selectedItemId ${nav_view.selectedItemId}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.e(TAG, "MainFragment onSaveInstanceState selectedItemId ${nav_view.selectedItemId}")
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ITEM_ID_KEY, nav_view.selectedItemId)
    }

    private fun showFragmentByItemId(itemId: Int) {
        when(itemId) {
            R.id.navigation_dashboard -> {
                showFragment(
                    DashboardFragment.newInstance()
                )
            }
            R.id.navigation_transactions -> {
                showFragment(
                    TransactionFragment.newInstance()
                )
            }
            R.id.navigation_accounts -> {
                showFragment(
                    AccountsFragment.newInstance()
                )
            }
            R.id.navigation_settings -> {
                showFragment(
                    SettingsFragment.newInstance()
                )
            }
        }
    }

    private fun showFragment(fragment: BaseFragment) {
        showFragment(
            fragment = fragment,
            container = R.id.main_fragment_container,
            addInBackStack = false,
            shouldAddOrReplace = false
        )
    }

    companion object {
        private const val SELECTED_ITEM_ID_KEY: String = "selected_item"
        fun newInstance(): MainFragment = MainFragment()
    }
}
