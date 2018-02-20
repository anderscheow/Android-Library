package io.github.anderscheow.library.base

import android.os.Bundle
import butterknife.ButterKnife

abstract class BaseAppCompatActivity : FoundationAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initializer.invoke()

        super.onCreate(savedInstanceState)

        setContentView(resLayout)
        ButterKnife.bind(this)

        toolbar?.let {
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
        }

        init()
    }
}
