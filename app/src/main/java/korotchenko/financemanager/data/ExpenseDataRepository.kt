package korotchenko.financemanager.data

import korotchenko.logic.models.ExpenseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseDataRepository @Inject constructor(){

    private val inMemoryExpenseModelList: MutableList<ExpenseModel> = mutableListOf()

    fun getExpenseList(): List<ExpenseModel> = inMemoryExpenseModelList

    fun saveTransaction(model: ExpenseModel) {
        inMemoryExpenseModelList += model
    }
}