package korotchenko.logic.models

import org.threeten.bp.LocalDateTime

data class ExpenseModel(
    val id: Long,
    val description: String = "",
    val sum: Int,
    val category: CategoryModel,
    val date: LocalDateTime
)