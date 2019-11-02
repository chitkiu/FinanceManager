package korotchenko.common.models

import java.io.Serializable

data class TransactionModel(
    val id: Long,
    val description: String = "",
    val sum: Double,
    val categoryId: Long,
    val date: String
) : Serializable {
    companion object {
        const val ID_KEY = "id"
        const val DESCRIPTION_KEY = "description"
        const val SUM_KEY = "sum"
        const val CATEGORY_ID_KEY = "category_id"
        const val DATE_KEY = "date"
    }
}