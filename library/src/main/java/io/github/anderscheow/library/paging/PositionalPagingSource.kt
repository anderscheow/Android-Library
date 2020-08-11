package io.github.anderscheow.library.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * Key refer to Page Number. LoadResult.Page.nextKey will be Page Number + 1 or null if no next page
 */
abstract class PositionalPagingSource<Value : Any>
    : PagingSource<Int, Value>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        return try {
            getNextPageData(params.key)
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition
    }

    abstract suspend fun getNextPageData(nextKey: Int?): LoadResult<Int, Value>
}