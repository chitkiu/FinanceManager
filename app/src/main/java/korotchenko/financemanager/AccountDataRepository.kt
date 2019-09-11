package korotchenko.financemanager

import korotchenko.logic.models.AccountModel

class AccountDataRepository private constructor() {

    private val inMemoryAccountsList: MutableList<AccountModel> = mutableListOf()

    init {
        //TODO remove this, use only for test
        inMemoryAccountsList.addAll(listOf(
            AccountModel(
                id = 1,
                name = "Карта приват",
                balance = 0.0
            ),AccountModel(
                id = 2,
                name = "Карта моно",
                balance = 0.0
            ),AccountModel(
                id = 3,
                name = "Наличные",
                balance = 0.0
            )
        ))
    }

    fun getAccountsList(): List<AccountModel> {
        return inMemoryAccountsList
    }

    fun saveAccount(model: AccountModel) {
        inMemoryAccountsList += model
    }

    fun deleteAccount(model: AccountModel) {
        inMemoryAccountsList.remove(model)
    }

    fun deleteAccountById(id: Long) {
        inMemoryAccountsList.firstOrNull { it.id == id }?.let {
            inMemoryAccountsList.remove(it)
        }
    }

    companion object {
        private var adr: AccountDataRepository? = null
        fun getInstance(): AccountDataRepository {
            if(adr == null) {
                adr = AccountDataRepository()
            }
            return adr!!
        }
    }
}