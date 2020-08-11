package io.github.anderscheow.library.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.github.anderscheow.library.paging.model.PageAssociatedModel

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<Key : Any, Value : PageAssociatedModel<Key>>
    : RemoteMediator<Key, Value>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Key, Value>): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                            ?: return MediatorResult.Success(
                                    endOfPaginationReached = true
                            )

                    lastItem.key
                }
            }

            return getNextPageDataAndInsertToDb(loadType, loadKey)
        } catch (ex: Exception) {
            MediatorResult.Error(ex)
        }
    }

    abstract fun getLastItem(state: PagingState<Key, Value>): Value?

    abstract suspend fun getNextPageDataAndInsertToDb(loadType: LoadType, nextKey: Key?): MediatorResult
}