package ru.riton.ru.riton.config

import org.slf4j.MDC

object Context {
    private const val REQUEST_ID = "Request-ID"

    var requestId: String?
        get() = contextHolder.get().requestId
        set(value) {
            contextHolder.get().requestId = value
            MDC.put(REQUEST_ID, value)
        }


    private val contextHolder = object : InheritableThreadLocal<ContextValue>() {
        override fun initialValue(): ContextValue {
            return ContextValue()
        }
    }

    fun clearContext() {
        contextHolder.remove()
        MDC.clear()
    }

    private class ContextValue {
        var requestId: String? = null
    }
}
