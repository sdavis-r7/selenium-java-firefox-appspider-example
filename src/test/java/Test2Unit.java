package com.rapid7;


import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Test2Unit {

    private WebDriver driver;
    private String baseScheme = "https";
    private String baseUrl = baseScheme + "://www.mozilla.org/";
//    private String proxyPort = "8080";  //gogo burpsuite
//    private String proxyUrl = "127.0.0.1";
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String FF_PROFILE = "RAPID7APPSPIDERSELENIUM";
    @Before
    public void setUp() throws Exception {
        Proxy  objProxy = new Proxy();
        DesiredCapabilities objDesiredCapabilities = DesiredCapabilities.htmlUnit();

//        objProxy.setHttpProxy(proxyUrl+":"+proxyPort);
//        objProxy.setSslProxy(proxyUrl+":"+proxyPort);
//        objDesiredCapabilities.setCapability(CapabilityType.PROXY, objProxy);
        ProfilesIni listProfiles = new ProfilesIni();
        FirefoxProfile profile = listProfiles.getProfile( FF_PROFILE );
        if( profile != null ){
            objDesiredCapabilities.setCapability(FirefoxDriver.PROFILE,profile);
            System.out.println("loaded firefox profile: "+ FF_PROFILE);
        } else {
            System.out.println("no firefox profile exists..");
            if (baseScheme.equals("https")) {
                System.exit(-1);
            } else {
                System.out.println("non-encrypted (continuing).");
            }
        }

        driver = new FirefoxDriver(objDesiredCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void test2() throws Exception {
        driver.get(baseUrl + "/en-US/");
        driver.findElement(By.cssSelector(".container")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}
