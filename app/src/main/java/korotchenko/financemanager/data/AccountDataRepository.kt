package korotchenko.financemanager.data

import korotchenko.financemanager.data.db.AccountsDBRequestWrapper
import korotchenko.logic.models.AccountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataRepository @Inject constructor(
    private val accountsDbRequestWrapper: AccountsDBRequestWrapper
) {

    private var inMemoryAccountsList: MutableList<AccountModel>? = null

    fun getAccountsList(): List<AccountModel> {
        initAccountList()
        return inMemoryAccountsList ?: emptyList()
    }

    fun saveAccount(model: AccountModel) {
        initAccountList()
        inMemoryAccountsList?.takeIf{ !it.contains(model) }?.let {
            it.add(model)
            accountsDbRequestWrapper.saveAccount(model)
        }
    }

    fun saveAccounts(accounts: List<AccountModel>) {
        initAccountList()
        accountsDbRequestWrapper.saveAccounts(accounts)
        inMemoryAccountsList?.addAll(accounts)
    }

    fun deleteAccount(model: AccountModel) {
        initAccountList()
        inMemoryAccountsList?.remove(model)
        accountsDbRequestWrapper.deleteAccount(model)
    }

    fun deleteAccountById(id: Long) {
        initAccountList()
        accountsDbRequestWrapper.deleteAccountById(id)
        inMemoryAccountsList?.firstOrNull { it.id == id }?.let {
            inMemoryAccountsList?.remove(it)
        }
    }

    private fun initAccountList() {
        if(inMemoryAccountsList == null) {
            inMemoryAccountsList = mutableListOf()
            inMemoryAccountsList?.addAll(accountsDbRequestWrapper.getAllAccountFromDB())
        }
    }
}