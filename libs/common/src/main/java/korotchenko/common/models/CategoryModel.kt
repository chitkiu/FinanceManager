package korotchenko.common.models

data class CategoryModel(
    val id: Long,
    val name: String,
    val categotyType: CategoryType,
    val color: String = "",
    val icon: String = ""
)
