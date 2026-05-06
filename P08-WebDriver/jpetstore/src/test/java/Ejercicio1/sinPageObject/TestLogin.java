package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import java.time.Duration;

public class TestLogin {

    private WebDriverWait wait;
    private WebDriver driver;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //driver = new ChromeDriver();
        driver.get("https://jpetstore.aspectran.com");
    }

    @Test
    public void R1_requirement_loginOK_should_login_with_success_when_user_account_exists() {
        Assertions.assertEquals("JPetStore Demo", driver.getTitle());

        WebElement signin = driver.findElement(By.linkText("Sign In"));
        signin.click();

        //<h5 class="card-title">Please enter your username and password.</h5> //tb cpath y tagname si se sabe q es el único encabezado h5
        WebElement mess = driver.findElement(By.cssSelector("h5.card-title"));
        Assertions.assertEquals("Please enter your username and password.", mess.getText());

        //<input type="text" id="username" name="username" value="j2ee" class="form-control">
        WebElement name = driver.findElement(By.id("username"));
        name.clear();
        name.sendKeys("j2ee");

        //<input type="password" id="password" name="password" value="j2ee" class="form-control">
        WebElement pass = driver.findElement(By.id("password"));
        pass.clear();
        pass.sendKeys("j2ee");

        //<button type="submit" class="btn btn-primary">Login</button> //Boton de login
        WebElement login = driver.findElement(By.cssSelector("button.btn.btn-primary"));
        login.click();

        //<i class="bi bi-person-circle"></i> Welcome Alyssa!
        WebElement welcome = driver.findElement(By.cssSelector("div.text-end.pb-2"));
        String welcomeMess = welcome.getText();

        /*<a class="btn btn-secondary dropdown-toggle show" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="true" title="My Account">
        dropDown.selectByVisibleText("My Account"); ESTO SOLO VA CON <select> y <option> **************************************************************************************************************************/
        WebElement dropDown = driver.findElement(By.id("dropdownMenuButton"));
        dropDown.click();

        //<a class="dropdown-item" href="/account/editAccountForm">My Account</a>
        //WebElement myAcc = driver.findElement(By.cssSelector("form h3"));
        //myAcc.click();
        WebElement myAcc = driver.findElement(By.linkText("My Account"));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", myAcc); //sin esto no va

        /*<form method="post" action="/account/editAccount">
            <h3>User Information</h3> tb se puede tag name y xpath*/
        WebElement userInfo = driver.findElement(By.cssSelector("form h3")); // elh3 es hijo directo delform, no hay nada en medio
        Assertions.assertEquals("User Information", userInfo.getText());

        //<input type="text" name="firstName" value="Alyssa" class="form-control">
        WebElement infoName = driver.findElement(By.name("firstName"));

        //infoName.getText(); // NO PQ GETTEXT ES PARA TEXT ENTRE 2 ETIQUETAS
        String sInfoName = infoName.getDomProperty("value");
        Assertions.assertTrue(welcomeMess.contains(sInfoName));
    }

    @Disabled
    @Test
    public void R2_requirement_loginFailed_should_fail_when_user_account_not_exists(){
        Assertions.assertEquals("JPetStore Demo", driver.getTitle());

        //<a href="/account/newAccountForm" class="btn btn-secondary">Sign Up</a>
        //WebElement signIn = driver.findElement(By.cssSelector("a.btn.btn-secondary")); ESTE ES MUY GENERICO.
        WebElement signIn = driver.findElement(By.linkText("Sign In"));
        signIn.click();

        //<h5 class="card-title">Please enter your username and password.</h5>
        WebElement formTitle = driver.findElement(By.cssSelector("h5.card-title"));
        Assertions.assertEquals("Please enter your username and password.", formTitle.getText());

        //<input type="text" id="username" name="username" value="j2ee" class="form-control">
        WebElement name = driver.findElement(By.id("username"));
        name.clear();
        name.sendKeys("j2ee");

        //<input type="password" id="password" name="password" value="j2ee" class="form-control">
        WebElement pass = driver.findElement(By.id("password"));
        pass.sendKeys("123");

        //<button type="submit" class="btn btn-primary">Login</button>
        WebElement login = driver.findElement(By.cssSelector("button.btn.btn-primary"));
        login.click();

        //<div class="alert alert-danger mt-3"> "Invalid username or password.  Signon failed..." </div>
        WebElement messErr = driver.findElement(By.cssSelector("div.alert.alert-danger.mt-3"));
        Assertions.assertEquals("Invalid username or password. Signon failed.", messErr.getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

