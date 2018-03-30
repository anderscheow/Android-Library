package io.github.anderscheow.library.base

import android.os.Bundle
import butterknife.ButterKnife

abstract class BaseAppCompatActivity : FoundationAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initBeforeSuperOnCreate.invoke()

        super.onCreate(savedInstanceState)

        setContentView(getResLayout())
        ButterKnife.bind(this)

        getToolbar()?.let {
            setSupportActionBar(getToolbar())

            supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
        }

        init()
    }
}
