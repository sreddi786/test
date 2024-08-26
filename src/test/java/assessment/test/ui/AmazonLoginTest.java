package assessment.test.ui;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class AmazonLoginTest {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set up ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Initialize WebDriver
        driver = new ChromeDriver();

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Maximize the browser window
        driver.manage().window().maximize();

        // Navigate to the Amazon login page
        driver.get("https://www.amazon.com/");
    }

    @Test
    public void testSuccessfulLogin() {
    	driver.findElement(By.xpath("//span[text()='Account & Lists']")).click();
        // Locate and fill the email/phone number field
        WebElement emailField = driver.findElement(By.id("ap_email"));
        emailField.sendKeys("test@example.com"); // Replace with a valid email

        // Click on the Continue button
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        // Locate and fill the password field
        WebElement passwordField = driver.findElement(By.id("ap_password"));
        passwordField.sendKeys("valid_password"); 

        // Click on the Sign-In button
        WebElement signInButton = driver.findElement(By.id("signInSubmit"));
        signInButton.click();

        String expectedTitle = "Your Account";
        Assert.assertTrue(driver.getTitle().contains(expectedTitle), "Login failed: Title does not contain 'Your Account'");
    }

    @AfterClass
    public void teardown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
    
    
}

