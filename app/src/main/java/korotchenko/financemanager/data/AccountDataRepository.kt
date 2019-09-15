package korotchenko.financemanager.data

import korotchenko.logic.models.AccountModel

class AccountDataRepository {

    private val inMemoryAccountsList: MutableList<AccountModel> = mutableListOf()

    fun getAccountsList(): List<AccountModel> {
        return inMemoryAccountsList
    }

    fun saveAccount(model: AccountModel) {
        if(!inMemoryAccountsList.contains(model)) {
            inMemoryAccountsList += model
        }
    }

    fun deleteAccount(model: AccountModel) {
        inMemoryAccountsList.remove(model)
    }

    fun deleteAccountById(id: Long) {
        inMemoryAccountsList.firstOrNull { it.id == id }?.let {
            inMemoryAccountsList.remove(it)
        }
    }
}