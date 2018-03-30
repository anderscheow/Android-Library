package io.github.anderscheow.library.base.live

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import butterknife.ButterKnife
import io.github.anderscheow.library.BR
import io.github.anderscheow.library.base.FoundationAppCompatActivity
import io.github.anderscheow.library.base.live.util.ProgressDialogMessage
import io.github.anderscheow.library.base.live.view_model.BaseAndroidViewModel
import org.jetbrains.anko.toast

abstract class LifecycleAppCompatActivity<VM : BaseAndroidViewModel<*>> : FoundationAppCompatActivity() {

    var viewModel: VM? = null

    abstract fun setupViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initBeforeSuperOnCreate.invoke()

        super.onCreate(savedInstanceState)

        viewModel = setupViewModel()

        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, getResLayout())
        binding.setVariable(BR.obj, viewModel)

        getToolbar()?.let {
            setSupportActionBar(getToolbar())

            supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
        }

        ButterKnife.bind(this)

        setupProgressDialog()
        setupToast()

        init()
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
        viewModel?.toastMessage?.observe(this, Observer { s ->
            s?.let {
                toast(it)
            }
        })
    }
}
