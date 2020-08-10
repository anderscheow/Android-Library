package io.github.anderscheow.library.viewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.anderscheow.library.paging.BasePagingSource

abstract class NetworkPagingAndroidViewModel<in Args, Key : Any, Value : Any>(
        context: Application
) : BaseAndroidViewModel<Args>(context) {

    protected abstract val loadPageSize: Int

    protected abstract val pagingSource: BasePagingSource<Key, Value>

    protected val pager by lazy {
        Pager(
                config = PagingConfig(pageSize = loadPageSize)
        ) {
            pagingSource
        }.flow.cachedIn(viewModelScope)
    }

    override fun onRefresh() {
        super.onRefresh()
        pagingSource.invalidate()
    }
}