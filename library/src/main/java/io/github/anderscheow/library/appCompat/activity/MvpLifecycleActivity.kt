package io.github.anderscheow.library.appCompat.activity

import android.os.Bundle
import io.github.anderscheow.library.mvp.AbstractPresenter
import io.github.anderscheow.library.mvp.MvpView
import io.github.anderscheow.library.viewModel.BaseAndroidViewModel

abstract class MvpLifecycleActivity<VM : BaseAndroidViewModel<*>, V : MvpView, P : AbstractPresenter<V>> : LifecycleActivity<VM>() {

    abstract fun getMvpView(): V

    abstract fun getPresenter(): P

    override fun onDestroy() {
        getPresenter().onDetachView()
        super.onDestroy()
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        getPresenter().onAttachView(getMvpView())
    }
}
