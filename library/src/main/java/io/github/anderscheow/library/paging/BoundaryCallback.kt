package io.github.anderscheow.library.paging

import android.arch.paging.PagedList
import android.support.annotation.MainThread
import io.github.anderscheow.library.utils.PagingRequestHelper
import io.github.anderscheow.library.utils.createStatusLiveData
import java.util.concurrent.Executor

abstract class BoundaryCallback<T : PagingModel>(private val handleResponse: (List<T>?) -> Unit,
                                                 private val totalItems: (Long) -> Unit,
                                                 private val executor: Executor)
    : PagedList.BoundaryCallback<T>() {

    val helper = PagingRequestHelper(executor)
    val networkState = helper.createStatusLiveData()

    private var pageNumber = 1

    abstract fun loadItemsSynchronously(pageNumber: Int, success: (List<T>) -> Unit, failed: (Throwable) -> Unit)

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { callback ->
            loadItemsSynchronously(pageNumber, { items ->
                insertItemsIntoDb(items, callback)
            }, {
                callback.recordFailure(it)
            })
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: T) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { callback ->
            loadItemsSynchronously(pageNumber, { items ->
                insertItemsIntoDb(items, callback)
            }, {
                callback.recordFailure(it)
            })
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        // ignored, since we only ever append to what's in the DB
    }

    /**
     * every time callback gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(response: List<T>, callback: PagingRequestHelper.Request.Callback) {
        executor.execute {
            handleResponse(response)
            callback.recordSuccess()

            pageNumber += 1
        }
    }
}