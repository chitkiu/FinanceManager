package korotchenko.logic.presenter

import com.google.android.gms.wearable.DataMap
import korotchenko.logic.models.AccountModel

class AccountModelMapper {
    fun toDataMap(accountModel: AccountModel): DataMap {
        val dataMap = DataMap()
        dataMap.putLong(AccountModel.ID_KEY, accountModel.id)
        dataMap.putString(AccountModel.NAME_KEY, accountModel.name)
        dataMap.putDouble(AccountModel.BALANCE_KEY, accountModel.balance)
        if(!accountModel.description.isNullOrBlank()) {
            dataMap.putString(AccountModel.DESCRIPTION_KEY, accountModel.description)
        }
        return dataMap
    }

    fun fromDataMap(dataMap: DataMap): AccountModel? {
        if (dataMap.containsKey(AccountModel.ID_KEY)
            && dataMap.containsKey(AccountModel.NAME_KEY)
            && dataMap.containsKey(AccountModel.BALANCE_KEY)
        ) {
            return AccountModel(
                dataMap.getLong(AccountModel.ID_KEY),
                dataMap.getString(AccountModel.NAME_KEY),
                dataMap.getString(AccountModel.DESCRIPTION_KEY, null),
                dataMap.getDouble(AccountModel.BALANCE_KEY)
            )
        }
        return null
    }
}