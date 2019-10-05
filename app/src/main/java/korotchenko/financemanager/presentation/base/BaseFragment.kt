package korotchenko.financemanager.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.activity.MainActivity
import korotchenko.logic.models.CredentialModel

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity?
        get() {
            return activity as? MainActivity
        }

    protected val googleSignInClient: GoogleSignInClient?
        get() = mainActivity?.googleSignInClient

    abstract val layoutID: Int

    val TAG: String = javaClass.simpleName

    protected lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        compositeDisposable = CompositeDisposable()
        return inflater.inflate(layoutID, container, false)
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
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
        fragment: BaseFragment,
        container: Int = R.id.fragment_container,
        addInBackStack: Boolean = true,
        shouldAddOrReplace: Boolean = true
    ) {
        mainActivity?.showFragment(fragment, container, addInBackStack, shouldAddOrReplace)
    }
}