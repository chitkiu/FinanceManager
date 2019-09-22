package korotchenko.logic.models

import com.google.android.gms.wearable.DataMap

data class AccountModel(
    val id: Long,
    val name: String,
    val description: String? = "",
    val balance: Double
) {
    fun toDataMap(): DataMap {
        val dataMap = DataMap()
        dataMap.putLong(ID_KEY, id)
        dataMap.putString(NAME_KEY, name)
        dataMap.putDouble(BALANCE_KEY, balance)
        if(!description.isNullOrBlank()) {
            dataMap.putString(DESCRIPTION_KEY, description)
        }
        return dataMap
    }

    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val BALANCE_KEY = "balance"
        const val DESCRIPTION_KEY = "description"

        fun fromDataMap(dataMap: DataMap): AccountModel? {
            if (dataMap.containsKey(ID_KEY)
                && dataMap.containsKey(NAME_KEY)
                && dataMap.containsKey(BALANCE_KEY)
            ) {
                return AccountModel(
                    dataMap.getLong(ID_KEY),
                    dataMap.getString(NAME_KEY),
                    dataMap.getString(DESCRIPTION_KEY, null),
                    dataMap.getDouble(BALANCE_KEY)
                )
            }
            return null
        }
    }
}