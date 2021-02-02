package io.github.anderscheow.library.appCompat.activity

import android.os.Bundle
import io.github.anderscheow.library.mvp.AbstractBasePresenter
import io.github.anderscheow.library.mvp.BaseView

abstract class MvpBaseActivity<V : BaseView, P : AbstractBasePresenter<V>> : BaseActivity() {

    abstract fun getMvpView(): V

    abstract fun getPresenter(): P

    override fun onStart() {
        super.onStart()
        getPresenter().onStart()
    }

    override fun onResume() {
        super.onResume()
        getPresenter().onResume()
    }

    override fun onPause() {
        getPresenter().onPause()
        super.onPause()
    }

    override fun onDestroy() {
        getPresenter().onDestroy()
        getPresenter().onDetachView()
        super.onDestroy()
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        getPresenter().onAttachView(getMvpView())
    }
}
