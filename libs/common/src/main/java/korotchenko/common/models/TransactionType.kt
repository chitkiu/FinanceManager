package korotchenko.common.models

enum class TransactionType {
    INCOME,
    EXPENCE,
    NONE;

    companion object {
        fun getTransactionTypeFromId(id: Int): TransactionType {
            if(id < 0) {
                return NONE
            }
            val values = values()
            if(id >= values.size) {
                return NONE
            }
            return values[id]
        }
    }
}