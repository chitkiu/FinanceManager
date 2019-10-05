package korotchenko.logic.models

data class AccountModel(
    val id: Long,
    val name: String,
    val description: String = "",
    val balance: Double
) {
    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val BALANCE_KEY = "balance"
        const val DESCRIPTION_KEY = "description"
    }
}