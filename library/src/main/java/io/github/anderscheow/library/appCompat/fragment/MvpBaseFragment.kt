package io.github.anderscheow.library.appCompat.fragment

import io.github.anderscheow.library.mvp.AbstractPresenter
import io.github.anderscheow.library.mvp.MvpView

abstract class MvpBaseFragment<V : MvpView, P : AbstractPresenter<V>> : BaseFragment() {

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
