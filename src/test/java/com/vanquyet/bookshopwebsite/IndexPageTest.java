package com.tuannghia.andshop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IndexPageTest {

    private WebDriver driver;

    public WebDriverWait wait;

    @BeforeAll
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
    }

    @AfterAll
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePageLoadsCorrectly() {
        driver.get("https://demoqa.com/");
        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Cửa hàng sách"), "Trang chủ phải có tiêu đề 'Cửa hàng sách'");
    }

    @Test
    public void testBuyNowButtonIsPresent() {
        driver.get("http://localhost:8080/");
        WebElement buyNowBtn = driver.findElement(By.cssSelector(".shopbtn"));
        Assertions.assertTrue(buyNowBtn.isDisplayed(), "Nút 'Mua hàng ngay' phải hiển thị");
    }

    @Test
    public void testProductListExists() {
        driver.get("http://localhost:8080/");

        WebElement bestsellerSection = driver.findElement(By.cssSelector(".wn__product__area"));
        Assertions.assertNotNull(bestsellerSection, "Phải có section sản phẩm bán chạy");

        WebElement newProductSection = driver.findElement(By.cssSelector(".wn__bestseller__area"));
        Assertions.assertNotNull(newProductSection, "Phải có section sản phẩm mới");
    }

}
