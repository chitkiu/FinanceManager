package korotchenko.financemanager.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import korotchenko.financemanager.R
import korotchenko.logic.models.AccountModel
import kotlinx.android.synthetic.main.account_view_item.view.*


class AccountsAdapter(
    private val moneySymbol: String,
    private val onAccountClick: (AccountModel) -> Unit = {},
    private val onAccountLongClick: (AccountModel, View) -> Unit = { _, _ -> },
    private val accountsList: List<AccountModel>
) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AccountViewHolder(inflater.inflate(R.layout.account_view_item, parent, false))
    }

    override fun getItemCount(): Int = accountsList.size

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(accountsList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.account_view_item
    }

    inner class AccountViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

       fun bind(account: AccountModel) {
           view.setOnClickListener {
               onAccountClick(account)
           }
           view.setOnLongClickListener {
               onAccountLongClick(account, it)
               return@setOnLongClickListener true
           }
           view.nameTextView.text = account.name
           view.balanceTextView.text = String.format("%.2f %s", account.balance, moneySymbol)

           view.descriptionHintTextView.visibility = if(account.description.isNullOrBlank()) View.GONE else View.VISIBLE
           view.descriptionTextView.visibility = if(account.description.isNullOrBlank()) View.GONE else View.VISIBLE
           view.descriptionTextView.text = account.description
       }
    }
}