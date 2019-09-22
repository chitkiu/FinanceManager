package korotchenko.financemanager.presentation.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import korotchenko.logic.models.CredentialModel


abstract class BaseActivity : WearableActivity() {

    private var googleApiClient: GoogleApiClient? = null

    abstract val layoutID: Int

    protected val TAG: String = javaClass.simpleName

    protected lateinit var compositeDisposable: CompositeDisposable

    abstract fun onSignInError()

    abstract fun onSignInSuccess(credential: CredentialModel)

    abstract fun onSignOutSuccess()

    abstract fun onSignOutError()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(layoutID)
        setAmbientEnabled()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutID)
        setAmbientEnabled()
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
        initGoogleAuth()
        if (googleApiClient != null && !googleApiClient!!.isConnected) {
            googleApiClient?.connect()
        }
    }

    override fun onPause() {
        if (googleApiClient != null && googleApiClient!!.isConnected) {
            googleApiClient?.disconnect()
        }
        compositeDisposable.dispose()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN) {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data))
        }
    }

    protected fun signInByGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    protected fun signOutFromGoogle() {
        val signOut = Auth.GoogleSignInApi.signOut(googleApiClient)
        signOut.setResultCallback {
            if(it.isSuccess) {
                onSignOutSuccess()
            } else {
                onSignOutError()
            }
        }
    }

    protected fun handleError(e: Throwable) {
        Log.d(TAG, e.localizedMessage)
    }

    private fun initGoogleAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        compositeDisposable += Single.fromCallable {
            val pendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient)
            pendingResult.get()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({signInResult ->
                handleSignInResult(signInResult)
            }, ::handleError)

    }

    private fun handleSignInResult(result: GoogleSignInResult?) {
        if(result == null || result.signInAccount == null) {
            onSignInError()
        } else {
            val account = result.signInAccount!!
            onSignInSuccess(mapToCredentialModel(account))
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