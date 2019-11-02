package korotchenko.financemanager.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.presenters.SignInPresenter
import kotlinx.android.synthetic.main.fragment_sign_in.*

interface SignInView : BaseView {
    fun startActivityForResult(intent: Intent)

    fun successSignIn()
    fun failedSignIn()
}

class SignInFragment : BaseFragment<SignInPresenter>(), SignInView {

    override val layoutID: Int = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_in_button.setOnClickListener {
            presenter.signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN) {
            presenter.handleSignInResult(data)
        }
    }

    override fun startActivityForResult(intent: Intent) {
        startActivityForResult(intent, GOOGLE_SIGN_IN)
    }

    override fun successSignIn() {
        showFragment(
            fragment = MainFragment.newInstance(),
            container = R.id.fragment_container,
            addInBackStack = false,
            shouldAddOrReplace = false
        )
    }

    override fun failedSignIn() {
        sign_in_button.visibility = View.VISIBLE
    }

    companion object {
        private const val GOOGLE_SIGN_IN: Int = 432
        fun newInstance() = SignInFragment()
    }
}
