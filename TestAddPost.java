package cubes.tests;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;
import org.junit.Assert;
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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.openqa.selenium.interactions.Actions;

import cubes.webpages.LoginPage;
import cubes.webpagesCategories.CategoryFormPage;
import cubes.webpagesPosts.AddPostPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAddPost {
	
	private static ChromeDriver driver;
	private static WebDriverWait driverWait;
	private static LoginPage loginPage;
	private static AddPostPage addPostPage;
	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add";

	private final String CATEGORY_POST1 = "BrankoCat";
	
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
		driver.close();
	}

	@Before
	public void setUp() throws Exception {
		
		
		//driver.navigate().to(PAGE_URL);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tc02AddpostEmptyAllFields() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		/*kada je aktivan zakomentarisan kod test prolazi
		 addPostPage.clickOnweChooseCategory("");
		addPostPage.clickOnweAddPostTitle("Proba2020202020202202020220220202022020202");
		addPostPage.clickOnweAddPostDescription("Proba20202020202022020202202202"
				+ "02022020202Proba2020202020202202020220220202022020202Proba"
				+ "2020202020202202020220220202022020202");
		addPostPage.clickOnweAddPostTag();*/
		js.executeScript("window.scrollBy(0,4000)");
		addPostPage.clickOnweSaveAddNewPost();
		js.executeScript("window.scrollBy(0,6000)");
	
		AssertJUnit.assertEquals(true, addPostPage.isContentMessage());
		js.executeScript("window.scrollBy(0,6000)");
		
	}
	
	@Test
	public void tc03AddpostCancel() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js03 = (JavascriptExecutor) driver;
		js03.executeScript("window.scrollBy(0,8000)");
		
		addPostPage.clickOnweCancelAddNewPost();
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts";
        Assert.assertEquals(expectedUrl, currentUrl);
        
	}
	
	@Test
	public void tc04SavePostOnlyInputCategory() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		JavascriptExecutor js04 = (JavascriptExecutor) driver;
		
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 
		js04.executeScript("window.scrollBy(0,3000)");
		addPostPage.clickOnweSaveAddNewPost();
		
		
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagInputError(), "This field is required.");
		
	}
	
	@Test
	public void tc05SavePostInputCategoryAndIncorectTitle() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitle");
		js2.executeScript("window.scrollBy(0,3000)");
		addPostPage.clickOnweSaveAddNewPost();
		js2.executeScript("window.scrollBy(0,-3000)");
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagInputError(), "This field is required.");
		
	}
	
	
	
	@Test
	public void tc06SavePostInputCategoryAndCorectTitle() throws InterruptedException {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js06 = (JavascriptExecutor) driver;
		js06.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("");
		 addPostPage.clickOnweAddPostImpNo();
		 js06.executeScript("window.scrollBy(0,3000)");
		 addPostPage.clickOnweSaveAddNewPost();
		 
		
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagInputError(), "This field is required.");
		
	}
	
	@Test
	public void tc07SavePostInputCategoryCorectTitleAndIncorectDescription() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js07 = (JavascriptExecutor) driver;
		
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescription");
		 addPostPage.clickOnweAddPostImpNo();
		js07.executeScript("window.scrollBy(0,4000)");
		addPostPage.clickOnweSaveAddNewPost();
		
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		assertEquals(addPostPage.getTagInputError(), "This field is required.");
		
	}
	
	@Test
	public void tc08SavePostInputCategoryCorectTitleAndCorectDescription() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js08 = (JavascriptExecutor) driver;
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		 js08.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpNo();
		 js08.executeScript("window.scrollBy(0,4000)");
		 addPostPage.clickOnweSaveAddNewPost();
		 
		
		assertEquals(addPostPage.getTagInputError(), "This field is required.");
		
	}
	
	@Test
	public void tc09SavePostInputCategoryCorectTitletDescriptionTag() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js09 = (JavascriptExecutor) driver;
		JavascriptExecutor executor9 = (JavascriptExecutor) driver;
		 js09.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpNo();
		 addPostPage.clickOnweAddPostTag();
		 js09.executeScript("window.scrollBy(0,4000)");
		addPostPage.clickOnweSaveAddNewPost();
		 
		
		assertEquals(addPostPage.getweContentMessageInputError(), "This field is required.");
		
	}
	
	@Test
	public void tc10SavePostCorectAllFields() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js10 = (JavascriptExecutor) driver;
		JavascriptExecutor executor10 = (JavascriptExecutor) driver;
		 js10.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpNo();
		 addPostPage.clickOnweAddPostTag();
		 js10.executeScript("window.scrollBy(0,500)");
		
		 WebElement iframeElement = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
	        driver.switchTo().frame(iframeElement);

	        // Find the <p> element inside the iframe
	        WebElement textareaElement1 = driver.findElement(By.tagName("p"));

	        // Set the value of the <textarea>
	        textareaElement1.sendKeys("b");

	        // Switch back to the default content
	        driver.switchTo().defaultContent();
	        
	        addPostPage.clickOnweSaveAddNewPost();
	        
	        assertEquals(addPostPage.getToastAddPostMessage(), "New post has been added");
		
	}
	
	@Test
	public void tc11SavePostCorectAllFieldsImprtantYes() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js11 = (JavascriptExecutor) driver;
		
		js11.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpYes();
		 addPostPage.clickOnweAddPostTag();
		 js11.executeScript("window.scrollBy(0,500)");
		
		 WebElement iframeElement = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
	        driver.switchTo().frame(iframeElement);

	        // Find the <p> element inside the iframe
	        WebElement textareaElement2 = driver.findElement(By.tagName("p"));

	        // Set the value of the <textarea>
	        textareaElement2.sendKeys("b");

	        // Switch back to the default content
	        driver.switchTo().defaultContent();
	        
	        addPostPage.clickOnweSaveAddNewPost();
			
	        assertEquals(addPostPage.getToastAddPostMessage(), "New post has been added");
	}
	
	@Test
	public void tc12SavePostCorectAllFieldsImprtantYesIncorectPhoto() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js12 = (JavascriptExecutor) driver;
		
		js12.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpYes();
		 addPostPage.clickOnweAddPostTag();
		 
		 js12.executeScript("window.scrollBy(0,500)");
		
		 
		 WebElement fileInputElement = driver.findElement(By.cssSelector("input[name='photo'][type='file']"));

	        // Set the file path as the value of the file input field
	        String filePath = "C:\\Users\\Korisnik\\Downloads\\Book1.xlsx";
	        fileInputElement.sendKeys(filePath);
		 
		 js12.executeScript("window.scrollBy(0,500)");
		
		 WebElement iframeElement = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
	        driver.switchTo().frame(iframeElement);

	        // Find the <p> element inside the iframe
	        WebElement textareaElement2 = driver.findElement(By.tagName("p"));

	        // Set the value of the <textarea>
	        textareaElement2.sendKeys("bbb");
	       
	        // Switch back to the default content
	        driver.switchTo().defaultContent();
	        
	        addPostPage.clickOnweSaveAddNewPost();
			
	        assertEquals(addPostPage.getweAddPostPhotoErrorMessage(), "The photo must be an image.");
	}
	
	@Test
	public void tc13SavePostCorectAllFieldsImprtantYesCorectPhoto() {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		//driver.navigate().to(PAGE_URL);
		JavascriptExecutor js13 = (JavascriptExecutor) driver;
		
		js13.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpYes();
		 addPostPage.clickOnweAddPostTag();
		 
		 js13.executeScript("window.scrollBy(0,500)");
		 
		 
		 WebElement fileInputElement = driver.findElement(By.cssSelector("input[name='photo'][type='file']"));

	        // Set the file path as the value of the file input field
	        String filePath = "C:\\Users\\Korisnik\\Downloads\\pexels-pixabay-458976.jpg";
	       
	        fileInputElement.sendKeys(filePath);
		 
	        js13.executeScript("window.scrollBy(0,500)");
	        
	       
		
	        WebElement iframeElement = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
	        driver.switchTo().frame(iframeElement);

	        // Find the <p> element inside the iframe
	        WebElement textareaElement3 = driver.findElement(By.tagName("p"));

	        // Set the value of the <textarea>
	        textareaElement3.sendKeys("bb");
	        driver.switchTo().defaultContent();
	        // Switch back to the default content
	        
	        js13.executeScript("window.scrollBy(0,2000)");
	        addPostPage.clickOnweSaveAddNewPost();
	       
			
	       assertEquals(addPostPage.getToastAddPostMessage(), "New post has been added");
	}
	
	@Test
	public void tc14SavePostCorectAllFieldsTwoTags() throws InterruptedException {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js14 = (JavascriptExecutor) driver;
		
		js14.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpYes();
		 addPostPage.clickOnweAddPostTag();
		 addPostPage.clickOnweAddPostTag2();
		 
		 js14.executeScript("window.scrollBy(0,500)");
		 
		 
		 WebElement fileInputElement2 = driver.findElement(By.cssSelector("input[name='photo'][type='file']"));

	        // Set the file path as the value of the file input field
		 String filePath = "C:\\Users\\Korisnik\\Downloads\\pexels-pixabay-458976.jpg";
	       
	        fileInputElement2.sendKeys(filePath);
		 
	        js14.executeScript("window.scrollBy(0,500)");
		
		 WebElement iframeElement3 = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
	        driver.switchTo().frame(iframeElement3);

	        // Find the <p> element inside the iframe
	        WebElement textareaElement5 = driver.findElement(By.tagName("p"));

	        // Set the value of the <textarea>
	        textareaElement5.sendKeys("bbb");

	        // Switch back to the default content
	        driver.switchTo().defaultContent();
	        
	        addPostPage.clickOnweSaveAddNewPost();
			
	       assertEquals(addPostPage.getToastAddPostMessage(), "New post has been added");
	      
	      
	}
	
	@Test
	public void tc15SavePostCorectAllFieldsContentBold()  {
		
		AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
		
		JavascriptExecutor js15 = (JavascriptExecutor) driver;
		JavascriptExecutor executor15 = (JavascriptExecutor) driver;
		js15.executeScript("window.scrollBy(0,-4000)");
		 addPostPage.clickOnweChooseCategory("BrankoCat");
		 addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle");
		 addPostPage.clickOnweAddPostDescription("BrankoDescriptionBrankoDescriptionBrankoDescription");
		 addPostPage.clickOnweAddPostImpYes();
		 addPostPage.clickOnweAddPostTag();
		 
		 
		 js15.executeScript("window.scrollBy(0,500)");
		 
		 
		 WebElement fileInputElement2 = driver.findElement(By.cssSelector("input[name='photo'][type='file']"));

	        // Set the file path as the value of the file input field
		 String filePath = "C:\\Users\\Korisnik\\Downloads\\pexels-pixabay-458976.jpg";
	       
	        fileInputElement2.sendKeys(filePath);
		 
	        //js15.executeScript("window.scrollBy(0,500)");
		
		 WebElement iframeElement3 = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
	        driver.switchTo().frame(iframeElement3);
	        
	        // Find the <p> element inside the iframe
	        WebElement textareaElement5 = driver.findElement(By.tagName("p"));
	        textareaElement5.click();
  
	        js15.executeScript("document.execCommand('bold', false, null);");

	        textareaElement5.sendKeys("bbb");

	        // Switch back to the default content
	        driver.switchTo().defaultContent();
	        
	        addPostPage.clickOnweSaveAddNewPost();
			
	       assertEquals(addPostPage.getToastAddPostMessage(), "New post has been added");
	      
	}

}
