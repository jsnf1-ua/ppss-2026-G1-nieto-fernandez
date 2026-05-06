package ejercicio2.conPO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver= driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String loginForm() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title"))).getText();
    }

    public HomePage login(String usname,String uspass) {
        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        name.clear();
        name.sendKeys(usname);

        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        pass.clear();
        pass.sendKeys(uspass);

        pass.submit(); //en vez de buscar el boton de sign in lo busca el submit.

        return new HomePage(driver);
    }
}
