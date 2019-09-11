package korotchenko.financemanager.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import io.reactivex.disposables.CompositeDisposable
import korotchenko.financemanager.R
import korotchenko.financemanager.activity.MainActivity
import korotchenko.logic.models.CredentialModel

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity?
        get() {
            return activity as? MainActivity
        }

    protected lateinit var googleSignInClient: GoogleSignInClient

    abstract val layoutID: Int

    protected val TAG: String = javaClass.simpleName

    protected lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutID, container, false)
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
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
        addInBackStack: Boolean = true
    ) {
        mainActivity?.showFragment(fragment, container, addInBackStack)
    }
}