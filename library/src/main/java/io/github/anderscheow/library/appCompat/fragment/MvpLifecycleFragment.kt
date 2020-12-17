package io.github.anderscheow.library.appCompat.fragment

import io.github.anderscheow.library.mvp.AbstractPresenter
import io.github.anderscheow.library.mvp.MvpView
import io.github.anderscheow.library.viewModel.BaseAndroidViewModel

abstract class MvpLifecycleFragment<VM : BaseAndroidViewModel<*>, V : MvpView, P : AbstractPresenter<V>> : LifecycleFragment<VM>() {

    abstract fun getMvpView(): V

    abstract fun getPresenter(): P

    override fun onDestroy() {
        getPresenter().onDetachView()
        super.onDestroy()
    }

    override fun init() {
        super.init()
        getPresenter().onAttachView(getMvpView())
    }
}
