package korotchenko.financemanager.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.wear.widget.SwipeDismissFrameLayout
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.activity_account_details.*

//TODO: Make inner view scrollable
class AccountDetailFragment: BaseFragment() {

    override val layoutID: Int = R.layout.activity_account_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accountModel = arguments?.getSerializable(ACCOUNT_KEY) as? AccountModel
        accountModel?.let(::loadAccountModel)
        top_drawer_content.addCallback(callback)
    }

    private fun loadAccountModel(accountModel: AccountModel) {
        nameTextView.text = accountModel.name
        balanceTextView.text = String.format("%.2f %s", accountModel.balance, MONEY_SYMBOL)

        descriptionHintTextView.visibility = if(accountModel.description.isBlank()) View.GONE else View.VISIBLE
        descriptionTextView.visibility = if(accountModel.description.isBlank()) View.GONE else View.VISIBLE
        descriptionTextView.text = accountModel.description
    }

    private val callback = object : SwipeDismissFrameLayout.Callback() {
        override fun onDismissed(layout: SwipeDismissFrameLayout) {
            hideFragment(this@AccountDetailFragment)
        }
    }

    companion object {
        private const val ACCOUNT_KEY = "account"
        private const val MONEY_SYMBOL = "₴"

        fun newInstance(accountModel: AccountModel): AccountDetailFragment {
            val fragment = AccountDetailFragment()
            if(fragment.arguments == null) {
                fragment.arguments = Bundle()
            }
            fragment.arguments?.putSerializable(ACCOUNT_KEY, accountModel)
            return fragment
        }
    }
}