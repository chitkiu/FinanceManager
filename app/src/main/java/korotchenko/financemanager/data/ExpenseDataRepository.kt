package korotchenko.financemanager.data

import korotchenko.logic.models.ExpenseModel

class ExpenseDataRepository {

    private val inMemoryExpenseModelList: MutableList<ExpenseModel> = mutableListOf()

    fun getExpenseList(): List<ExpenseModel> = inMemoryExpenseModelList

    fun saveTransaction(model: ExpenseModel) {
        inMemoryExpenseModelList += model
    }
}