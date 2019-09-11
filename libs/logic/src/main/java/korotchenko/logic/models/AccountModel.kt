package korotchenko.logic.models

data class AccountModel(
    val id: Long,
    val name: String,
    val description: String? = "",
    val balance: Double?
)