package testcase;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AutomationTests {

    WebDriver driver;
    WebDriverWait wait;
    String BASE_URL = "https://demowebshop.tricentis.com/";

    @BeforeClass
    public void setup() {
        System.out.println("=== Starting Browser in HEADLESS Mode ===");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");  
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        System.out.println("=== Browser Started Successfully ===");
    }

    // ===========================================================
    // 1️⃣ TEST – Verify Home Page Loads
    // ===========================================================
    @Test(priority = 1)
    public void test01_verifyHomePage() {
        System.out.println("Running Selenium Test 1: Verifying Home Page...");
        
        driver.get(BASE_URL);
        Assert.assertTrue(driver.getTitle().contains("Demo Web Shop"));

        System.out.println("Completed Selenium Test 1: Home Page Loaded Successfully.");
    }

    // ===========================================================
    // 2️⃣ TEST – Search Product
    // ===========================================================
    @Test(priority = 2)
    public void test02_searchProduct() {
        System.out.println("Running Selenium Test 2: Searching for a product...");

        driver.get(BASE_URL);

        WebElement searchBox = driver.findElement(By.id("small-searchterms"));
        searchBox.sendKeys("laptop" + Keys.ENTER);

        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".page-title h1")
                )
        );

        Assert.assertTrue(title.getText().contains("Search"));

        System.out.println("Completed Selenium Test 2: Product search successful.");
    }

    // ===========================================================
    // 3️⃣ TEST – Add Product to Cart
    // ===========================================================
    @Test(priority = 3)
    public void test03_addProductToCart() {
        System.out.println("Running Selenium Test 3: Adding product to cart...");

        driver.get(BASE_URL);

        driver.findElement(By.id("small-searchterms")).sendKeys("laptop" + Keys.ENTER);

        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".product-item .product-title a")
                )
        );
        firstProduct.click();

        WebElement addToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-31"))
        );
        addToCartBtn.click();

        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".bar-notification.success")
                )
        );

        Assert.assertTrue(successMsg.getText().contains("The product has been added"));

        System.out.println("Completed Selenium Test 3: Product added to cart successfully.");
    }

    // ===========================================================
    // 4️⃣ TEST – User Registration
    // ===========================================================
    @Test(priority = 4)
    public void test04_registerUser() {
        System.out.println("Running Selenium Test 4: Registering new user...");

        driver.get(BASE_URL);

        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");

        String email = "testuser" + System.currentTimeMillis() + "@mail.com";
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys("Test@12345");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test@12345");

        driver.findElement(By.id("register-button")).click();

        WebElement result = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".result")
                )
        );

        Assert.assertTrue(result.getText().contains("completed"));

        System.out.println("Completed Selenium Test 4: User registered successfully.");
    }

    // ===========================================================
    // 5️⃣ TEST – Category Navigation
    // ===========================================================
    @Test(priority = 5)
    public void test05_verifyCategoryNavigation() {
        System.out.println("Running Selenium Test 5: Navigating to Books category...");

        driver.get(BASE_URL);

        driver.findElement(By.linkText("Books")).click();

        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );

        Assert.assertEquals(title.getText(), "Books");

        System.out.println("Completed Selenium Test 5: Category navigation successful.");
    }

    @AfterClass
    public void teardown() {
        System.out.println("=== Closing Browser ===");
        driver.quit();
        System.out.println("=== Browser Closed Successfully ===");
    }
}
