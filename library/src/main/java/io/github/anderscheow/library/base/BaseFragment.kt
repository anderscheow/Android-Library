package io.github.anderscheow.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.orhanobut.logger.Logger

@Suppress("UNUSED")
abstract class BaseFragment : FoundationFragment() {

    private var unbinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Logger.v("Fragment CREATED VIEW")
        val view = inflater.inflate(getResLayout(), container, false)

        if (requiredButterknife()) {
            unbinder = ButterKnife.bind(this, view)
        }

        return view
    }

    override fun onDestroyView() {
        Logger.v("Fragment VIEW DESTROYED")
        unbinder?.unbind()
        super.onDestroyView()
    }
}
