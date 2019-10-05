package korotchenko.financemanager.data

import korotchenko.financemanager.data.db.AccountsDBRequestWrapper
import korotchenko.logic.models.AccountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataRepository @Inject constructor(
    private val accountsDbRequestWrapper: AccountsDBRequestWrapper
) {

    private var inMemoryAccountsList: MutableList<AccountModel> = mutableListOf()

    init {
        inMemoryAccountsList.addAll(accountsDbRequestWrapper.getAllAccountFromDB())
    }

    fun getAccountsList(): List<AccountModel> {
        return inMemoryAccountsList
    }

    fun saveAccount(model: AccountModel) {
        inMemoryAccountsList.takeIf{ !it.contains(model) }?.let {
            it.add(model)
            accountsDbRequestWrapper.saveAccount(model)
        }
    }

    fun saveAccounts(accounts: List<AccountModel>) {
        accountsDbRequestWrapper.saveAccounts(accounts)
        inMemoryAccountsList.addAll(accounts)
    }

    fun deleteAccount(model: AccountModel) {
        inMemoryAccountsList.remove(model)
        accountsDbRequestWrapper.deleteAccount(model)
    }

    fun deleteAccountById(id: Long) {
        accountsDbRequestWrapper.deleteAccountById(id)
        inMemoryAccountsList.firstOrNull { it.id == id }?.let {
            inMemoryAccountsList.remove(it)
        }
    }
}