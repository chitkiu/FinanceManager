package korotchenko.financemanager.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign


abstract class BaseActivity : WearableActivity() {

    abstract val layoutID: Int

    protected val TAG: String = javaClass.simpleName

    protected lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState, persistentState)
        setContentView(layoutID)
        setAmbientEnabled()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
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

    protected fun <T> Observable<T>.safeSubscribe(
        onNext: (T) -> Unit
    ) {
        compositeDisposable += subscribe(onNext, ::handleError)
    }

    protected fun <T> Observable<T>.safeSubscribe(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable += subscribe(onNext, onError)
    }

    protected fun <T> Single<T>.safeSubscribe(
        onNext: (T) -> Unit
    ) {
        compositeDisposable += subscribe(onNext, ::handleError)
    }

    protected fun <T> Single<T>.safeSubscribe(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable += subscribe(onNext, onError)
    }

    protected fun <T> Maybe<T>.safeSubscribe(
        onNext: (T) -> Unit
    ) {
        compositeDisposable += subscribe(onNext, ::handleError)
    }

    protected fun <T> Maybe<T>.safeSubscribe(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable += subscribe(onNext, onError)
    }

    protected fun <T> Completable.safeSubscribe(
        onNext: () -> Unit
    ) {
        compositeDisposable += subscribe(onNext, ::handleError)
    }

    protected fun <T> Completable.safeSubscribe(
        onNext: () -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable += subscribe(onNext, onError)
    }

}