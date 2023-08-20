package cubes.tests;

import static org.junit.Assert.*;
import cubes.webpages.LoginPage;
import cubes.webpagesTags.TagFormPage;
import cubes.webpagesTags.TagLisPage;
import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLoginPage {
	
	private static ChromeDriver driver;
	private static WebDriverWait wait;
	private static WebDriverWait driverWait;
	// posto je ovako bilo po prebacivanju iz testa  
	//uradjena inicijalizacija u prebaceno umetode klase
	/*private LoginPage loginPage= new LoginPage(driver);
	private TagFormPage tagFormPage=new TagFormPage(driver);
	private TagLisPage tagLisPage=new TagLisPage(driver,((FluentWait<WebDriver>) wait));
	
	private static LoginPage loginPage;
	private static TagFormPage tagFormPage;
	private static TagLisPage tagLisPage;*/

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "\\C:\\Users\\Branko\\Desktop\\webdriver\\chromedriver.exe");
		
		driver = new ChromeDriver();
		 wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		 driverWait = new WebDriverWait(driver,Duration.ofSeconds(10));
		 /*loginPage= new LoginPage(driver);
		 tagFormPage=new TagFormPage(driver);
		 tagLisPage=new TagLisPage(driver,((FluentWait<WebDriver>) wait));*/
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
	public void aloginTest() {
		// objekat bio ovde pa iyvuceno u fild - pa vraceno posto ne radi kad je gore
		LoginPage loginPage= new LoginPage(driver);
		loginPage.loginSuccess();
		// bilo ovde pa iyvuceno u fild 
		TagFormPage tagFormPage=new TagFormPage(driver,((FluentWait<WebDriver>) driverWait));
		tagFormPage.addNewTag("Tag Seleium");
		// bilo ovde pa iyvuceno u fild 
		TagLisPage tagLisPage=new TagLisPage(driver,((FluentWait<WebDriver>) wait));
		tagLisPage.deleteTag("Tag Seleium");
	}
	
}
