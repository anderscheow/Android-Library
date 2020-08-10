package io.github.anderscheow.library.viewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.*

abstract class NetworkLocalPagingAndroidViewModel<in Args, Key : Any, Value : Any>(
        context: Application
) : BaseAndroidViewModel<Args>(context) {

    protected abstract val loadPageSize: Int

    @OptIn(ExperimentalPagingApi::class)
    protected abstract val remoteMediator: RemoteMediator<Key, Value>

    protected abstract val pagingSource: PagingSource<Key, Value>

    protected val pager by lazy {
        Pager(
                config = PagingConfig(pageSize = loadPageSize),
                remoteMediator = remoteMediator
        ) {
            pagingSource
        }.flow.cachedIn(viewModelScope)
    }

    override fun onRefresh() {
        super.onRefresh()
        pagingSource.invalidate()
    }
}