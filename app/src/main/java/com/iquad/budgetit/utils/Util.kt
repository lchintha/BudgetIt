package com.iquad.budgetit.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

const val playStoreUrl = "https://play.google.com/store/apps/details?id=com.vrcmicrotech.aproundup&hl=en_US"
const val email = "chintap94@gmail.com"
const val tncUrl = "file:///android_asset/terms_conditions.html"
const val privacyPolicyUrl = "file:///android_asset/privacy_policy.html"

object Util {
    //Launch URL in browser
    fun launchUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            GlobalStaticMessage.show(
                message = "No Browser Found",
                messageType = MessageType.FAILURE
            )
        }
    }

    //Share the app
    fun shareApp(context: Context) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, playStoreUrl)
            type = "text/plain"
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            GlobalStaticMessage.show(
                message = "No App Found",
                messageType = MessageType.FAILURE
            )
        }
    }

    //Send email
    fun sendEmail(context: Context) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback on BudgetIt")
        }
        if (context.packageManager.resolveActivity(intent, 0) != null) {
            context.startActivity(intent)
        } else {
            GlobalStaticMessage.show(
                message = "No App Found to send feedback",
                messageType = MessageType.FAILURE
            )
        }
    }
}