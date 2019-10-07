package korotchenko.financemanager.presentation.activity

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.base.BaseActivity
import korotchenko.financemanager.presentation.fragment.SignInFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(
            fragment = SignInFragment.newInstance(),
            addInBackStack = false,
            shouldAddOrReplace = false
        )
    }
}
