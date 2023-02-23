package com.test.utils

import io.restassured.RestAssured
import io.restassured.authentication.PreemptiveBasicAuthScheme
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.response.Response
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.Executor
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.json.JSONObject
import java.io.*
import java.nio.charset.StandardCharsets


object AppUtils {
    private var response: Response? = null
    fun setupAuthPrep(basePath: String?) {
        val authenticationScheme = PreemptiveBasicAuthScheme()
        authenticationScheme.userName = System.getenv("BROWSERSTACK_USERNAME")
        authenticationScheme.password = System.getenv("BROWSERSTACK_ACCESS_KEY")
        RestAssured.requestSpecification = RequestSpecBuilder()
                .setBaseUri("https://api-cloud.browserstack.com")
                .setBasePath(basePath)
                .setAuth(authenticationScheme)
                .build()
        RestAssured.responseSpecification = ResponseSpecBuilder()
                .expectStatusCode(200)
                .build()
    }

    @Throws(Exception::class)
    fun updateJSON(appCustomID: String?, testSuiteCustomID: String?, filePath: String?) {
        val f = File(filePath)
        if (f.exists()) {
            val `is`: InputStream = FileInputStream(filePath)
            val jsonTxt = IOUtils.toString(`is`, StandardCharsets.UTF_8)
            //System.out.println(jsonTxt);
            val json = JSONObject(jsonTxt)
            val a = json.getString("app")
            println(a)
            json.put("app", appCustomID)
            json.put("testSuite", testSuiteCustomID)
            val writer = BufferedWriter(FileWriter(filePath))
            writer.write(json.toString())
            writer.close()
        }
    }

    fun uploadAppiumApp(appCustomID: String, fileUpload: Boolean, urlUpload: Boolean, appFile: File, appFileURL: String) {
        val customIds = RestAssured.get("recent_apps/$appCustomID").jsonPath().getList<String>("custom_id")
        if (customIds == null) {
            println("Uploading app ...")
            if (fileUpload && !urlUpload) {
                doFileUpload(appFile, appCustomID, "upload")
            } else if (!fileUpload && urlUpload) {
                doURLUpload(appFileURL, appCustomID, "upload")
            }
        } else {
            println("Using previously uploaded app...")
        }
    }

    fun uploadApp(appCustomID: String, fileUpload: Boolean, urlUpload: Boolean, appFile: File, appFileURL: String) {
        val appCustomIds = RestAssured.get("apps").jsonPath().getList<String>("apps.custom_id")
        if (appCustomIds.isEmpty() || !appCustomIds.contains(appCustomID)) {
            println("Uploading app ...")
            if (fileUpload && !urlUpload) {
                doFileUpload(appFile, appCustomID, "app")
            } else if (!fileUpload && urlUpload) {
                doURLUpload(appFileURL, appCustomID, "app")
            }
        } else {
            println("Using previously uploaded app...")
        }
    }

    fun uploadTestSuite(testSuiteCustomID: String, fileUpload: Boolean, urlUpload: Boolean, testSuiteFile: File, testSuiteURL: String) {
        val testSuiteCustomIds = RestAssured.get("test-suites").jsonPath().getList<String>("test_suites.custom_id")
        if (testSuiteCustomIds.isEmpty() || !testSuiteCustomIds.contains(testSuiteCustomID)) {
            println("Uploading test suite ...")
            if (fileUpload && !urlUpload) {
                doFileUpload(testSuiteFile, testSuiteCustomID, "test-suite")
            } else if (!fileUpload && urlUpload) {
                doURLUpload(testSuiteURL, testSuiteCustomID, "test-suite")
            }
        } else {
            println("Using previously uploaded test suite...")
        }
    }

    private val appUrl: String
        private get() {
            val json = response!!.body.jsonPath()
            println("App Url: " + json.get("app_url"))
            return json.get("app_url")
        }

    private val testSuiteUrl: String
        private get() {
            val json = response!!.body.jsonPath()
            println("Test Suite Url: " + json.get("test_suite_url"))
            return json.get("test_suite_url")
        }

    private fun doURLUpload(url: String, customID: String, app: String) {
        println("In URL upload");
        response = RestAssured.given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("url", url, "text")
                .param("custom_id", customID)
                .post(app)

        if (app == "test-suite") {
            testSuiteUrl
        } else if (app == "app") {
            appUrl
        }
    }

    private fun doFileUpload(fileToUpload: File, customID: String, app: String) {
        println("In file upload");
        response = RestAssured.given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", fileToUpload)
                .param("custom_id", customID)
                .post(app)

        if (app == "test-suite") {
            testSuiteUrl
        } else if (app == "app") {
            appUrl
        }
    }

    private fun runBashScript() {
        val cmd = CommandLine("path/to/shellscript.sh")
        cmd.addArgument("foo")
        cmd.addArgument("bar")

        val exec: Executor = DefaultExecutor()
        exec.setWorkingDirectory(FileUtils.getUserDirectory())
        exec.execute(cmd)
    }

}