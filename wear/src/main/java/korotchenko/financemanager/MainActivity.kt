package korotchenko.financemanager

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.Wearable
import korotchenko.financemanager.presentation.base.BaseActivity
import korotchenko.logic.models.CredentialModel
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.wearable.DataMapItem



class MainActivity : BaseActivity(), DataClient.OnDataChangedListener {

    override val layoutID: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sign_in_button.setOnClickListener {
            signInByGoogle()
        }
        sign_out_button.setOnClickListener {
            signOutFromGoogle()
        }
    }

    override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(this)
    }

    override fun onPause() {
        super.onPause()

        Wearable.getDataClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        Log.d("MainActivity", "onDataChanged dataEvents $dataEvents")

        dataEvents.forEach { event ->
            when(event.type) {
                DataEvent.TYPE_CHANGED -> handleAccountList(event)
            }
        }
    }

    private fun handleAccountList(event: DataEvent) {
        val path = event.dataItem.uri.path
        if(path == ACCOUNT_URL || path == "accounts") {
            val dataMapItem = DataMapItem.fromDataItem(event.dataItem)
            dataMapItem.dataMap.getDataMapArrayList(ACCOUNT_KEY).forEach {
                Log.d("MainActivity", "handleAccountList data $it")
            }
        }
    }

    override fun onSignInError() {
        sign_in_button.visibility = View.VISIBLE
        sign_in_text.visibility = View.GONE
        sign_in_credentials.visibility = View.GONE
        sign_out_button.visibility = View.GONE
    }

    override fun onSignInSuccess(credential: CredentialModel) {
        sign_in_button.visibility = View.GONE
        sign_in_text.visibility = View.VISIBLE
        sign_in_credentials.visibility = View.VISIBLE
        sign_in_credentials.text = credential.toString()
        sign_out_button.visibility = View.VISIBLE
    }

    override fun onSignOutSuccess() {
        sign_in_button.visibility = View.VISIBLE
        sign_in_text.visibility = View.GONE
        sign_in_credentials.visibility = View.GONE
        sign_out_button.visibility = View.GONE
    }

    override fun onSignOutError() {
        sign_in_button.visibility = View.GONE
        sign_in_text.visibility = View.VISIBLE
        sign_in_credentials.visibility = View.VISIBLE
//        sign_in_credentials.text = get.toString()
        sign_out_button.visibility = View.VISIBLE
    }

    companion object {
        private const val ACCOUNT_URL = "/accounts"
        private const val ACCOUNT_KEY = "all_account"
    }
}
