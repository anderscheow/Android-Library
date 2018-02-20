/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.github.anderscheow.library.base.live

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import io.github.anderscheow.library.BR
import io.github.anderscheow.library.base.FoundationAppCompatActivity
import io.github.anderscheow.library.base.live.util.ProgressDialogMessage
import io.github.anderscheow.library.base.live.view_model.BaseAndroidViewModel

/**
 * Temporary class until Architecture Components is final. Makes [AppCompatActivity] a
 * [LifecycleOwner].
 */
@SuppressLint("Registered")
abstract class LifecycleAppCompatActivity<VM : BaseAndroidViewModel<*>> : FoundationAppCompatActivity() {

    var viewModel: VM? = null

    abstract fun setupViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initializer.invoke()

        viewModel = setupViewModel()

        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, resLayout)
        binding.setVariable(BR.obj, viewModel)

        super.onCreate(savedInstanceState)

        toolbar?.let {
            setSupportActionBar(toolbar)

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
