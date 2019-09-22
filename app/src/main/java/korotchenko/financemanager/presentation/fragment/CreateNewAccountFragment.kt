package korotchenko.financemanager.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import korotchenko.financemanager.data.AccountDataRepository
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.fragment_create_new_account.*
import javax.inject.Inject

class CreateNewAccountFragment : BaseFragment() {

    @Inject
    lateinit var accountDataRepository: AccountDataRepository

    override val layoutID: Int = R.layout.fragment_create_new_account

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_save_account.setOnClickListener {
            handleUpdate(buildModel())
            if(!start_balance_input_layout.error.isNullOrBlank()) {
                return@setOnClickListener
            }
            if(!name_input_layout.error.isNullOrBlank()) {
                return@setOnClickListener
            }
            accountDataRepository.saveAccount(buildModel())
            activity?.onBackPressed()
        }

        start_balance_edittext.addTextChangedListener(afterTextChanged = {
            handleUpdate(buildModel())
        })
        name_edittext.addTextChangedListener(afterTextChanged = {
            handleUpdate(buildModel())
        })
    }

    private fun handleUpdate(model: AccountModel) {
        start_balance_input_layout.error = null
        name_input_layout.error = null
        when {
            model.name.isBlank() -> name_input_layout.error = "Cannot be blank!"
        }
    }

    private fun buildModel(): AccountModel {
        return AccountModel(
            id = System.currentTimeMillis(),
            name = name_edittext.text.toString(),
            description = description_edittext.text.toString(),
            balance = start_balance_edittext.text.toString().toDoubleOrNull() ?: 0.0
        )
    }

    companion object {
        fun newInstance() = CreateNewAccountFragment()
    }
}