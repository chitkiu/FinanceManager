package korotchenko.financemanager.data.wearable

import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import korotchenko.common.models.AccountModel
import korotchenko.common.presenter.AccountModelMapper
import javax.inject.Inject


//TODO: Remove or update
class TransferDataManager @Inject constructor(
    private val dataClient: DataClient,
    private val accountModelMapper: AccountModelMapper
) {

    fun sendAccounts(accounts: List<AccountModel>) {
        val dataMap = PutDataMapRequest.create(ACCOUNT_URL)
        dataMap.dataMap.putDataMapArrayList(ACCOUNT_KEY, ArrayList(accounts.map(accountModelMapper::toDataMap)))
        val request = dataMap.asPutDataRequest()
        request.setUrgent()

        val dataItemTask = dataClient.putDataItem(request)

        dataItemTask.addOnSuccessListener { dataItem ->
            Log.d("TransferDataManager", "Sending image was successful: $dataItem")
        }
    }
    companion object {
        private const val ACCOUNT_URL = "/accounts"
        private const val ACCOUNT_KEY = "all_account"
    }
}