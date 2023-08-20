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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import cubes.webpages.LoginPage;
import cubes.webpagesTags.TagFormPage;
import cubes.webpagesTags.TagLisPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAddTag {

	private static ChromeDriver driver;
	private static WebDriverWait driverWait;
	private static LoginPage loginPage;
	private static TagFormPage tagFormPage;
	private static TagLisPage tagLisPage;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "\\C:\\Users\\Korisnik\\Desktop\\webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driverWait = new WebDriverWait(driver,Duration.ofSeconds(10));
		LoginPage loginPage= new LoginPage(driver);
		loginPage.loginSuccess();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

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
	
	@Test
	public void tc2TestNavigationLink() {
		
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		tagFormPage.checkNavigationLink("Home", "https://testblog.kurs-qa.cubes.edu.rs/admin");
		tagFormPage.checkNavigationLink("Tags", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags");

	}
	
	@Test
	public void tc3TestAddEmptyTag() {
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		tagFormPage.inputTagString("");
		tagFormPage.clickSave();
		//tagFormPage.clickCancel();
		
		String errorMessage = tagFormPage.getErrorMessage();
		
		assertEquals("This field is required.", errorMessage);
	}
	
	@Test
	public void tc4TestAddTag() {
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		
		
		String tagName = tagFormPage.addNewRandomTag();
		
		
		String expectedTagName = tagFormPage.checkTag(tagName);
		tagFormPage.openPage();
		
		
		assertEquals(tagName, expectedTagName);
	}
	
	@Test
	public void tc5TestAddTag() {
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		String tagName = tagFormPage.addNewRandomTag();
		
		tagFormPage.openPage();
		
		tagFormPage.inputTagString(tagName);
		tagFormPage.clickSave();
		
		String errorMessage = tagFormPage.getErrorMessage("The name has already been taken.");
		
		assertEquals("The name has already been taken.", errorMessage);
	}
	
	@Test
	public void tc6TestCancel() {
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		tagFormPage.inputTagString("Tag test name");
		tagFormPage.clickCancel();
		
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin/tags");
		
	}
	
	@Test
	public void tc7TestLogout(){
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		tagFormPage.clickProfile();
		tagFormPage.clickLogout();
		
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/login");
	}

}
