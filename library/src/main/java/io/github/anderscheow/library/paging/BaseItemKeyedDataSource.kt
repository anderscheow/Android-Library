package io.github.anderscheow.library.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.support.annotation.CallSuper

import io.github.anderscheow.library.constant.NetworkState

import java.util.concurrent.Executor

abstract class BaseItemKeyedDataSource<Key, Value>(private val retryExecutor: Executor) : ItemKeyedDataSource<Key, Value>() {

    private var retry: (() -> Any)? = null

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val totalItems: MutableLiveData<Long> = MutableLiveData()

    var pageNumber = 1
        private set

    @CallSuper
    override fun loadInitial(params: ItemKeyedDataSource.LoadInitialParams<Key>, callback: ItemKeyedDataSource.LoadInitialCallback<Value>) {
        retry = {
            loadInitial(params, callback)
        }

        networkState.postValue(NetworkState.LOADING)
    }

    @CallSuper
    override fun loadAfter(params: ItemKeyedDataSource.LoadParams<Key>, callback: ItemKeyedDataSource.LoadCallback<Value>) {
        retry = {
            loadAfter(params, callback)
        }

        networkState.postValue(NetworkState.LOADING)
    }

    @CallSuper
    override fun loadBefore(params: ItemKeyedDataSource.LoadParams<Key>, callback: ItemKeyedDataSource.LoadCallback<Value>) {

    }

    protected fun incrementPageNumber() {
        pageNumber += 1
    }

    protected fun postSuccessValue() {
        networkState.postValue(NetworkState.LOADED)

        retry = null
    }

    protected fun postSuccessValue(totalOfElements: Long) {
        networkState.postValue(NetworkState.LOADED)
        totalItems.postValue(totalOfElements)

        retry = null
    }

    protected fun postFailedValue(errorMessage: String) {
        networkState.postValue(NetworkState.error(errorMessage))
    }

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }
}
