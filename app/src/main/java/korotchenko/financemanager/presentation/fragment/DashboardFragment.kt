package korotchenko.financemanager.presentation.fragment


import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.presenters.DashboardPresenter
import korotchenko.logic.models.CredentialModel
import kotlinx.android.synthetic.main.fragment_dashboard.*

interface DashboardView : BaseView{
    fun showCurrentUserAccount(credentialModel: CredentialModel)
}

class DashboardFragment : BaseFragment<DashboardPresenter>(), DashboardView {

    override val layoutID: Int = R.layout.fragment_dashboard

    override fun showCurrentUserAccount(credentialModel: CredentialModel) {
        userInfoTextView.text = "${credentialModel.email} \n ${credentialModel.firstName} ${credentialModel.lastName}"
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}
