package io.github.anderscheow.library.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar

import io.github.anderscheow.library.constant.EventBusType
import com.orhanobut.logger.Logger

import org.greenrobot.eventbus.EventBus

import butterknife.ButterKnife
import io.github.anderscheow.library.services.ApplicationService
import io.github.anderscheow.library.services.ServiceBoundAppCompatActivity

abstract class BaseAppCompatActivity<T : ApplicationService> : ServiceBoundAppCompatActivity<T>() {

    @get:LayoutRes
    abstract val resLayout: Int

    abstract val toolbar: Toolbar?

    abstract val eventBusType: EventBusType?

    abstract fun requiredDisplayHomeAsUp(): Boolean

    abstract fun requiredDataBinding(): Boolean

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("Activity CREATED")
        super.onCreate(savedInstanceState)
        if (EventBusType.isOnCreate(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }

        if (!requiredDataBinding()) {
            setContentView(resLayout)
            ButterKnife.bind(this)

            toolbar?.let {
                setSupportActionBar(toolbar)

                supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
            }
        }

        startService()
        init()
    }

    override fun onStart() {
        Logger.v("Activity STARTED")
        super.onStart()
        if (EventBusType.isOnStart(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onResume() {
        Logger.v("Activity RESUMED")
        super.onResume()
        if (EventBusType.isOnResume(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onPause() {
        Logger.v("Activity PAUSED")
        if (EventBusType.isOnResume(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onPause()
    }

    override fun onStop() {
        Logger.v("Activity STOPPED")
        if (EventBusType.isOnStart(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onStop()
    }

    override fun onRestart() {
        Logger.v("Activity RESTARTED")
        super.onRestart()
    }

    override fun onDestroy() {
        Logger.v("Activity DESTROYED")
        if (EventBusType.isOnCreate(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }
}
