package io.github.anderscheow.library.appCompat.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import io.github.anderscheow.library.BR
import io.github.anderscheow.library.kotlinExt.toast
import io.github.anderscheow.library.viewModel.BaseAndroidViewModel
import io.github.anderscheow.library.viewModel.util.AlertDialogData
import io.github.anderscheow.library.viewModel.util.AlertDialogMessage
import io.github.anderscheow.library.viewModel.util.ProgressDialogMessage
import io.github.anderscheow.library.viewModel.util.ToastMessage

abstract class LifecycleFragment<VM : BaseAndroidViewModel<*>> : FoundationFragment() {

    protected val viewModel by lazy {
        setupViewModel()
    }

    abstract fun setupViewModel(): VM

    abstract fun setupViewModelObserver(lifecycleOwner: LifecycleOwner)

    open fun showAlertDialog(data: AlertDialogData) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupProgressDialog()
        setupToast()
        setupAlertDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, getResLayout(), container, false)
        val view = binding.root

        binding.setVariable(BR.obj, viewModel)
        binding.lifecycleOwner = this

        return view
    }

    override fun onActivityCreated(savedInstaceState: Bundle?) {
        super.onActivityCreated(savedInstaceState)
        setupViewModelObserver(viewLifecycleOwner)
    }

    private fun setupProgressDialog() {
        viewModel.progressDialogMessage.observe(this, object : ProgressDialogMessage.ProgressDialogObserver {
            override fun onNewMessage(message: Int) {
                showProgressDialog(message)
            }

            override fun dismiss() {
                dismissProgressDialog()
            }
        })
    }

    private fun setupToast() {
        viewModel.toastMessage.observe(this, object : ToastMessage.ToastObserver {
            override fun onNewMessage(message: String?) {
                message?.let {
                    toast(it)
                }
            }
        })
    }

    private fun setupAlertDialog() {
        viewModel.alertDialogMessage.observe(this, object : AlertDialogMessage.AlertDialogObserver {
            override fun onNewMessage(data: AlertDialogData) {
                showAlertDialog(data)
            }
        })
    }
}
