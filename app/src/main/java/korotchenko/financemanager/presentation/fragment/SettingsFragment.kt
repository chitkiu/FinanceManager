package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    override val layoutID: Int = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signOutButton.setOnClickListener {
            signOutFromGogle()
        }
    }

    private fun signOutFromGogle() {
        val signOut = googleSignInClient?.signOut()
        signOut?.addOnCompleteListener {
            if(!it.isSuccessful) {
                onSignOutError()
            }
        }
        signOut?.addOnSuccessListener {
            onSignOutSuccess()
        }
    }

    private fun onSignOutSuccess() {
        showFragment(
            SignInFragment.newInstance(),
            R.id.fragment_container,
            true
        )
    }

    private fun onSignOutError() {
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}
