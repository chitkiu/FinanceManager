package korotchenko.financemanager.presentation.presenters

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.fragment.SignInFragment
import korotchenko.financemanager.presentation.fragment.SignInView
import javax.inject.Inject

class SignInPresenter @Inject constructor(
    private val context: Context,
    private val googleSignInClient: GoogleSignInClient
) : BasePresenter<SignInView>() {

    override fun onViewAttach(isFirstAttach: Boolean) {
        if (isFirstAttach) {
            Maybe.fromCallable {
                GoogleSignIn.getLastSignedInAccount(context)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe {
                    view?.successSignIn()
                }
        }
    }

    fun signIn() {
        Log.e("SignInPresenter", "signIn")
        view?.startActivityForResult(googleSignInClient.signInIntent)
    }

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            task
                .getResult(ApiException::class.java)
                ?.let {
                    Log.e("SignInPresenter", "handleSignInResult getResult")
                    view!!.successSignIn()
                }
                ?: view?.failedSignIn()
        } catch (e: ApiException) {
            Log.e("SignInPresenter", "handleSignInResult e $e")
            view?.failedSignIn()
        }
    }
}