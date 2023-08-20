package cubes.tests;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import cubes.webpages.LoginPage;
import cubes.webpagesTags.TagFormPage;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testUpdateTag {
	
	private static ChromeDriver driver;
	private static WebDriverWait driverWait;
	private static LoginPage loginPage;
	private static TagFormPage tagFormPage;
	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/tags";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "\\C:\\Users\\Branko\\Desktop\\webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driverWait = new WebDriverWait(driver,Duration.ofSeconds(10));
		LoginPage loginPage= new LoginPage(driver);
		loginPage.loginSuccess();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tc1AddNewTag() {
		
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		driver.navigate().to(PAGE_URL);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String tagName = tagFormPage.clickAddNewTag();
		js.executeScript("window.scrollBy(0,3000)");
		assertEquals(true, tagFormPage.isTagInList(tagName));
	}
	
	@Test
	public void tc2UpdateEmptyAndCancel() {
		
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		driver.navigate().to(PAGE_URL);
		String tagName = tagFormPage.clickAddNewTag();
		JavascriptExecutor js5 = (JavascriptExecutor) driver;
		js5.executeScript("window.scrollBy(0,3000)");
		WebElement weButtonUpdateTag = driver.findElement(By.xpath("//strong[.='"+tagName+"']//ancestor::tr/td[5]/div/a[2]/i"));
		weButtonUpdateTag.click();
		tagFormPage.clickEmptyUpdate();
		String expectederoormsg = tagFormPage.getErrorMessage();
		String message="This field is required.";
		assertEquals(message, expectederoormsg);
		
		tagFormPage.clickCancelUpdate();
		String expectedUrl = "https://testblog.kurs-qa.cubes.edu.rs/admin/tags";
		String currentUrl = driver.getCurrentUrl();
		assertEquals(currentUrl, expectedUrl);
	}
	
	@Test
	public void tc3UpdateWithExistingNameTag() {
		
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		driver.navigate().to(PAGE_URL);
		String tagName = tagFormPage.clickAddNewTag();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,3000)");
		
		WebElement weButtonUpdateTag = driver.findElement(By.xpath("//strong[.='"+tagName+"']//ancestor::tr/td[5]/div/a[2]/i"));
		weButtonUpdateTag.click();
		tagFormPage.inputExistingTag();
		tagFormPage.clickSave();
		String errorMessage = tagFormPage.getErrorMessageExitingTag();
		
		assertEquals("The name has already been taken.", errorMessage);
	}

}
