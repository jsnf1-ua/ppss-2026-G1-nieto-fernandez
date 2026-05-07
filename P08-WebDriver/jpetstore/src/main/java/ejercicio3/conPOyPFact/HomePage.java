package ejercicio3.conPOyPFact;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    //<a href="/order/listOrders" class="btn btn-primary">My Orders</a>
    @FindBy(linkText = "My Orders")
    WebElement myOrds;

    //<a href="/categories/CATS">Cats</a>
    @FindBy(linkText = "Cats")
    WebElement cats;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getPagesTitle() {
        return driver.getTitle();
    }

    public boolean isMyOrdersButtonPresent() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(myOrds)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public CategoryPage cats() {
        wait.until(ExpectedConditions.visibilityOf(cats)).click();
        return PageFactory.initElements(driver, CategoryPage.class);
    }
}
