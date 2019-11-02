package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.presenters.MainPresenter
import kotlinx.android.synthetic.main.fragment_main.*

interface MainView: BaseView {

}

class MainFragment : BaseFragment<MainPresenter>(), MainView {

    override val layoutID: Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navView.setOnNavigationItemSelectedListener { menuItem ->
            showFragmentByItemId(menuItem.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        navView.selectedItemId = savedInstanceState?.getInt(SELECTED_ITEM_ID_KEY, R.id.navigation_dashboard) ?: R.id.navigation_dashboard
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ITEM_ID_KEY, navView.selectedItemId)
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
                    TransactionsFragment.newInstance()
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

    private fun showFragment(fragment: BaseFragment<out BasePresenter<out BaseView>>) {
        showFragment(
            fragment = fragment,
            container = R.id.main_fragment_container,
            blockFragmentRepeat = true,
            addInBackStack = false,
            shouldAddOrReplace = false
        )
    }

    companion object {
        private const val SELECTED_ITEM_ID_KEY: String = "selected_item"
        fun newInstance(): MainFragment = MainFragment()
    }
}
