package korotchenko.logic.mappers

import korotchenko.logic.models.AccountModel

abstract class AccountModelMapper<T: Any> {
    abstract fun fromAccountModel(model: AccountModel): T
    abstract fun toAccountModel(model: T): AccountModel
}