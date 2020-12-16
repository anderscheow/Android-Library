package io.github.anderscheow.library

import androidx.multidex.MultiDexApplication
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

abstract class CustomApplication : MultiDexApplication(), DIAware {

    abstract fun getCommonModule(): DI.Module

    abstract fun getActivityModule(): DI.Module

    abstract fun getFragmentModule(): DI.Module

    abstract fun importAdditionalModule(builder: DI.MainBuilder)

    override val di = DI.lazy {
        import(androidXModule(this@CustomApplication))
        import(getCommonModule())
        import(getActivityModule())
        import(getFragmentModule())

        importAdditionalModule(this)
    }
}
