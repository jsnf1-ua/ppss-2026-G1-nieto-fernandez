package ejercicio2.conPO;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class TestLogin2 {
    private WebDriver driver;
    private HomePage homePage;

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
        driver.get("https://jpetstore.aspectran.com");

        homePage = new HomePage(driver);
    }

    @Test
    public void R3_requierement_PO_loginOK_should_login_with_success_when_user_account_exists() {
        String title = homePage.getPageTitle();
        Assertions.assertEquals("JPetStore Demo", title);

        LoginPage loginPage = homePage.signIn();

        Assertions.assertEquals("Please enter your username and password.", loginPage.loginForm());

        homePage = loginPage.login("j2ee", "j2ee");

        String bienvenida = homePage.welcomMess();

        MyAccountPage myAccountPage = homePage.myAcc();

        Assertions.assertEquals("User Information", myAccountPage.getUserInfo());

        Assertions.assertEquals("Welcome " + myAccountPage.getActName() + "!", bienvenida);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
