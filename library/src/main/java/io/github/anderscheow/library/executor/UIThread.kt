package io.github.anderscheow.library.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}

class UIThread : PostExecutionThread {
    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}