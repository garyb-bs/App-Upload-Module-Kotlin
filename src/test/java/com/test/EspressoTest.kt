package com.test

import org.testng.annotations.BeforeSuite
import com.test.utils.AppUtils
import org.testng.annotations.BeforeTest
import com.test.EspressoTest
import kotlin.Throws
import io.restassured.RestAssured
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File
import java.lang.Exception

class EspressoTest {
    // Set one to true and one to false depending on the type of upload
    private val doFileUpload = true
    private val doUrlUpload = false
    @BeforeSuite
    fun setup() {
        AppUtils.setupAuthPrep("app-automate/espresso/v2")
    }

    @BeforeTest
    fun uploadAppAndTestSuite() {
        AppUtils.uploadApp(appCustomID, doFileUpload, doUrlUpload, appFile, appFileURL)
        AppUtils.uploadTestSuite(testSuiteCustomID, doFileUpload, doUrlUpload, testSuiteFile, testSuiteURL)
    }

    @Test
    @Throws(Exception::class)
    fun espressoTest() {
        AppUtils.updateJSON(appCustomID, testSuiteCustomID, jsonFilePath)
        println("Executing test suite ...")
        val message = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(File(jsonFilePath))
                .post("build")
                .jsonPath()
                .get<String>("message")
        Assert.assertEquals(message, "Success", "Build did not start")
    }

    companion object {
        // path to JSON file (does not need to be updated)
        private const val jsonFilePath = "src/test/resources/app/espresso.json"

        // Provide unique custom IDs (if IDs exist, it will use the
        private const val appCustomID = "MyCustomAppID-23-02-2023-14-01"
        private const val testSuiteCustomID = "MyCustomTestID-23-02-2023-14-01"

        // Provide the URLs of your app and tests files if doing upload by URL
        private const val appFileURL = "https://www.browserstack.com/app-automate/sample-apps/android/Calculator.apk"
        private const val testSuiteURL = "https://www.browserstack.com/app-automate/sample-apps/android/CalculatorTest.apk"

        // Set the path to the files on your machine
        private val appFile = File(System.getProperty("user.dir") + "/apps/app-debug.apk")
        private val testSuiteFile = File(System.getProperty("user.dir") + "/apps/CalculatorTest.apk")
    }
}