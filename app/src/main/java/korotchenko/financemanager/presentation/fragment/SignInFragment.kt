package korotchenko.financemanager.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.logic.models.CredentialModel
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : BaseFragment() {

    override val layoutID: Int = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_in_button.setOnClickListener {
            signInByGoogle()
        }
        sign_out_button.setOnClickListener {
            signOutFromGogle()
        }
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable += Maybe.fromCallable {
            GoogleSignIn.getLastSignedInAccount(context)
        }
            .filter { it != null }
            .map { it as GoogleSignInAccount }
            .map { mapToCredentialModel(it)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ account ->
                onSignInSuccess(account)
            }, ::handleError)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    onSignInSuccess(mapToCredentialModel(account))
                } else {
                    onSignInError()
                }
            } catch (e: ApiException) {
                onSignInError()
            }
        }
    }

    private fun signInByGoogle() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
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

    private fun onSignInError() {
        sign_in_button.visibility = View.VISIBLE
        sign_in_text.visibility = View.GONE
        sign_in_credentials.visibility = View.GONE
        sign_out_button.visibility = View.GONE
    }

    private fun onSignInSuccess(credential: CredentialModel) {/*
        sign_in_button.visibility = View.GONE
        sign_in_text.visibility = View.VISIBLE
        sign_out_button.visibility = View.VISIBLE
        sign_in_credentials.visibility = View.VISIBLE
        sign_in_credentials.text = credential.toString()
        sign_out_button.visibility = View.VISIBLE*/
        showFragment(MainFragment.newInstance())
    }

    private fun onSignOutSuccess() {
        sign_in_button.visibility = View.VISIBLE
        sign_in_text.visibility = View.GONE
        sign_in_credentials.visibility = View.GONE
        sign_out_button.visibility = View.GONE
    }

    private fun onSignOutError() {
        sign_in_button.visibility = View.GONE
        sign_in_text.visibility = View.VISIBLE
        sign_out_button.visibility = View.VISIBLE
        sign_in_credentials.visibility = View.VISIBLE
        sign_in_credentials.text = getSignInCredentials()?.toString() ?: ""
        sign_out_button.visibility = View.VISIBLE
    }

    companion object {
        private const val GOOGLE_SIGN_IN: Int = 432
        fun newInstance() = SignInFragment()
    }
}
