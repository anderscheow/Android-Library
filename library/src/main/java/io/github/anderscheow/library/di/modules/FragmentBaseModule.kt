package io.github.anderscheow.library.di.modules

import org.kodein.di.Kodein

abstract class FragmentBaseModule {

    fun provideFragmentModule() = Kodein.Module("fragmentModule") {
        provideAdditionalFragmentModule(this)
    }

    open fun provideAdditionalFragmentModule(builder: Kodein.Builder) {
    }
}
