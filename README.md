# Appium, Espresso & XCUITest Upload Module - BrowserStack <a href="https://www.browserstack.com/"><img src="https://www.vectorlogo.zone/logos/browserstack/browserstack-icon.svg" alt="BrowserStack" height="30"/></a> <a href="https://developer.android.com"><img src="https://developer.android.com/static/images/training/testing/espresso.png" alt="Java" height="30" /></a> <a href="https://developer.apple.com/documentation/xctest/user_interface_tests"><img src="https://images.ctfassets.net/czwjnyf8a9ri/2OWZnl3v2xJcqBZPIczU1s/1ea9ea383887e13d76b0b6c386ddf09c/logo-xcuitest.png?w=250" alt="XCUITest" height="30" /></a>

App and Test Suite upload module using [TestNG](http://testng.org) to upload your apps and test suites for [Espresso](https://developer.android.com) and [XCUITest](https://developer.apple.com/documentation/xctest/user_interface_tests) on BrowserStack.

## Using Maven

### Setup

- Clone the repo
- Install dependencies
  ```
  mvn compile
  ```
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

Set up your paths to your apps and test suites, custom IDs etc. See the below code snippet that shows where to update:

### Espresso and XCUITest
```java
 // Set one to true and one to false depending on the type of upload
 private val doFileUpload = true
 private val doUrlUpload = false
        
 companion object {
    // path to JSON file (does not need to be updated)
    private const val jsonFilePath = "src/test/resources/app/espresso.json"
  
    // Provide unique custom IDs (if IDs exist, it will use the
    private const val appCustomID = "MyAppCustomID"
    private const val testSuiteCustomID = "MyTestSuiteCustomID"
  
    // Provide the URLs of your app and tests files if doing upload by URL
    private const val appFileURL = "https://www.browserstack.com/app-automate/sample-apps/android/Calculator.apk"
    private const val testSuiteURL = "https://www.browserstack.com/app-automate/sample-apps/android/CalculatorTest.apk"
  
    // Set the path to the files on your machine
    private val appFile = File("/path/to/app-filename")
    private val testSuiteFile = File("/path/to/test-suite-filename")
 }
```

(This code is present at the top of [EspressoTest.java](./src/test/java/com/test/EspressoTest.java) and [XCUITest.java](./src/test/java/com/test/XCUITest.java))

### Appium
```java
// Set one to true and one to false depending on the type of upload
private val doFileUpload = true
private val doUrlUpload = false
        
companion object {
    // Provide unique custom IDs (if IDs exist, it will use the
    private const val appCustomID = "AppiumCustomID"
  
    // Provide the URLs of your app and tests files if doing upload by URL
    private const val appFileURL = "https://www.browserstack.com/app-automate/sample-apps/android/Calculator.apk"
  
    // Set the path to the files on your machine
    private val appFile = File("/path/to/apk-filename.apk")
}
```

(This code is present at the top of [AppiumTest.java](./src/test/java/com/test/AppiumtTest.java))

### Running your tests
#### Espresso tests

- Run an Espresso test.
  ```
  mvn -P espresso test
  ```
Or simply right click anywhere in the [EspressoTest.java](./src/test/java/com/test/EspressoTest.java) file and click "**Run EspressoTest**

#### XCUI tests

- Run a XCUI test.
  ```
  mvn -P xcuitest test
  ```
Or simply right click anywhere in the [XCUITest.java](./src/test/java/com/test/XCUITest.java) file and click "**Run XCUITest**  

#### Appium Tests

- Run an Appium test.
  ```
  mvn -P appiumtest test
  ```
Or simply right click anywhere in the [AppiumTest.java](./src/test/java/com/test/AppiumTest.java) file and click "**Run AppiumTest**


## Notes

- You can view the tests being run and their results on the [App Automate](https://app-automate.browserstack.com) dashboard
- The [espresso.json](./src/test/resources/app/espresso.json) and [xcuitest.json](./src/test/resources/app/xcuitest.json) files are where Devices, Sharding and other parameters can be specified / modified.
- Export the environment variables for the Username and Access Key of your BrowserStack account.
  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> && export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
