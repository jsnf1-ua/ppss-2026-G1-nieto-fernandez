package ejercicio3.conPOyPFact;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoryPage {
    WebDriver driver;
    WebDriverWait wait;

    //<h3>Cats</h3>
    //@FindBy(linkText = "Cats") eso es para hrefs
    @FindBy(tagName = "h3")
    WebElement catsdogspages;

    //<tr>
    //            <td>
    //                <a href="/products/K9-BD-01/items/EST-6">EST-6</a>
    //            </td>
    //            <td>K9-BD-01</td>
    //            <td>
    //                Male Adult Bulldog
    //            </td>
    //            <td>$18.50</td>
    //            <td class="text-end">
    //                <a class="btn btn-primary btn-sm" href="/cart/addItemToCart?itemId=EST-6">Add to Cart</a>
    //            </td>
    //        </tr>
    @FindBy(css = "table tr:nth-child(2) td:nth-child(1) a") //tr: tabla, nth-child(2) 2 fila pq la primera suelen ser los encabezados, td:nth-child(1) dentro de esa fila la 1 celda, a pq busca el enlace de la etiq a.
    WebElement firstElem;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean page() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(catsdogspages)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public ProductPage selectfirst() {
        wait.until(ExpectedConditions.visibilityOf(firstElem)).isDisplayed();
        firstElem.click();
        return PageFactory.initElements(driver, ProductPage.class);
    }
}
