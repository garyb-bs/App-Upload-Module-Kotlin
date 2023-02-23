package com.test

import org.testng.annotations.BeforeSuite
import com.test.utils.AppUtils
import org.testng.annotations.BeforeTest
import com.test.XCUITest
import kotlin.Throws
import io.restassured.RestAssured
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File
import java.lang.Exception

class XCUITest {
    // Set one to true and one to false depending on the type of upload
    private val doFileUpload = false
    private val doUrlUpload = true
    @BeforeSuite
    fun setup() {
        AppUtils.setupAuthPrep("app-automate/xcuitest/v2")
    }

    @BeforeTest
    fun uploadAppAndTestSuite() {
        AppUtils.uploadApp(appCustomID, doFileUpload, doUrlUpload, appFile, appFileURL)
        AppUtils.uploadTestSuite(testSuiteCustomID, doFileUpload, doUrlUpload, testSuiteFile, testSuiteURL)
    }

    @Test
    @Throws(Exception::class)
    fun xcuiTest() {
        AppUtils.updateJSON(appCustomID, testSuiteCustomID, jsonFilePath)
        println("Executing test suite...")
        val message = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(File("src/test/resources/app/xcuitest.json"))
                .post("build")
                .jsonPath()
                .get<String>("message")
        Assert.assertEquals(message, "Success", "Build did not start")
    }

    companion object {
        // path to JSON file (does not need to be updated)
        private const val jsonFilePath = "src/test/resources/app/xcuitest.json"

        // Provide unique custom IDs (if IDs exist, it will use the
        private const val appCustomID = "XCUIAppCustomID"
        private const val testSuiteCustomID = "XCUITestSuiteCustomID"

        // Provide the URLs of your app and tests files if doing upload by URL
        private const val appFileURL = "https://www.browserstack.com/app-automate/sample-apps/ios/BrowserStack-SampleApp.ipa"
        private const val testSuiteURL = "https://www.browserstack.com/app-automate/sample-apps/ios/BrowserStack-SampleXCUITest.zip"

        // Set the path to the files on your machine
        private val appFile = File("/path/to/ipa-filename.ipa")
        private val testSuiteFile = File("/path/to/test-suite-zip.zip")
    }
}