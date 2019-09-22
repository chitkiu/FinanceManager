package korotchenko.logic.models

data class ExchangingModel(
    val id: Long,
    val from: AccountModel,
    val to: AccountModel,
    val sum: String,
    val description: String? = "",
    val date: String
)