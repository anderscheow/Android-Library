@file:Suppress("UNUSED", "UNCHECKED_CAST")

package io.github.anderscheow.library.kotlin

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 *  Redirect to user to specific uri and try fallbackUri if failed
 */
fun Context.redirectTo(uri: Uri,
                       fallbackUri: Uri?,
                       intentAction: String = Intent.ACTION_VIEW,
                       packageName: String? = null) {
    try {
        startActivity(Intent(intentAction, uri).apply {
            packageName?.let {
                this.`package` = it
            }
        })
    } catch (e: ActivityNotFoundException) {
        fallbackUri?.let {
            startActivity(Intent(intentAction, it))
        }
    }
}

/**
*  Redirect to user to specific url and try fallbackUrl if failed
*/
fun Context.redirectTo(url: String,
                       fallbackUrl: String?,
                       intentAction: String = Intent.ACTION_VIEW,
                       packageName: String? = null) {
    this.redirectTo(
            uri = Uri.parse(url),
            fallbackUri = Uri.parse(fallbackUrl),
            intentAction = intentAction,
            packageName = packageName
    )
}