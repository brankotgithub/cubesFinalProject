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
public class TestDeleteTag {
	
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
		driver.navigate().to(PAGE_URL);
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
/*
	@Test
	public void tc1TestLinkFromMenu() {
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		tagFormPage.checkMenuLink("Sliders list", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders");
		tagFormPage.checkMenuLink("Add Slider", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders/add");
		tagFormPage.checkMenuLink("Post Categories list", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories");
		tagFormPage.checkMenuLink("Add Post Category", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories/add");
		tagFormPage.checkMenuLink("Tags list", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags");
		tagFormPage.checkMenuLink("Add Tag", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags/add");
		tagFormPage.checkMenuLink("Posts list", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
		tagFormPage.checkMenuLink("Add Post", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
		tagFormPage.checkMenuLink("Comments List", "https://testblog.kurs-qa.cubes.edu.rs/admin/comments");
		tagFormPage.checkMenuLink("Users List", "https://testblog.kurs-qa.cubes.edu.rs/admin/users");
		tagFormPage.checkMenuLink("Add User", "https://testblog.kurs-qa.cubes.edu.rs/admin/users/add");
	}
*/
	@Test
	public void tc2TestAddAndCancelDeleteTag() {
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		
		String tagName = tagFormPage.addNewRandomTag();
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("window.scrollBy(0,3000)");
		
		
		tagFormPage.clickCancelDelete(tagName);
		String expectedTagName = tagFormPage.checkTag(tagName);
		assertEquals(tagName, expectedTagName);
		
		 
		js.executeScript("window.scrollBy(0,3000)");
		tagFormPage.clickDelete(tagName);
		String expectedTagName2 = tagFormPage.getSuccessDeleteMessage();
		String message=tagName+" tag has been deleted";
		assertEquals(message, expectedTagName2);
		
	}
	
	@Test
	public void tc3TestDeleteTag() {
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		String tagName = tagFormPage.addNewRandomTag();
		
		js.executeScript("window.scrollBy(0,3000)");
		tagFormPage.clickDelete(tagName);
		
		assertEquals(false, tagFormPage.isTagInList(tagName));
	}
	
}
