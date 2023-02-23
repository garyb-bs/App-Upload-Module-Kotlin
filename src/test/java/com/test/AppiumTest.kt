package com.test

import org.testng.annotations.BeforeSuite
import com.test.utils.AppUtils
import com.test.AppiumTest
import org.testng.annotations.Test
import java.io.File

class AppiumTest {
    // Set one to true and one to false depending on the type of upload
    private val doFileUpload = true
    private val doUrlUpload = false
    @BeforeSuite
    fun setup() {
        AppUtils.setupAuthPrep("app-automate")
    }

    @Test
    fun uploadApp() {
        AppUtils.uploadAppiumApp(appCustomID, doFileUpload, doUrlUpload, appFile, appFileURL)
    }

    companion object {
        // Provide unique custom IDs (if IDs exist, it will use the
        private const val appCustomID = "AndroidCustomIDTest-20-02-2023"

        // Provide the URLs of your app and tests files if doing upload by URL
        private const val appFileURL = "https://www.browserstack.com/app-automate/sample-apps/android/Calculator.apk"

        // Set the path to the files on your machine
        private val appFile = File("/Users/garybehan/Downloads/app-debug.apk")
    }
}