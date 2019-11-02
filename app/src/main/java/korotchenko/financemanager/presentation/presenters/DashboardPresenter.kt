package korotchenko.financemanager.presentation.presenters

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.fragment.DashboardView
import korotchenko.common.models.CredentialModel
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val context: Context
) : BasePresenter<DashboardView>() {

    override fun onViewAttach(isFirstAttach: Boolean) {
        if(isFirstAttach) {
            Maybe.fromCallable {
                GoogleSignIn.getLastSignedInAccount(context)
            }
                .map { mapToCredentialModel(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe { account ->
                    view?.showCurrentUserAccount(account)
                }
        }
    }

    protected fun mapToCredentialModel(account: GoogleSignInAccount): CredentialModel {
        return CredentialModel(
            account.email ?: "",
            account.givenName ?: "",
            account.familyName ?: ""
        )
    }
}