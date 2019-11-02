package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.presenters.SettingsPresenter
import kotlinx.android.synthetic.main.fragment_settings.*

interface SettingsView : BaseView {
    fun onSuccessSignOut()
    fun onSignOutError()
}

class SettingsFragment : BaseFragment<SettingsPresenter>(), SettingsView {

    override val layoutID: Int = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signOutButton.setOnClickListener {
            presenter.signOut()
        }
    }

    override fun onSuccessSignOut() {
        showFragment(
            fragment = SignInFragment.newInstance(),
            container = R.id.fragment_container,
            addInBackStack = false,
            shouldAddOrReplace = false
        )
    }

    override fun onSignOutError() { }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}
