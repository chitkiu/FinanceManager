package korotchenko.financemanager.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity : WearableActivity() {

    abstract val layoutID: Int

    protected val TAG: String = javaClass.simpleName

    protected lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(layoutID)
        setAmbientEnabled()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutID)
        setAmbientEnabled()
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
    }

    protected fun handleError(e: Throwable) {
        Log.e(TAG, e.localizedMessage)
    }
}