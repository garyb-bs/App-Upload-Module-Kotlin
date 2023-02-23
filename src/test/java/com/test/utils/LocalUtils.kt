package com.test.utils

import com.browserstack.local.Local
import com.test.utils.LocalUtils
import java.lang.Exception
import java.util.HashMap
import java.lang.RuntimeException

object LocalUtils {
    private var local: Local? = null
    private val ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY")
    fun startLocal() {
        local = Local()
        val options: MutableMap<String, String> = HashMap()
        options["key"] = ACCESS_KEY
        try {
            local!!.start(options)
        } catch (e: Exception) {
            throw RuntimeException("Unable to start a local connection", e)
        }
        println("Local testing connection established")
    }

    fun stopLocal() {
        try {
            local!!.stop()
        } catch (e: Exception) {
            throw RuntimeException("Unable to stop the local connection", e)
        }
        println("Local testing connection terminated")
    }
}