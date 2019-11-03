package korotchenko.financemanager.presentation.base

import android.util.Log
import androidx.annotation.CallSuper
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import korotchenko.financemanager.R

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class BasePresenter<V : BaseView> {

    protected var compositeDisposable: CompositeDisposable? = null

    protected var view: V? = null

    open fun onViewAttach(isFirstAttach: Boolean) {}

    @CallSuper
    fun attach(view: V) {
        if(this.view != view) {
            this.view = view
        }
        compositeDisposable?.dispose()
        compositeDisposable = CompositeDisposable()
    }

    @CallSuper
    fun detach() {
        compositeDisposable?.dispose()
        this.view = null
    }


    protected fun handleError(e: Throwable) {
        Log.e("Error", e.localizedMessage)
    }

    protected fun <T> Observable<T>.safeSubscribe(
        onNext: (T) -> Unit
    ) {
        compositeDisposable?.add(subscribe(onNext, ::handleError))
    }

    protected fun <T> Observable<T>.safeSubscribe(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable?.add(subscribe(onNext, onError))
    }

    protected fun <T> Single<T>.safeSubscribe(
        onNext: (T) -> Unit
    ) {
        compositeDisposable?.add(subscribe(onNext, ::handleError))
    }

    protected fun <T> Single<T>.safeSubscribe(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable?.add(subscribe(onNext, onError))
    }

    protected fun <T> Maybe<T>.safeSubscribe(
        onNext: (T) -> Unit
    ) {
        compositeDisposable?.add(subscribe(onNext, ::handleError))
    }

    protected fun <T> Maybe<T>.safeSubscribe(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable?.add(subscribe(onNext, onError))
    }

    protected fun Completable.safeSubscribe(
        onNext: () -> Unit
    ) {
        compositeDisposable?.add(subscribe(onNext, ::handleError))
    }

    protected fun Completable.safeSubscribe(
        onNext: () -> Unit,
        onError: (Throwable) -> Unit = ::handleError
    ) {
        compositeDisposable?.add(subscribe(onNext, onError))
    }
}
