package io.github.anderscheow.library.appCompat.activity

import android.os.Bundle

abstract class BaseActivity : FoundationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initBeforeSuperOnCreate()

        super.onCreate(savedInstanceState)

        setContentView(getResLayout())

        getToolbar()?.let { toolbar ->
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
        }

        init(savedInstanceState)
    }
}
