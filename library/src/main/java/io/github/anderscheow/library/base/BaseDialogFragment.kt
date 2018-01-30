package io.github.anderscheow.library.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.anderscheow.library.constant.EventBusType
import com.orhanobut.logger.Logger

import org.greenrobot.eventbus.EventBus

import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseDialogFragment : DialogFragment() {

    @get:LayoutRes
    abstract val resLayout: Int

    abstract val eventBusType: EventBusType?

    // Header and content
    @get:StringRes
    abstract val title: Int

    var isDestroy = false
        private set

    private var unbinder: Unbinder? = null

    override fun onAttach(context: Context?) {
        Logger.v("Fragment ATTACHED")
        super.onAttach(context)
        if (EventBusType.isOnAttach(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("Fragment CREATED")
        super.onCreate(savedInstanceState)
        if (EventBusType.isOnCreate(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        if (title > 0) {
            dialog.setTitle(title)
        }

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Logger.v("Fragment CREATED VIEW")
        val view = inflater.inflate(resLayout, container, false)
        unbinder = ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            init()
        }
    }

    override fun onActivityCreated(savedInstaceState: Bundle?) {
        Logger.v("Fragment ACTIVITY CREATED")
        super.onActivityCreated(savedInstaceState)
    }

    override fun onStart() {
        Logger.v("Fragment STARTED")
        super.onStart()
        if (EventBusType.isOnStart(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onResume() {
        Logger.v("Fragment RESUMED")
        super.onResume()
        if (EventBusType.isOnResume(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onPause() {
        Logger.v("Fragment PAUSED")
        if (EventBusType.isOnResume(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onPause()
    }

    override fun onStop() {
        Logger.v("Fragment STOPPED")
        if (EventBusType.isOnStart(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onStop()
    }

    override fun onDestroyView() {
        Logger.v("Fragment VIEW DESTROYED")
        unbinder?.unbind()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Logger.v("Fragment DESTROYED")
        isDestroy = true
        if (EventBusType.isOnCreate(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }

    override fun onDetach() {
        Logger.v("Fragment DETACHED")
        if (EventBusType.isOnAttach(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDetach()
    }

    open fun init() {}
}
