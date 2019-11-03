package korotchenko.financemanager.presentation.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import korotchenko.common.models.TransactionModel
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseFragment
import korotchenko.financemanager.presentation.base.BaseView
import korotchenko.financemanager.presentation.presenters.AddTransactionPresenter
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import org.threeten.bp.LocalDate

interface AddTransactionView : BaseView {
    fun showDate(lastSelectedDate: LocalDate)

    fun openDateSelectedDialog(lastSelectedDate: LocalDate, listener: (year: Int, month: Int, dayOfMonth: Int) -> Unit)
}

class AddTransactionFragment : BaseFragment<AddTransactionPresenter>(), AddTransactionView {

    override val layoutID: Int = R.layout.fragment_add_transaction

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreState(savedInstanceState)

        fabAddTransaction.setOnClickListener {
            presenter.addTransaction(buildModel())
        }

        transactionDateSelector.setOnClickListener {
            presenter.selectDate()
        }
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        presenter.loadDate(
            savedInstanceState
                ?.getSerializable(SELECTED_DATE_KEY)
                    as? LocalDate
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SELECTED_DATE_KEY, presenter.lastSelectedDate)
    }

    override fun openDateSelectedDialog(
        lastSelectedDate: LocalDate,
        listener: (year: Int, month: Int, dayOfMonth: Int) -> Unit
    ) {
        val pickerListener = { _: View, year: Int, month: Int, dayOfMonth: Int ->
            listener(year, month, dayOfMonth)
        }

        DatePickerDialog(
            context,
            pickerListener,
            lastSelectedDate.year,
            lastSelectedDate.monthValue - 1,
            lastSelectedDate.dayOfMonth
        )
            .show()
    }

    override fun showDate(lastSelectedDate: LocalDate) {
        transactionDateSelector.text = String.format("%02d.%02d.%04d", lastSelectedDate.dayOfMonth, lastSelectedDate.monthValue, lastSelectedDate.year)
    }

    private fun buildModel(): TransactionModel {
        return TransactionModel(
            id = System.currentTimeMillis(),
            description = descriptionEdittext.text.toString(),
            sum = sumEdittext.text.toString().toDoubleOrNull() ?: Double.MIN_VALUE,
            transactionTypeId = 0,
            categoryId = 0,
            date = presenter.lastSelectedDate.toString()
        )
    }

    companion object {
        private const val SELECTED_DATE_KEY = "date"

        fun newInstance() : AddTransactionFragment = AddTransactionFragment()
    }
}