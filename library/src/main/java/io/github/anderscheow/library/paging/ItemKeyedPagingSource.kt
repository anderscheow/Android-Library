package io.github.anderscheow.library.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.anderscheow.library.paging.model.PageAssociatedModel

/**
 * Key refer to Object ID | Page Number which is String. LoadResult.Page.nextKey will be Key.split["|"][1] + 1 or null if no next page
 */
abstract class ItemKeyedPagingSource<Value : PageAssociatedModel<String>>
    : PagingSource<String, Value>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Value> {
        return try {
            getNextPageData(params.key)
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<String, Value>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.key
        }
    }

    abstract suspend fun getNextPageData(nextKey: String?): LoadResult<String, Value>
}