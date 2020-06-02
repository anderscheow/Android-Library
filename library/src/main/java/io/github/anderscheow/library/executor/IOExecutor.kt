package io.github.anderscheow.library.executor

import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface DiskIOExecutor {
    fun getScheduler(): Executor
}

class IOExecutor : DiskIOExecutor {
    override fun getScheduler(): Executor {
        return Executors.newSingleThreadExecutor()
    }
}