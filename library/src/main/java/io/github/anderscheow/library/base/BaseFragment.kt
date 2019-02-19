package io.github.anderscheow.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger

abstract class BaseFragment : FoundationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Logger.v("Fragment CREATED VIEW")

        return inflater.inflate(getResLayout(), container, false)
    }

    override fun onDestroyView() {
        Logger.v("Fragment VIEW DESTROYED")
        super.onDestroyView()
    }
}
