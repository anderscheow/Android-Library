package io.github.anderscheow.library.base.live

import android.os.Bundle
import androidx.core.widget.toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.anderscheow.library.BR
import io.github.anderscheow.library.base.FoundationAppCompatActivity
import io.github.anderscheow.library.base.live.util.ProgressDialogMessage
import io.github.anderscheow.library.base.live.util.ToastMessage
import io.github.anderscheow.library.base.live.view_model.BaseAndroidViewModel

@Suppress("UNUSED")
abstract class LifecycleAppCompatActivity<VM : BaseAndroidViewModel<*>> : FoundationAppCompatActivity() {

    protected val viewModel by lazy {
        setupViewModel()
    }

    abstract fun setupViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initBeforeSuperOnCreate.invoke()

        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, getResLayout())
        binding.setVariable(BR.obj, viewModel)

        getToolbar()?.let {
            setSupportActionBar(getToolbar())

            supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
        }

        setupProgressDialog()
        setupToast()

        init()
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
