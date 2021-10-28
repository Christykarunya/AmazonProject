package week4.projects;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Amazon {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		ChromeDriver driver=new ChromeDriver(options);
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//Actions builder= new Actions(driver);
		
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("oneplus 9 pro");
		driver.findElement(By.id("nav-search-submit-button")).click();
		
		driver.findElement(By.xpath("//span[text()='OnePlus 9 Pro 5G (Morning Mist, 12GB RAM, 256GB Storage)']")).click();
		
		Set<String> winSet = driver.getWindowHandles();
		List<String> winList= new ArrayList<String>(winSet);
		driver.switchTo().window(winList.get(1));
		
		Thread.sleep(2000);
		String ratingCount = driver.findElement(By.xpath("(//span[@id='acrCustomerReviewText'])[1]")).getText();
		System.out.println("No. of Rating :" +ratingCount);
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//span[@id='acrPopover']//i)[1]")).click();
		//builder.moveToElement(starRating).perform();
		
		String ratingFive = driver.findElement(By.xpath("(//a[contains(@title,'reviews have 5 stars')])[6]")).getText();
		System.out.println("Rating percentage for 5star :" +ratingFive);
		
		String mrp = driver.findElement(By.xpath("//span[@id='listPriceLegalMessage']/preceding-sibling::span")).getText();
		System.out.println("MRP of Mobile :" +mrp);
		
		WebElement image = driver.findElement(By.xpath("//div[@id='imgTagWrapperId']/img"));
		File imageSS = image.getScreenshotAs(OutputType.FILE);
		File dest= new File("./snaps/pic1.png");
		FileUtils.copyFile(imageSS, dest);
		
		driver.findElement(By.id("add-to-cart-button")).click();
		String mobPrice = driver.findElement(By.xpath("//span[@id='attach-accessory-cart-subtotal']")).getText();
		System.out.println("Mobile Price :" +mobPrice);
	
		if(mobPrice.equals(mrp)) {
			System.out.println("Both are equal");
		}
		else {
			System.out.println("Both are different");
		}
	
	}

}
