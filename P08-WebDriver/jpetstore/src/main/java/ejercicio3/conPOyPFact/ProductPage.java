package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    //Mirar CategoryPage buscar el primerlink esa es la estructura
    //@FindBy(linkText = "Tailless Manx") solo va con etiquetas <a>
    //@FindBy(xpath = "//td[contains(text(), 'Tailless Manx')]") el contain text fallaba pq haya saltos de linea
    //@FindBy(xpath = "//td[normalize-space()='Tailless Manx']") /// normalize busca por palabras clave
    //@FindBy(css = "table tr:nth-child(2) td:nth-child(3)") // No tiene a q no es un enlace!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    @FindBy(css = "tbody tr:nth-of-type(1) td:nth-of-type(2)")
    WebElement first;

    //Mirar CategoryPage buscar el primerlink esa es la estructura
    //@FindBy(css = "table tr:nth-child(2) td:nth-child(4)")
    @FindBy(css = "tbody tr:nth-of-type(1) td:nth-of-type(4)")
    WebElement price;

    //<a class="dropdown-item" href="/categories/DOGS">Dogs</a> SE PUEDE BUSCAR ELLinK EN LA PAGina SIN DAR AL DROPDOWN*****************************************
    @FindBy(css = "a.dropdown-item[href='/categories/DOGS']") //A diferencia de nth-child, el nth-of-type ignora los espacios en blanco y solo cuenta las etiquetas reales
    WebElement linkDogs;

    //<a class="btn btn-primary btn-sm" href="/cart/addItemToCart?itemId=EST-14">Add to Cart</a>
    @FindBy(linkText = "Add to Cart")
    WebElement addToCart;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public List<String> saveData() {
        List<String> info = new ArrayList<>();

        info.add(wait.until(ExpectedConditions.visibilityOf(first)).getText());
        info.add(price.getText());

        return info;
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public CategoryPage openDogsInNewTab() { //***********************************************************************************************
        // Extraemos la URL del enlace
        String url = linkDogs.getDomProperty("href");
        // abrir pestaña y saltar a ella
        // El driver ya queda enfocado en la nueva pestaña automáticamente
        driver.switchTo().newWindow(WindowType.TAB);
        // Navegamos a la URL de Dogs
        driver.get(url);
        return PageFactory.initElements(driver, CategoryPage.class);
    }

    public CartPage addToCart() {
        wait.until(ExpectedConditions.visibilityOf(addToCart)).click();
        return PageFactory.initElements(driver, CartPage.class);
    }
}
