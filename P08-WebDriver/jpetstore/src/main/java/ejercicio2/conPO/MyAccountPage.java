package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {
    WebDriver driver;
    WebDriverWait wait;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getUserInfo() {
        WebElement userInf = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form h3")));
        return userInf.getText();
    }

    public String getActName() {
        WebElement name = driver.findElement(By.name("firstName"));
        return name.getDomProperty("value");
    }
}
