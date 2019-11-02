package korotchenko.financemanager.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.activity.MainActivity
import korotchenko.common.models.CredentialModel
import javax.inject.Inject

abstract class BaseFragment<P : BasePresenter<out BaseView>> : BaseMVPFragment<P>(), BaseView {

    @Inject
    lateinit var presenter: P

    override fun getViewPresenter(): P = presenter

    val TAG: String = javaClass.simpleName

    protected val mainActivity: MainActivity?
        get() {
            return activity as? MainActivity
        }

    abstract val layoutID: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutID, container, false)
    }

    protected fun getSignInCredentials(): CredentialModel? {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return account?.let {
            mapToCredentialModel(account)
        }
    }

    protected fun mapToCredentialModel(account: GoogleSignInAccount): CredentialModel {
        return CredentialModel(
            account.email ?: "",
            account.givenName ?: "",
            account.familyName ?: ""
        )
    }

    protected fun handleError(e: Throwable) {
        Log.d(TAG, e.localizedMessage)
    }

    protected fun showFragment(
        fragment: BaseFragment<out BasePresenter<out BaseView>>,
        container: Int = R.id.fragment_container,
        blockFragmentRepeat: Boolean = false,
        addInBackStack: Boolean = true,
        shouldAddOrReplace: Boolean = true
    ) {
        mainActivity?.showFragment(fragment, container, blockFragmentRepeat, addInBackStack, shouldAddOrReplace)
    }

}