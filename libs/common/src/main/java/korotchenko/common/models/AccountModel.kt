package korotchenko.common.models

import java.io.Serializable

data class AccountModel(
    val id: Long,
    val name: String,
    val description: String = "",
    val balance: Double
) : Serializable {
    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val BALANCE_KEY = "balance"
        const val DESCRIPTION_KEY = "description"
    }
}