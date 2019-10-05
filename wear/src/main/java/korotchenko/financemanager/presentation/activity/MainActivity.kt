package korotchenko.financemanager.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseActivity
import korotchenko.logic.models.CredentialModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override val layoutID: Int = R.layout.activity_main

    private var googleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sign_in_button.setOnClickListener {
            signInByGoogle()
        }
        sign_out_button.setOnClickListener {
            signOutFromGoogle()
        }
    }

    override fun onResume() {
        super.onResume()
        initGoogleAuth()
        googleApiClient?.connect()
    }

    override fun onPause() {
        googleApiClient?.disconnect()
        super.onPause()
    }

    private fun onSignInError() {
        sign_in_button.visibility = View.VISIBLE
        sign_in_text.visibility = View.GONE
        sign_in_credentials.visibility = View.GONE
        sign_out_button.visibility = View.GONE
    }

    private fun onSignInSuccess(credential: CredentialModel) {
        val intent = Intent(this, AccountsActivity::class.java)
        startActivity(intent)
        googleApiClient?.disconnect()
        finish()
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
        sign_in_credentials.visibility = View.VISIBLE
        sign_out_button.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN) {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data))
        }
    }

    private fun signInByGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent,
            GOOGLE_SIGN_IN
        )
    }

    private fun signOutFromGoogle() {
        Auth
            .GoogleSignInApi
            .signOut(googleApiClient)
            .setResultCallback {
                if (it.isSuccess) {
                    onSignOutSuccess()
                } else {
                    onSignOutError()
                }
            }
    }

    private fun initGoogleAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        Auth.GoogleSignInApi
            .silentSignIn(googleApiClient)
            .setResultCallback(::handleSignInResult)
    }

    private fun handleSignInResult(result: GoogleSignInResult?) {
        if(result == null || result.signInAccount == null) {
            onSignInError()
        } else {
            result
                .signInAccount
                ?.let(::mapToCredentialModel)
                ?.let(::onSignInSuccess)
        }
    }

    private fun mapToCredentialModel(account: GoogleSignInAccount): CredentialModel {
        return CredentialModel(
            account.email ?: "",
            account.givenName ?: "",
            account.familyName ?: ""
        )
    }

    companion object {
        private const val GOOGLE_SIGN_IN: Int = 432
    }
}
