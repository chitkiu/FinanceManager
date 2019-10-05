package korotchenko.financemanager.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseActivity
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.activity_account_details.*

class AccountDetailsActivity : BaseActivity() {
    override val layoutID: Int = R.layout.activity_account_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accountModel = intent.getSerializableExtra(ACCOUNT_KEY) as? AccountModel
        accountModel?.let(::loadAccountModel)
    }

    private fun loadAccountModel(accountModel: AccountModel) {
        nameTextView.text = accountModel.name
        balanceTextView.text = String.format("%.2f %s", accountModel.balance, MONEY_SYMBOL)

        descriptionHintTextView.visibility = if(accountModel.description.isBlank()) View.GONE else View.VISIBLE
        descriptionTextView.visibility = if(accountModel.description.isBlank()) View.GONE else View.VISIBLE
        descriptionTextView.text = accountModel.description
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        const val ACCOUNT_KEY = "account"
    }
}
