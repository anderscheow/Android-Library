package io.github.anderscheow.library.base.live

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import io.github.anderscheow.library.BR
import io.github.anderscheow.library.base.FoundationFragment
import io.github.anderscheow.library.base.live.util.ProgressDialogMessage
import io.github.anderscheow.library.base.live.util.ToastMessage
import io.github.anderscheow.library.base.live.view_model.BaseAndroidViewModel
import io.github.anderscheow.library.kotlin.lazyThreadSafetyNone
import org.jetbrains.anko.support.v4.toast

@Suppress("UNUSED")
abstract class LifecycleFragment<VM : BaseAndroidViewModel<*>> : FoundationFragment() {

    protected val viewModel by lazyThreadSafetyNone {
        setupViewModel()
    }

    private var unbinder: Unbinder? = null

    abstract fun setupViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupProgressDialog()
        setupToast()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, getResLayout(), container, false)
        val view = binding.root

        if (requiredButterknife()) {
            unbinder = ButterKnife.bind(this, view)
        }

        binding.setVariable(BR.obj, viewModel)

        return view
    }

    override fun onDestroyView() {
        unbinder?.unbind()
        super.onDestroyView()
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
}
