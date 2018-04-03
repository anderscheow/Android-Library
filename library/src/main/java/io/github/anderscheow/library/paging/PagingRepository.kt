package io.github.anderscheow.library.paging

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.github.anderscheow.library.constant.NetworkState
import io.github.anderscheow.library.utils.Listing

abstract class PagingRepository<T : PagingModel> {

    abstract fun insertResultIntoDb(items : List<T>?)

    abstract fun getFirstPageItemsFromApi(success: (List<T>) -> Unit, failed: (Throwable?) -> Unit)

    @MainThread
    abstract fun getItems(pageSize: Int): Listing<T>

    @MainThread
    protected fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING

        getFirstPageItemsFromApi({
            insertResultIntoDb(it)

            networkState.postValue(NetworkState.LOADED)
        }, {
            networkState.value = NetworkState.error(it?.message)
        })

        return networkState
    }
}