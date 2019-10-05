package korotchenko.financemanager.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.communicators.AccountActionCommunicator
import korotchenko.financemanager.presentation.communicators.AccountSelect
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.account_view_item.view.*

class AccountsAdapter(
    private val moneySymbol: String,
    private val accountActionCommunicator: AccountActionCommunicator,
    private var accountsList: List<AccountModel> = emptyList()
) : RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.account_view_item, parent, false))
    }

    override fun getItemCount(): Int = accountsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(accountsList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.account_view_item
    }

    fun setDate(accounts: List<AccountModel>) {
        accountsList = accounts
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(account: AccountModel) {
            view.nameTextView.text = account.name
            view.balanceTextView.text = String.format("%.2f %s", account.balance, moneySymbol)
            view.setOnClickListener {
                accountActionCommunicator.sendAction(AccountSelect(account))
            }
        }
    }
}