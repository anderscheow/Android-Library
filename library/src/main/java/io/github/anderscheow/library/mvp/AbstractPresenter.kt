package io.github.anderscheow.library.mvp

import android.app.Application
import androidx.annotation.UiThread
import io.github.anderscheow.library.CustomApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.DIAware
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

open class AbstractPresenter<V : BaseView>(application: Application) : BasePresenter<V>,
    DIAware, CoroutineScope {

    private val job = Job()

    override val di by (application as CustomApplication).di
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private var viewRef: WeakReference<V>? = null

    var view: V? = null
        private set
        get() = viewRef?.get()

    @UiThread
    override fun onAttachView(view: V) {
        if (isViewAttached().not()) {
            viewRef = WeakReference(view)
        }
    }

    @UiThread
    override fun onDetachView() {
        if (viewRef != null) {
            viewRef!!.clear()
            viewRef = null
        }
        job.cancel()
    }

    @UiThread
    override fun onStart() {
    }

    @UiThread
    override fun onResume() {
    }

    @UiThread
    override fun onPause() {
    }

    @UiThread
    override fun onDestroy() {
    }

    @UiThread
    fun isViewAttached(): Boolean {
        return viewRef != null && viewRef!!.get() != null
    }
}