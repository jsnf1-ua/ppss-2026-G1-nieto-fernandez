package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public LoginPage signIn() {
        //<a href="/account/signonForm" class="btn btn-outline-light me-2">Sign In</a>
        WebElement signIn = driver.findElement(By.linkText("Sign In"));
        signIn.click();
        return new LoginPage(driver);
    }

    public  String welcomMess() {
        WebElement mess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-end.pb-2")));
        return mess.getText();
    }

    public MyAccountPage myAcc() {
        WebElement dropD = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdownMenuButton")));
        dropD.click();

        WebElement miAcc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("My Account")));
        miAcc.click();

        return new MyAccountPage(driver);
    }
}
