package io.github.anderscheow.library.base.live.view_model

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.github.anderscheow.library.constant.NetworkState
import io.github.anderscheow.library.paging.BaseDataSourceFactory
import io.github.anderscheow.library.paging.BaseItemKeyedDataSource
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Suppress("UNUSED")
abstract class PagingWithoutLocalAndroidViewModel<in T, Key, Value>(context: Application) : BaseAndroidViewModel<T>(context) {

    protected abstract val numberOfThreads: Int

    protected abstract val loadPageSize: Int

    protected abstract fun getDataSourceFactory(executor: Executor): BaseDataSourceFactory<Key, Value>

    var items: LiveData<PagedList<Value>>? = null
    var networkState: LiveData<NetworkState>? = null
    var totalItems: LiveData<Long>? = null

    private var tDataSource: LiveData<BaseItemKeyedDataSource<Key, Value>>? = null

    fun init() {
        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val factory = getDataSourceFactory(executor)
        tDataSource = factory.mutableLiveData

        networkState = Transformations.switchMap(factory.mutableLiveData
        ) { input -> input.networkState }

        totalItems = Transformations.switchMap(factory.mutableLiveData
        ) { input -> input.totalItems }

        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
                .setInitialLoadSizeHint(loadPageSize)
                .setPageSize(loadPageSize).build()

        items = LivePagedListBuilder(factory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
    }

    override fun start(args: T?) {
        showProgressDialog(0)
    }

    override fun onRefresh() {
        tDataSource?.value?.invalidate()
    }

    @Suppress("UNUSED")
    fun retry() {
        tDataSource?.value?.retry()
    }
}
