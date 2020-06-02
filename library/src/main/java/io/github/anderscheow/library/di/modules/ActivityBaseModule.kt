package io.github.anderscheow.library.di.modules

import org.kodein.di.Kodein

abstract class ActivityBaseModule {

    fun provideActivityModule() = Kodein.Module("activityModule") {
        provideAdditionalActivityModule(this)
    }

    open fun provideAdditionalActivityModule(builder: Kodein.Builder) {
    }
}


