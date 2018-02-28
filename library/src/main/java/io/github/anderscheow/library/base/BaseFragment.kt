package io.github.anderscheow.library.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.orhanobut.logger.Logger
import io.github.anderscheow.library.constant.EventBusType
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*

abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun getResLayout(): Int

    abstract fun getEventBusType(): EventBusType?

    abstract fun init()

    abstract var initializer: () -> Unit

    var isDestroy = false
        private set

    private var unbinder: Unbinder? = null

    override fun onAttach(context: Context?) {
        Logger.v("Fragment ATTACHED")
        super.onAttach(context)
        if (EventBusType.isOnAttach(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
        initializer.invoke()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("Fragment CREATED")
        super.onCreate(savedInstanceState)
        if (EventBusType.isOnCreate(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Logger.v("Fragment CREATED VIEW")
        val view = inflater.inflate(getResLayout(), container, false)
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
        if (EventBusType.isOnStart(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onResume() {
        Logger.v("Fragment RESUMED")
        super.onResume()
        if (EventBusType.isOnResume(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onPause() {
        Logger.v("Fragment PAUSED")
        if (EventBusType.isOnResume(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onPause()
    }

    override fun onStop() {
        Logger.v("Fragment STOPPED")
        if (EventBusType.isOnStart(getEventBusType())) {
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
        if (EventBusType.isOnCreate(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }

    override fun onDetach() {
        Logger.v("Fragment DETACHED")
        if (EventBusType.isOnAttach(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDetach()
    }

    fun showProgressDialog(message: Int) {
        activity?.let {
            (activity as? FoundationAppCompatActivity)?.showProgressDialog(message)
        }
    }

    fun dismissProgressDialog() {
        activity?.let {
            (activity as? FoundationAppCompatActivity)?.dismissProgressDialog()
        }
    }

    fun toast(message: String?) {
        activity?.let {
            message?.let {
                (activity as? FoundationAppCompatActivity)?.toast(message)
            }
        }
    }

    fun showOkAlertDialog(action: () -> Unit, message: String, buttonText: Int = 0) {
        activity?.let {
            (activity as? FoundationAppCompatActivity)?.showOkAlertDialog(action, message, buttonText)
        }
    }

    fun showYesAlertDialog(action: () -> Unit, message: String, buttonText: Int = 0) {
        activity?.let {
            (activity as? FoundationAppCompatActivity)?.showYesAlertDialog(action, message, buttonText)
        }
    }

    fun showNoAlertDialog(action: () -> Unit, message: String, buttonText: Int = 0) {
        (activity as? FoundationAppCompatActivity)?.showNoAlertDialog(action, message, buttonText)
    }

    fun showYesNoAlertDialog(yesAction: () -> Unit, noAction: () -> Unit, message: String, yesButtonText: Int = 0, noButtonText: Int = 0) {
        (activity as? FoundationAppCompatActivity)?.showYesNoAlertDialog(yesAction, noAction, message, yesButtonText, noButtonText)
    }
}
