package es.codeurjc.ais;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(
		classes = Application.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class testSelenium {
	@LocalServerPort
	int port;
	
	private WebDriver driver;

	@BeforeAll
	public static void setUpManager() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void setUpDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
	}
	
	@AfterEach
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void testDrama() {
		driver.get("http://localhost:"+this.port+"/");
		
		WebElement searchInput = driver.findElement(By.name("topic"));
		searchInput.sendKeys("drama");
		searchInput.submit();
		
		driver.findElement(By.xpath("/html/body/div[2]/div/a[1]")).click();
		assertNotNull(driver.findElement(By.id("drama")));
	}
	
	@Test
	public void testEpicFantasy() {
		driver.get("http://localhost:"+this.port+"/");
		
		WebElement searchInput = driver.findElement(By.name("topic"));
		searchInput.sendKeys("epic fantasy");
		searchInput.submit();
		
		driver.findElement(By.id("The Way of Kings")).click();
		driver.findElement(By.name("nickname")).sendKeys("usuario");
		driver.findElement(By.name("content")).sendKeys("test");
		driver.findElement(By.id("add-review")).click();
		
		String nickname = driver.findElement(By.xpath("/html/body/div[2]/div/div[17]/div[1]/div/a")).getText(); 
		String content = driver.findElement(By.xpath("/html/body/div[2]/div/div[17]/div[1]/div/div[2]")).getText();
		
		assertTrue(nickname.equals("usuario"));
		assertTrue(content.equals("test"));
	}
	
	@Test
	public void testNoReview() {
		driver.get("http://localhost:"+this.port+"/");
		
		WebElement searchInput = driver.findElement(By.name("topic"));
		searchInput.sendKeys("epic fantasy");
		searchInput.submit();
		
		driver.findElement(By.id("Words of Radiance")).click();
		driver.findElement(By.name("nickname")).sendKeys("usuario");
		driver.findElement(By.id("add-review")).click();
		
		assertNotNull(driver.findElement(By.id("error-message")));
		
		List<WebElement> reviews = driver.findElements(By.className("text"));
        if (reviews.size() > 0) {
            for(int i=0;i<reviews.size();i++) {
                assertTrue(!reviews.get(i).getText().isEmpty());
            }
        } 

	}
}
