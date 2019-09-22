package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override val layoutID: Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav_view.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_dashboard -> {

                }
                R.id.navigation_transactions -> {

                }
                R.id.navigation_accounts -> {
                    showFragment(
                        fragment = AccountsFragment.newInstance(),
                        container = R.id.main_fragment_container,
                        addInBackStack = true
                    )
                }
                R.id.navigation_settings -> {

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}
