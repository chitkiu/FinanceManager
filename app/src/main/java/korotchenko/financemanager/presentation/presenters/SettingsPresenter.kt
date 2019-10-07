package korotchenko.financemanager.presentation.presenters

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.fragment.SettingsView
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    private val googleSignInClient: GoogleSignInClient
) : BasePresenter<SettingsView>() {

    fun signOut() {
        val signOut = googleSignInClient.signOut()
        signOut?.addOnCompleteListener {
            if(!it.isSuccessful) {
                view?.onSignOutError()
            }
        }
        signOut?.addOnSuccessListener {
            view?.onSuccessSignOut()
        }
    }

}