package korotchenko.financemanager.presentation.fragment


import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {

    override val layoutID: Int = R.layout.fragment_dashboard

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable += Maybe.fromCallable {
            GoogleSignIn.getLastSignedInAccount(context)
        }
            .filter { it != null }
            .map { it as GoogleSignInAccount }
            .map { mapToCredentialModel(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ account ->
                userInfoTextView.text = "${account.email} \n ${account.firstName} ${account.lastName}"
            }, ::handleError)
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}
