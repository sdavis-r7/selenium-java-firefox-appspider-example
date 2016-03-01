package com.rapid7;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class TestFirefoxSelenium {

    private WebDriver driver;
    private String baseScheme = "https";
    private String baseUrl = baseScheme + "://www.mozilla.org/";
    private String proxyPort = "32768";
    private String proxyUrl = "127.0.0.1";
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String FF_PROFILE = "RAPID7APPSPIDERSELENIUM";

    public void setUp() throws Exception {

        Proxy  objProxy = new Proxy();
        DesiredCapabilities objDesiredCapabilities = DesiredCapabilities.htmlUnit();

        objProxy.setHttpProxy(proxyUrl+":"+proxyPort);
        objProxy.setSslProxy(proxyUrl+":"+proxyPort);
        objDesiredCapabilities.setCapability(CapabilityType.PROXY, objProxy);

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
                System.out.println("non-encrypted (continue).");
            }
        }

        driver = new FirefoxDriver(objDesiredCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        try {
            TestFirefoxSelenium t2 = new TestFirefoxSelenium();
            t2.setUp();
            t2.test2();
            t2.tearDown();
        } catch(Exception e){
            System.out.print(e.toString());
        }
    }

    public void test2() throws Exception {
        driver.get(baseUrl + "/en-US/");
        driver.findElement(By.cssSelector("span.toggle")).click();
        driver.findElement(By.cssSelector("span.toggle.open")).click();
    }

    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

}
