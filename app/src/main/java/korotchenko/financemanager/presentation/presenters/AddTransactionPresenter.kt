package korotchenko.financemanager.presentation.presenters

import korotchenko.common.models.TransactionModel
import korotchenko.financemanager.presentation.base.BasePresenter
import korotchenko.financemanager.presentation.fragment.AddTransactionView
import org.threeten.bp.LocalDate
import javax.inject.Inject

class AddTransactionPresenter @Inject constructor(

) : BasePresenter<AddTransactionView>() {

    var lastSelectedDate: LocalDate = LocalDate.now()
        private set

    override fun onViewAttach(isFirstAttach: Boolean) {
        super.onViewAttach(isFirstAttach)

        if(isFirstAttach) {
            view?.showDate(lastSelectedDate)
        }
    }

    fun addTransaction(model: TransactionModel) {

    }

    fun selectDate() {
        view!!.openDateSelectedDialog(
            lastSelectedDate,
            this::onDateSelected
        )
    }

    fun loadDate(date: LocalDate?) {
        date?.let { lastSelectedDate = it }
    }

    private fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        lastSelectedDate = LocalDate.of(year, month+1, dayOfMonth)
        view?.showDate(lastSelectedDate)
    }
}