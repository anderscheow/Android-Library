package io.github.anderscheow.library.di.modules

import org.kodein.di.DI

abstract class BaseModule(private val moduleName: String) {

    fun provideModule() = DI.Module(moduleName) {
        provideAdditionalModule(this)
    }

    abstract fun provideAdditionalModule(builder: DI.Builder)
}

abstract class ActivityBaseModule : BaseModule("activityModule")

abstract class FragmentBaseModule : BaseModule("fragmentModule")

abstract class CommonBaseModule : BaseModule("commonModule")


