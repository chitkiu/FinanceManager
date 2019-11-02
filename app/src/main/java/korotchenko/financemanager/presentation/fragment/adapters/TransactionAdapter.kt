package korotchenko.financemanager.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import korotchenko.financemanager.R
import korotchenko.common.models.TransactionModel


class TransactionAdapter(
    private val onTransactionSelect: (TransactionModel) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactionList: List<TransactionModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransactionViewHolder(inflater.inflate(R.layout.expense_view_item, parent, false), onTransactionSelect)
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.expense_view_item
    }

    fun setTransactions(transactions: List<TransactionModel>) {
        transactionList = transactions
    }

    inner class TransactionViewHolder(
        private val view: View,
        private val onTransactionSelect: (TransactionModel) -> Unit
    ) : RecyclerView.ViewHolder(view) {

       fun bind(transactionModel: TransactionModel) {

           view.setOnClickListener {
               onTransactionSelect(transactionModel)
           }
       }
    }
}