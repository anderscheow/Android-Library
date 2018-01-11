package io.github.anderscheow.library.base.live

import android.arch.lifecycle.LiveData

class CustomLiveData<T> : LiveData<T>() {

    fun assignValue(t: T) {
        value = t
    }
}
