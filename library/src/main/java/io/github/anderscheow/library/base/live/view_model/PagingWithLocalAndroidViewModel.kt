package io.github.anderscheow.library.base.live.view_model

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.PagedList
import io.github.anderscheow.library.constant.NetworkState
import io.github.anderscheow.library.paging.local_remote.PagingModel
import io.github.anderscheow.library.paging.util.Listing

@Suppress("UNUSED")
abstract class PagingWithLocalAndroidViewModel<in T, Value : PagingModel>(context: Application) : BaseAndroidViewModel<T>(context) {

    protected val repoResult = MutableLiveData<Listing<Value>>()

    var items: LiveData<PagedList<Value>>? = null
    var networkState: LiveData<NetworkState>? = null
    var totalItems: LiveData<Long>? = null
    var refreshState: LiveData<NetworkState>? = null

    fun init() {
        items = Transformations.switchMap(repoResult) { it.pagedList }
        networkState = Transformations.switchMap(repoResult) { it.networkState }
        totalItems = Transformations.switchMap(repoResult) { it.totalItems }
        refreshState = Transformations.switchMap(repoResult) { it.refreshState }
    }

    override fun start(args: T?) {
        showProgressDialog(0)
    }

    override fun onRefresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }
}
