package korotchenko.logic.models

data class EnpenseModel(
    val id: Long,
    val description: String = "",
    val sum: Int,
    val category: CategoryModel,
    val date: String
)