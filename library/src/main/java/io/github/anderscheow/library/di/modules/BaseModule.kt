package io.github.anderscheow.library.di.modules

import io.github.anderscheow.library.executor.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

abstract class BaseModule(private val moduleName: String) {

    fun provideModule() = Kodein.Module(moduleName) {
        provideAdditionalModule(this)
    }

    abstract fun provideAdditionalModule(builder: Kodein.Builder)
}

abstract class ActivityBaseModule : BaseModule("activityModule")

abstract class FragmentBaseModule : BaseModule("fragmentModule")

abstract class CommonBaseModule : BaseModule("commonModule") {

    override fun provideAdditionalModule(builder: Kodein.Builder) {
        builder.apply {
            bind<ThreadExecutor>() with singleton { JobExecutor() }
            bind<PostExecutionThread>() with singleton { UIThread() }
            bind<DiskIOExecutor>() with singleton { IOExecutor() }
        }
    }
}


