package es.codeurjc.ais.selenium;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import es.codeurjc.ais.Application;
import io.github.bonigarcia.wdm.WebDriverManager;

//import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestSelenium {

	@LocalServerPort
    int port;

	WebDriver driver;
	
	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}
	
	@AfterEach
	public void teardown() {
		if(driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void librosDrama(){
		//Given
		driver.get("http://localhost:"+this.port+"/");
		
		//When
		WebElement searchInput = driver.findElement(By.name("topic"));
		WebElement boton = driver.findElement(By.id("search-button")); 
		
		searchInput.sendKeys("drama");
		boton.click();
		
		WebElement libro = driver.findElement(By.id("Pride and Prejudice"));
		libro.click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement etiqueta = wait.until(
				presenceOfElementLocated(By.id("drama")));
		
		//Then
		assertTrue(etiqueta.getText().contains("drama"));
	}
	
	@Test
	public void librosEpicFantasyError() {
		
		//Given
		driver.get("http://localhost:"+this.port+"/");
		
		//When
		WebElement searchInput = driver.findElement(By.name("topic"));
		WebElement boton = driver.findElement(By.id("search-button"));
		
		searchInput.sendKeys("epic fantasy");
		boton.click();
		
		WebElement libro = driver.findElement(By.id("Words of Radiance"));
		libro.click();
		
		WebElement añadirReview = driver.findElement(By.id("add-review"));
		
		añadirReview.click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement mensajeError = wait.until(
				presenceOfElementLocated(By.id("error-message")));
		
		//Then
		assertTrue(mensajeError.getText().contains("Error at saving the review: empty fields"));
		assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("comment")));
	}
	
	@Test
	public void epicFantasy_topic_and_review() throws InterruptedException {
		
		//Given
		driver.get("http://localhost:"+this.port+"/");
//		@SuppressWarnings("deprecation")
//		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		String subject = "epic fantasy";
		String nickname = "Gonzalo";
		String review = "I really liked this book. Absolutely a must buy. Saludos chao chao";
		
		//When
		driver.findElement(By.name("topic")).sendKeys(subject);
		driver.findElement(By.id("search-button")).click();
		
//		wait.until(presenceOfElementLocated(By.id("The Way of Kings")));
		
		Thread.sleep(1000);
		
		WebElement bookTest = driver.findElement(By.id("The Way of Kings"));
		bookTest.click();
		
		driver.findElement(By.name("nickname")).sendKeys(nickname);
		driver.findElement(By.name("content")).sendKeys(review);
		
		Thread.sleep(3000);
		
		driver.findElement(By.id("add-review")).click();
		
		Thread.sleep(3000);
		
		//Then
		String authorTest = driver.findElement(By.className("author")).getText();
		WebElement reviewTest = driver.findElement(By.className("content"));
		
		assertThat(authorTest).isEqualTo(nickname);
		assertTrue(reviewTest.getText().contains(review));
	}
	
}
