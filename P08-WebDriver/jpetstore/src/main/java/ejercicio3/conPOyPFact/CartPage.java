package ejercicio3.conPOyPFact;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "tbody tr:nth-of-type(1) td:nth-of-type(2)")
    WebElement namecart;

    //@FindBy(css = "tfoot tr td:nth-of-type(7) strong") //Entra en el pie de tabla (tfoot), busca la fila (tr), vete a la séptima columna (td) y coge el texto en negrita (strong)
    @FindBy(xpath = "//td[contains(., 'Sub Total:')]/following-sibling::td") //Busca la celda que contiene "Sub Total:" y coge la siguiente celda
    WebElement priceCart;

    //<h3>Shopping Cart</h3>
    @FindBy(tagName = "h3")
    WebElement cartPage;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean cartpage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(cartPage)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String comp() {
        return wait.until(ExpectedConditions.visibilityOf(namecart)).getText();
    }

    public String otracomp() {
        return wait.until(ExpectedConditions.visibilityOf(priceCart)).getText();
    }
}
