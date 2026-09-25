package com.courseregistration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class CourseRegistrationTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String BASE_URL =
            System.getProperty("baseUrl", "http://127.0.0.1:5501");

    @Before
    public void setUp() {

        EdgeOptions options = new EdgeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new EdgeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the login page
        driver.get(BASE_URL + "/index_0.html");
    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    // TC01 - Verify Login Page Loads
    @Test
    public void testLoginPageLoads() {

        WebElement loginForm =
                driver.findElement(By.id("loginForm"));

        assertTrue(
                "Login form should be displayed",
                loginForm.isDisplayed()
        );
    }

    // TC02 - Verify Empty Username Validation
    @Test
    public void testEmptyUsernameValidation() {

        driver.findElement(By.id("loginForm")).submit();

        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("swal2-popup")
                )
        );

        assertTrue(
                alert.getText().contains("Username Required")
        );
    }

    // TC03 - Verify Empty Password Validation
    @Test
    public void testEmptyPasswordValidation() {

        driver.findElement(By.id("username"))
                .sendKeys("username");

        driver.findElement(By.id("loginForm")).submit();

        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("swal2-popup")
                )
        );

        assertTrue(
                alert.getText().contains("Password Required")
        );
    }

    // TC04 - Verify Invalid Login
    @Test
    public void testInvalidLogin() {

        driver.findElement(By.id("username"))
                .sendKeys("wronguser");

        driver.findElement(By.id("password"))
                .sendKeys("wrongpassword");

        driver.findElement(By.id("loginForm")).submit();

        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("swal2-popup")
                )
        );

        assertTrue(
                alert.getText().contains("Sign In Failed")
        );
    }

    // TC05 - Verify Valid Login
    @Test
    public void testValidLogin() {

        driver.findElement(By.id("username"))
                .sendKeys("username");

        driver.findElement(By.id("password"))
                .sendKeys("password");

        driver.findElement(By.id("loginForm"))
                .submit();

        // Wait for successful login alert
        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("swal2-popup")
                )
        );

        assertTrue(
                alert.getText().contains("Sign In Successful")
        );

        // Click Continue button
        WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.className("swal2-confirm")
                )
        );

        continueButton.click();

        // Wait for instruction page
        wait.until(
                ExpectedConditions.urlContains("instruction.html")
        );

        assertTrue(
                driver.getCurrentUrl().contains("instruction.html")
        );
    }

    // TC06 - Verify Course Registration Options
    @Test
    public void testCourseOptionsDisplayed() {

        driver.get(BASE_URL + "/index.html");

        int courseOptions = driver
                .findElements(
                        By.cssSelector("input[name='co']")
                )
                .size();

        assertEquals(
                "Five course options should be displayed",
                5,
                courseOptions
        );
    }

    // TC07 - Verify Proceed Without Course Selection
    @Test
    public void testProceedWithoutCourseSelection() {

        driver.get(BASE_URL + "/index.html");

        driver.findElement(By.id("proceedBtn"))
                .click();

        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("customAlertOverlay")
                )
        );

        String message = driver
                .findElement(By.id("customAlertMessage"))
                .getText();

        assertTrue(
                alert.isDisplayed()
        );

        assertEquals(
                "Please select a course option before proceeding.",
                message
        );
    }

    // TC08 - Verify Compulsory Course Selection
    @Test
    public void testCompulsoryCourseSelection() {

        driver.get(BASE_URL + "/index.html");

        WebElement compulsoryCourse = driver.findElement(
                By.cssSelector(
                        "input[name='co'][value='course1']"
                )
        );

        compulsoryCourse.click();

        assertTrue(
                "Compulsory Course should be selectable",
                compulsoryCourse.isSelected()
        );

        driver.findElement(By.id("proceedBtn"))
                .click();

        wait.until(
                ExpectedConditions.urlContains("course1.html")
        );

        assertTrue(
                driver.getCurrentUrl().contains("course1.html")
        );
    }

    // TC09 - Verify Project and Internship Option
    @Test
    public void testProjectInternshipNotApplicable() {

        driver.get(BASE_URL + "/index.html");

        WebElement projectOption = driver.findElement(
                By.cssSelector(
                        "input[name='co'][value='course5']"
                )
        );

        projectOption.click();

        driver.findElement(By.id("proceedBtn"))
                .click();

        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("customAlertOverlay")
                )
        );

        String message = driver
                .findElement(By.id("customAlertMessage"))
                .getText();

        assertTrue(
                alert.isDisplayed()
        );

        assertEquals(
                "Please complete registration for all Compulsory Courses before proceeding to other categories.",
                message
        );
    }

    // TC10 - Verify Sign Out
    @Test
    public void testSignOut() {

        driver.get(BASE_URL + "/index.html");

        WebElement signOutButton =
                driver.findElement(By.cssSelector(".button_3"));

        signOutButton.click();

        wait.until(
                ExpectedConditions.urlContains("index_0.html")
        );

        assertTrue(
                driver.getCurrentUrl().contains("index_0.html")
        );
    }
}