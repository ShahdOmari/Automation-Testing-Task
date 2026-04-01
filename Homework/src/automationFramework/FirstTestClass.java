package automationFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

public class FirstTestClass {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver",
            "C:\\Users\\2022\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        try {
            //  Open website
            driver.get("https://certwcs.frontgate.com/?aka_bypass=5C73514EE7A609054D81DE61DD9CA3D6");

            //  Verify logo
            WebElement logo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Frontgate LOGO']"))
            );
            System.out.println("Logo displayed: " + logo.isDisplayed());

            //  Verify My Account and My Bag displayed
            WebElement myAccount = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("my-account-button"))
            );
            WebElement myBag = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-analytics-name='show_mini_cart']"))
            );
            System.out.println("My Account displayed: " + myAccount.isDisplayed());
            System.out.println("My Bag displayed: " + myBag.isDisplayed());

            //  Hover over My Account icon
            actions.moveToElement(myAccount).perform();

            //  Click Sign In / Register in my account list
            WebElement signInOption = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Sign In / Register']"))
            );
            signInOption.click();
            System.out.println("Sign In/ Register page opened!");

            // Wait for sign in page to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
            Thread.sleep(500);

            //  Close cookie banner FIRST before filling fields
            try {
                WebElement cookieClose = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@aria-label='Close' or contains(@class,'close') or @id='close-pc-btn-handler']")
                    )
                );
                cookieClose.click();
                System.out.println("Cookie closed!");
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("No cookie found");
            }

            //  Fill email 
            WebElement emailInput = driver.findElement(By.cssSelector("input#email"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", emailInput);
            Thread.sleep(400);
            js.executeScript(
                "var el = arguments[0];" +
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "nativeInputValueSetter.call(el, 'chef03@anhmaybietchoi.com');" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));",
                emailInput
            );
            System.out.println("Email entered!");

            //  Fill password 
            WebElement passwordInput = driver.findElement(By.cssSelector("input#password"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", passwordInput);
            Thread.sleep(400);
            js.executeScript(
                "var el = arguments[0];" +
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "nativeInputValueSetter.call(el, 'Shahd@123456');" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));",
                passwordInput
            );
            System.out.println("Password entered!");

            //  Scroll to Sign In button and click 
            WebElement signInButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.login-button"))
            );
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", signInButton);
            Thread.sleep(400);
            js.executeScript("arguments[0].click();", signInButton);
            System.out.println("Sign In button clicked successfully!");

            //  Wait for homepage to load after login
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("my-account-button")
            ));
          

            //  Hover over My Account to show list
            WebElement myAccountAfterLogin = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("my-account-button"))
            );
            actions.moveToElement(myAccountAfterLogin).pause(Duration.ofSeconds(2)).perform();

            //  Get welcome text and verify first name
            WebElement welcomeText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Welcome ss')]")
                )
            );

            String welcomeMessage = welcomeText.getText();
            System.out.println("Welcome message: " + welcomeMessage);

            if (welcomeMessage.contains("ss")) {
                System.out.println("Login SUCCESS");
            } else {
                System.out.println("Login FAILED");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // driver.quit();
        }
    }
}