@file:Suppress("UNUSED")

package io.github.anderscheow.library.kotlin

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat

/**
 * Extension method to browse for Context.
 */
fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(ACTION_VIEW).apply {
            this.data = Uri.parse(url)
            if (newTask) this.addFlags(FLAG_ACTIVITY_NEW_TASK)
        }

        startActivity(intent)

        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Extension method to share for Context.
 */
fun Context.share(text: String, subject: String = ""): Boolean {
    return try {
        val intent = Intent().apply {
            this.type = "text/plain"
            this.putExtra(EXTRA_SUBJECT, subject)
            this.putExtra(EXTRA_TEXT, text)
        }

        startActivity(createChooser(intent, null))

        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

/**
 * Extension method to send email for Context.
 */
fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(ACTION_SENDTO).apply  {
        this.data = Uri.parse("mailto:")
        this.putExtra(EXTRA_EMAIL, arrayOf(email))
        if (subject.isNotBlank()) putExtra(EXTRA_SUBJECT, subject)
        if (text.isNotBlank()) putExtra(EXTRA_TEXT, text)
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)

        return true
    }

    return false
}

/**
* Extension method to make call for Context.
*/
@SuppressLint("MissingPermission")
fun Context.makeCall(number: String): Boolean {
    return try {
        val intent = Intent(ACTION_CALL, Uri.parse("tel:$number"))

        startActivity(intent)

        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Extension method to Send SMS for Context.
 */
fun Context.sendSms(number: String, text: String = ""): Boolean {
    return try {
        val intent = Intent(ACTION_VIEW, Uri.parse("sms:$number")).apply {
            this.putExtra("sms_body", text)
        }

        startActivity(intent)

        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Extension method to rate app on PlayStore for Context.
 */
fun Context.rate(packageName: String = this.packageName): Boolean =
        browse("market://details?id=$packageName") or
        browse("http://play.google.com/store/apps/details?id=$packageName")

/**
 * Extension method to check network availability
 */
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.activeNetworkInfo?.isConnected ?: false
}

/**
 * Extension method to find color based on color resource.
 */
fun Context.findColor(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

/**
 * Extension method to find drawable based on drawable resource.
 */
fun Context.findDrawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)