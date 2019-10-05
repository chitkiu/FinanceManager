package korotchenko.financemanager.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import korotchenko.financemanager.R
import korotchenko.logic.models.ExpenseModel


class ExpensesAdapter(
    private val onExpenseSelect: (ExpenseModel) -> Unit,
    private val expensesList: List<ExpenseModel>
) : RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExpenseViewHolder(inflater.inflate(R.layout.expense_view_item, parent, false))
    }

    override fun getItemCount(): Int = expensesList.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expensesList[position], onExpenseSelect)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.expense_view_item
    }

    inner class ExpenseViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

       fun bind(account: ExpenseModel, onExpenseSelect: (ExpenseModel) -> Unit) {
           view.setOnClickListener {
               onExpenseSelect(account)
           }
       }
    }
}