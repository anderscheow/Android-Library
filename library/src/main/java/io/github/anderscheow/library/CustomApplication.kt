package io.github.anderscheow.library

import androidx.multidex.MultiDexApplication
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

abstract class CustomApplication : MultiDexApplication(), KodeinAware {

    abstract fun getCommonModule(): Kodein.Module

    abstract fun getActivityModule(): Kodein.Module

    abstract fun getFragmentModule(): Kodein.Module

    abstract fun importAdditionalModule(builder: Kodein.MainBuilder)

    override val kodein = Kodein.lazy {
        import(androidXModule(this@CustomApplication))
        import(getCommonModule())
        import(getActivityModule())
        import(getFragmentModule())

        importAdditionalModule(this)
    }
}
