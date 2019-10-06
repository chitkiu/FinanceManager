package korotchenko.financemanager.presentation.tiles

import android.util.Log
import android.widget.RemoteViews
import com.google.android.clockwork.tiles.TileData
import com.google.android.clockwork.tiles.TileProviderService
import com.google.android.gms.wearable.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import korotchenko.financemanager.R
import korotchenko.logic.presenter.AccountModelMapper

//TODO: Add DI, Update desing
class MyTileProviderService : TileProviderService(), DataClient.OnDataChangedListener {

    private var id: Int = -1

    private val accountModelMapper: AccountModelMapper = AccountModelMapper()

    private var compositeDisposable: CompositeDisposable? = null

    override fun onTileUpdate(tileId: Int) {
        Log.e(TAG, "onTileUpdate() called with: tileId = [$tileId]")

        if (!isIdForDummyData(tileId)) {
            id = tileId
            Wearable.getDataClient(baseContext).addListener(this)
            sendRemoteViews()
        }
    }

    override fun onTileFocus(tileId: Int) {
        Log.e(TAG, "onTileFocus() called with: tileId = [$tileId]")

        id = tileId

        compositeDisposable?.dispose()
        compositeDisposable = CompositeDisposable()
        Wearable.getDataClient(baseContext).removeListener(this)
        Wearable.getDataClient(baseContext).addListener(this)

        Wearable
            .getDataClient(baseContext)
            .dataItems
            .addOnSuccessListener { it.forEach(::handleAccountList) }
    }

    override fun onTileBlur(tileId: Int) {
        Log.e(TAG, "onTileBlur() called with: tileId = [$tileId]")

        compositeDisposable?.dispose()
        Wearable.getDataClient(baseContext).removeListener(this)
    }

    private fun sendRemoteViews(balance: Double = 0.0) {
        Log.e(TAG, "sendRemoteViews")

        val remoteViews = RemoteViews(this.packageName, R.layout.tile)
        // *** Update your tile UI here
        val b = String.format("%.2f %s", balance, MONEY_SYMBOL)
        remoteViews.setTextViewText(R.id.totalBalanceTileTextView, b)

        val bob = TileData.Builder()
            .setRemoteViews(remoteViews)
        sendData(id, bob.build())
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            when(event.type) {
                DataEvent.TYPE_CHANGED -> handleAccountList(event)
            }
        }
    }

    private fun handleAccountList(event: DataEvent) {
        handleAccountList(event.dataItem)
    }

    private fun handleAccountList(dataItem: DataItem) {
        Log.e(TAG, "handleAccountList dataItem $dataItem")
        val path = dataItem.uri.path
        if(path == ACCOUNT_URL) {

            compositeDisposable?.add(
                Single.fromCallable {
                    DataMapItem
                        .fromDataItem(dataItem)
                        .dataMap
                        .getDataMapArrayList(ACCOUNT_KEY)
                }
                    .map { it.map(accountModelMapper::fromDataMap) }
                    .map { accountList ->
                        return@map accountList.sumByDouble { it?.balance ?: 0.0 }
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { balance ->
                        sendRemoteViews(balance)
                    }
            )
        }
    }

    companion object {
        private const val MONEY_SYMBOL = "₴"
        private const val ACCOUNT_URL = "/accounts"
        private const val ACCOUNT_KEY = "all_account"

        private const val TAG = "MyTileProviderService"
    }
}