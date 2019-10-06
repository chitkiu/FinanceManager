package korotchenko.financemanager.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import korotchenko.financemanager.R
import korotchenko.financemanager.presentation.activity.MainActivity

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity?
        get() {
            return activity as? MainActivity
        }

    abstract val layoutID: Int

    val TAG: String = javaClass.simpleName

    protected lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutID, container, false)
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

    protected fun showFragment(
        fragment: BaseFragment,
        container: Int = R.id.content_frame,
        blockFragmentRepeat: Boolean = false,
        addInBackStack: Boolean = true,
        shouldAddOrReplace: Boolean = true
    ) {
        mainActivity?.showFragment(fragment, container, addInBackStack, shouldAddOrReplace)
    }

    protected fun hideFragment(fragment: BaseFragment) {
        mainActivity?.hideFragment(fragment)
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