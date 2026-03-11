package com.example.cntrgssr.core.util

import androidx.annotation.StringRes

interface ResourceResolver {
    fun getString(@StringRes stringId: Int, vararg formatArgs: Any?): String
}