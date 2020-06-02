package io.github.anderscheow.library.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

interface ThreadExecutor {
    fun getScheduler(): Scheduler
}

class JobExecutor : ThreadExecutor {
    override fun getScheduler(): Scheduler {
        return Schedulers.io()
    }
}