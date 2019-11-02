package korotchenko.common.data.base

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.Single

abstract class BaseDataRepository<U: Any> constructor(
    private val requestWrapper: BaseRequestWrapper<U>,
    private val subscribeScheduler: Scheduler,
    private val observeScheduler: Scheduler
) {

    fun getAllModels(): Single<List<U>> {
        return Single.fromCallable {
            requestWrapper.getAllModel()
        }
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }

    fun getModelById(id: Long): Maybe<U> {
        return Maybe.fromCallable {
            requestWrapper.getModelById(id)
        }
            .map { it }
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }

    fun saveModel(model: U): Completable {
        return Completable.fromAction {
            requestWrapper.save(model)
        }
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }

    fun saveAllModel(list: List<U>): Completable {
        return Completable.fromAction {
            requestWrapper.saveAll(list)
        }
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }

    fun delete(model: U): Completable {
        return Completable.fromAction {
            requestWrapper.delete(model)
        }
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }

    fun deleteById(id: Long): Completable {
        return Completable.fromAction {
            requestWrapper.deleteById(id)
        }
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }
}