package io.github.anderscheow.library.di.modules

import io.github.anderscheow.library.executor.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

abstract class CommonBaseModule {

    fun provideCommonModule() = Kodein.Module("commonModule") {
        bind<ThreadExecutor>() with singleton { JobExecutor() }
        bind<PostExecutionThread>() with singleton { UIThread() }
        bind<DiskIOExecutor>() with singleton { IOExecutor() }
    }

    open fun provideAdditionalActivityModule(builder: Kodein.Builder) {
    }
}


