package ejercicio3.conPOyPFact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class TestCompareProducts {
    WebDriver driver;
    HomePage homePage;
    final static String cookies = "cookies.data";
    final static String url = "https://jpetstore.aspectran.com";

    @BeforeAll //una vez antes de todos los tests
    public static void createCookies() {
        Cookies.storeCookiesToFile(url, "j2ee", "j2ee", cookies);
    }

    @BeforeEach
    public void setUp() {
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();

        // options.addArguments("--headless=new"); PAra no ver el navegador.

        options.addArguments("--disable-features=SafeBrowsingPasswordCheck");

        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options); //PAra ver el navegador en WSL
        Cookies.loadCookiesFromFile(driver, "https://jpetstore.aspectran.com", cookies);

        homePage = new HomePage(driver);
    }

    @Test
    public void R5_requirement_compareProducts_And_BuySelectedOne() {
        Assertions.assertEquals("JPetStore Demo", homePage.getPagesTitle());

        Assertions.assertTrue(homePage.isMyOrdersButtonPresent());

        CategoryPage categoryPage = homePage.cats();

        Assertions.assertTrue(categoryPage.page());

        ProductPage productPage = categoryPage.selectfirst();

        List<String> catInfo = productPage.saveData();

        String ventanaGato = driver.getWindowHandle();

        categoryPage = productPage.openDogsInNewTab();

        Assertions.assertTrue(categoryPage.page());

        productPage = categoryPage.selectfirst();

        List<String> dogInfo = productPage.saveData();

        Assertions.assertNotEquals(dogInfo.getFirst(), catInfo.getFirst());

        driver.close();
        driver.switchTo().window(ventanaGato);

        CartPage cartPage = productPage.addToCart();

        Assertions.assertTrue(cartPage.cartpage());

        Assertions.assertEquals(catInfo.getFirst(), cartPage.comp());

        Assertions.assertEquals(catInfo.getLast(), cartPage.otracomp());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
