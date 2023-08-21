package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativePathTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    void wrongName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Bruce Wayne");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void wrongPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Брюс Уэйн");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790123456781");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void emptyName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText());
    }
    @Test
    void emptyNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Брюс Уэйн");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void withoutCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Брюс Уэйн");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",
                driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getText());
    }

}
