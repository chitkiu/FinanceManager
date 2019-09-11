package korotchenko.financemanager

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import korotchenko.financemanager.base.BaseActivity
import korotchenko.logic.models.CredentialModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

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
}
