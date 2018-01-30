package io.github.anderscheow.library.base.live

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import io.github.anderscheow.library.base.live.util.ProgressDialogMessage

import butterknife.ButterKnife
import butterknife.Unbinder
import io.github.anderscheow.library.base.BaseFragment
import io.github.anderscheow.library.BR
import io.github.anderscheow.library.R
import io.github.anderscheow.library.base.live.view_model.BaseAndroidViewModel

abstract class LifecycleFragment<VM : BaseAndroidViewModel<*>> : BaseFragment() {

    var viewModel: VM? = null

    private var progressDialog: ProgressDialog? = null

    private var unbinder: Unbinder? = null

    abstract fun setupViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = setupViewModel()

        setupProgressDialog()
        setupToast()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, resLayout, container, false)
        val view = binding.root
        unbinder = ButterKnife.bind(this, view)

        binding.setVariable(BR.obj, viewModel)

        return view
    }

    override fun onDestroyView() {
        unbinder?.unbind()
        super.onDestroyView()
    }

    private fun setupProgressDialog() {
        viewModel?.progressDialogMessage?.observe(this, object : ProgressDialogMessage.ProgressDialogObserver {
            override fun onNewMessage(message: Int) {
                showProgressDialog(message)
            }

            override fun dismiss() {
                dismissProgressDialog()
            }
        })
    }

    private fun setupToast() {
        viewModel?.toastMessage?.observe(this, Observer { s -> toast(s) })
    }

    private fun showProgressDialog(message: Int) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity)
        }

        progressDialog?.setMessage(if (message == 0) getString(R.string.prompt_please_wait) else getString(message))
        progressDialog?.setCanceledOnTouchOutside(false)
        progressDialog?.setCancelable(false)
        progressDialog?.isIndeterminate = true
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun toast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}
