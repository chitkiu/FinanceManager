package korotchenko.financemanager.fragment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import korotchenko.financemanager.R
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.account_view_item.view.*


class AccountsAdapter(
    private val moneySymbol: String,
    private val onAccountSelect: (AccountModel) -> Unit,
    private val accountsList: List<AccountModel>
) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AccountViewHolder(moneySymbol, inflater.inflate(R.layout.account_view_item, parent, false))
    }

    override fun getItemCount(): Int = accountsList.size

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(accountsList[position], onAccountSelect)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.account_view_item
    }

    inner class AccountViewHolder(
        private val moneySymbol: String,
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

       fun bind(account: AccountModel, onAccountSelect: (AccountModel) -> Unit) {
           view.setOnClickListener {
               onAccountSelect(account)
           }
           view.nameTextView.text = account.name
           view.balanceTextView.text = String.format("%.2f %s", account.balance, moneySymbol)
       }
    }
}