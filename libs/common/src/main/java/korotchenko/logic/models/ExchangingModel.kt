package korotchenko.logic.models

import org.threeten.bp.LocalDateTime

data class ExchangingModel(
    val id: Long,
    val from: AccountModel,
    val to: AccountModel,
    val sum: String,
    val description: String = "",
    val date: LocalDateTime
)