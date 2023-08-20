package cubes.tests;


import static org.testng.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.chrome.ChromeDriver;

import cubes.webpages.LoginPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLogin {
	
	private static ChromeDriver driver;
	private static LoginPage loginPage;
	private final String EMAIL_VALID = "kursqa@cubes.edu.rs";
	private final String EMAIL_INVALID = "example@mail123.com";
	private final String EMAIL_INCORECT_FORMAT = "abc123";
	private final String PASSWODR_VALID = "cubesqa";
	private final String PASSWORD_INVALID = "abcabc";
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "\\C:\\Users\\Korisnik\\Desktop\\webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		loginPage = new LoginPage(driver);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		loginPage.openPage();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tc03LoginWithEmptyFields() {		
		loginPage.inputStringInEmail("");
		
		loginPage.inputStringInPassword("");
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getEmailInputError(), "Email* is required field");
		
		assertEquals(loginPage.getPasswordInputError(), "Password* is required field");
	}
	
	@Test
	public void tc04LoginWithIncorectEmailFormat() {		
		loginPage.inputStringInEmail(EMAIL_INCORECT_FORMAT);
		
		loginPage.inputStringInPassword("");
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getEmailInputError(), "Please, enter the valid Email address");
		
		assertEquals(loginPage.getPasswordInputError(), "Password* is required field");
	}
	
	@Test
	public void tc05LoginWIthInvalidEmail() {		
		loginPage.inputStringInEmail(EMAIL_INVALID);
		
		loginPage.inputStringInPassword("");
		
		loginPage.clickOnSignIn();
				
		assertEquals(loginPage.getPasswordInputError(), "Password* is required field");
	}
	
	@Test
	public void tc06LoginWithValidEmail() {		
		loginPage.inputStringInEmail(EMAIL_VALID);
		
		loginPage.inputStringInPassword("");
		
		loginPage.clickOnSignIn();
				
		assertEquals(loginPage.getPasswordInputError(), "Password* is required field");
	}
	
	@Test
	public void tc07LoginWithEmptyEmailAndInvalidPassword() {		
		loginPage.inputStringInEmail("");
		
		loginPage.inputStringInPassword(PASSWORD_INVALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getEmailInputError(), "Email* is required field");
	}
	
	@Test
	public void tc08LoginWithEmptyEmailAndValidPassword() {		
		loginPage.inputStringInEmail("");
		
		loginPage.inputStringInPassword(PASSWODR_VALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getEmailInputError(), "Email* is required field");
	}
	
	@Test
	public void tc09LoginWithIncorectEmailFormatAndInvalidPassword() {		
		loginPage.inputStringInEmail(EMAIL_INCORECT_FORMAT);
		
		loginPage.inputStringInPassword(PASSWORD_INVALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getEmailInputError(), "Please, enter the valid Email address");
	}
	
	@Test
	public void tc10LoginWithInvalidEmailAndInvalidPassword() {		
		loginPage.inputStringInEmail(EMAIL_INVALID);
		
		loginPage.inputStringInPassword(PASSWORD_INVALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getInvalidError(), "These credentials do not match our records.");
	}
	
	@Test
	public void tc11LoginWithValidEmailAndInvalidPassword() {		
		loginPage.inputStringInEmail(EMAIL_VALID);
		
		loginPage.inputStringInPassword(PASSWORD_INVALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getInvalidError(), "These credentials do not match our records.");
	}
	
	@Test
	public void tc12LoginWithIncorectEmailFormatAndValidPassword() {		
		loginPage.inputStringInEmail(EMAIL_INCORECT_FORMAT);
		
		loginPage.inputStringInPassword(PASSWODR_VALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getEmailInputError(), "Please, enter the valid Email address");
	}
	
	@Test
	public void tc13LoginWithInvalidEmailAndValidPassword() {		
		loginPage.inputStringInEmail(EMAIL_INVALID);
		
		loginPage.inputStringInPassword(PASSWODR_VALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(loginPage.getInvalidError(), "These credentials do not match our records.");
	}
	
	@Test
	public void tc14LoginWithValidEmailAndValidPassword() {
		loginPage.inputStringInEmail(EMAIL_VALID);
		
		loginPage.inputStringInPassword(PASSWODR_VALID);
		
		loginPage.clickOnSignIn();
		
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin");
	}
}
