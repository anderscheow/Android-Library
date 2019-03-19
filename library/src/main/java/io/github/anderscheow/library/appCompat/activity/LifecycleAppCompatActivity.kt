package io.github.anderscheow.library.appCompat.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.anderscheow.library.BR
import io.github.anderscheow.library.viewModel.BaseAndroidViewModel
import io.github.anderscheow.library.viewModel.util.ProgressDialogMessage
import io.github.anderscheow.library.viewModel.util.ToastMessage
import org.jetbrains.anko.toast

abstract class LifecycleAppCompatActivity<VM : BaseAndroidViewModel<*>> : FoundationAppCompatActivity() {

    protected val viewModel by lazy {
        setupViewModel()
    }

    abstract fun setupViewModel(): VM

    abstract fun setupViewModelObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        initBeforeSuperOnCreate()

        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, getResLayout())
        binding.setVariable(BR.obj, viewModel)

        getToolbar()?.let {
            setSupportActionBar(getToolbar())

            supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
        }

        setupProgressDialog()
        setupToast()
        setupViewModelObserver()

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
