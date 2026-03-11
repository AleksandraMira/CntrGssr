package com.example.cntrgssr.core.util

import android.content.Context
import androidx.annotation.StringRes
import timber.log.Timber
import javax.inject.Inject

class ResourceResolverImpl @Inject constructor(
    private val context: Context,
) : ResourceResolver {
    override fun getString(@StringRes stringId: Int, vararg formatArgs: Any?) = try {
        context.getString(stringId, *formatArgs)
    } catch (exception: Exception) {
        Timber.e(exception)
        ""
    }
}