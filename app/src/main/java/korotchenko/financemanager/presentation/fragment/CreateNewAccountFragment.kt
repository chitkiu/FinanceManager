package korotchenko.financemanager.presentation.fragment

import android.os.Bundle
import android.view.View
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.presenters.CreateNewAccountPresenter
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.fragment_create_new_account.*

interface CreateNewAccountView : BaseView {
    fun showBalanceError(text: CharSequence?)
    fun showNameError(text: CharSequence?)

    fun goBackToMainScreen()
}

class CreateNewAccountFragment : BaseFragment<CreateNewAccountPresenter>(), CreateNewAccountView {

    override val layoutID: Int = R.layout.fragment_create_new_account

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_save_account.setOnClickListener {
            presenter.createNewAccount(buildModel())
        }
    }

    override fun goBackToMainScreen() {
        activity?.onBackPressed()
    }

    override fun showBalanceError(text: CharSequence?) {
        start_balance_input_layout.error = text
    }

    override fun showNameError(text: CharSequence?) {
        name_input_layout.error = text
    }

    private fun buildModel(): AccountModel {
        return AccountModel(
            id = System.currentTimeMillis(),
            name = name_edittext.text.toString(),
            description = description_edittext.text.toString(),
            balance = start_balance_edittext.text.toString().toDoubleOrNull() ?: EMPTY_BALANCE_VALUE
        )
    }

    companion object {
        val EMPTY_BALANCE_VALUE = Double.MIN_VALUE
        fun newInstance() = CreateNewAccountFragment()
    }
}