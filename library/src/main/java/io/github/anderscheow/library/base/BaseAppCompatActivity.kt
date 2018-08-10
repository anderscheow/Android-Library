package io.github.anderscheow.library.base

import android.os.Bundle

abstract class BaseAppCompatActivity : FoundationAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initBeforeSuperOnCreate.invoke()

        super.onCreate(savedInstanceState)

        setContentView(getResLayout())

        getToolbar()?.let {
            setSupportActionBar(getToolbar())

            supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
        }

        init()
    }
}
