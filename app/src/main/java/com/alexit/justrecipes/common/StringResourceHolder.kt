package com.alexit.justrecipes.common

import android.content.Context
import androidx.annotation.StringRes

sealed class StringResourceHolder {
    data object Empty: StringResourceHolder()
    data class DynamicString(val value: String) : StringResourceHolder()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : StringResourceHolder()

    // Метод для получения строки в Activity/Fragment или Compose
    fun asString(context: Context): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}