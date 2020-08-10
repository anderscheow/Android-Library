package io.github.anderscheow.library.paging

import androidx.paging.PagingSource

abstract class BasePagingSource<Key : Any, Value : Any>
    : PagingSource<Key, Value>() {

    protected var currentPage = 0
        private set

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value> {
        return try {
            if (params.key == null) {
                currentPage = 0
            }
            incrementPageNumber()
            getNextPageData(currentPage)
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    abstract fun getNextPageData(nextPageNumber: Int): LoadResult<Key, Value>

    private fun incrementPageNumber() {
        currentPage += 1
    }
}