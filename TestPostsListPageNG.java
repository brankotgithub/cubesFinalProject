package cubes.tests;

import org.testng.annotations.Test;

import cubes.webpages.LoginPage;
import cubes.webpagesPosts.AddPostPage;
import cubes.webpagesPosts.PostsListPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;


import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestPostsListPageNG {
	
	private  ChromeDriver driver;
	private  WebDriverWait driverWait;
	private  LoginPage loginPage;
	private  AddPostPage addPostPage;
	private  PostsListPage addPostsListPage;
	private  final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts";
	
  
  @BeforeMethod
  public void beforeMethod() {
	  
	  driver.navigate().to(PAGE_URL);
	  //ne radi PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
  }

  @AfterMethod
  public void afterMethod() {
	 
  }

  @BeforeClass
  public void beforeClass() {
	  System.setProperty("webdriver.chrome.driver", "\\C:\\Users\\Korisnik\\Desktop\\webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driverWait = new WebDriverWait(driver,Duration.ofSeconds(20));
		LoginPage loginPage= new LoginPage(driver);
		loginPage.loginSuccess();
		
  }

  @AfterClass
  public void afterClass() {
	  //driver.close();
  }

  @BeforeTest
  public void beforeTest() {
	  
  }

  @AfterTest
  public void afterTest() {
  }

  @BeforeSuite
  public void beforeSuite() {
	  
  }

  @AfterSuite
  public void afterSuite() {
  }
  
  @Test(priority=1)
  public void testTC2AddNewPost()  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  addPostsListPage.clickOnweAddNewPost();	
	  String currentUrl = driver.getCurrentUrl();
      String expectedUrl = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add";
      Assert.assertEquals(expectedUrl, currentUrl);
      
	}
  
  @Test(priority=2)
  public void testTC3_1SearchByTitleNoExist() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js02 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("wwwwwww");	
	  js02.executeScript("window.scrollBy(0,500)");
	 
	  assertEquals(addPostsListPage.getTableInfoTextNoPosts(), "Showing 0 to 0 of 0 entries");
	}
  
  @Test(priority=3)
  public void testTC3_2SearchByTitleExist() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js03 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  js03.executeScript("window.scrollBy(0,3000)");
	 //Thread.sleep(2000);
	  assertEquals(addPostsListPage.getTableInfoTextExistPosts(), "Showing 1 to");
	}
  
  @Test(priority=4)
  public void testTC3_3SearchByTitleExist() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js04 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  js04.executeScript("window.scrollBy(0,3000)");
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	  js04.executeScript("window.scrollBy(0,-3000)");
	  Thread.sleep(2000);
	  WebElement searchField = driver.findElement(By.xpath("//input[@name='title']"));
	  Actions actions = new Actions(driver);
      actions.doubleClick(searchField).perform();
     Thread.sleep(2000);
      actions.moveToElement(searchField).sendKeys(Keys.DELETE).perform();
 
	  Thread.sleep(5000);
	  js04.executeScript("window.scrollBy(0,3000)");
	  Thread.sleep(5000);
	  String expectedPattern2 = "Showing 1 to \\d+ of \\d+ entries";
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern2));
	}
  
  @Test(priority=5)
  public void testTC3_4SearchByTitleExistAndAuthor() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	 
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  Thread.sleep(5000);
	  addPostsListPage.checkTitleAuthor("Branko", "Polaznik Kursa");
	  
  	}
  
  @Test(priority=6)
  public void testTC3_5SearchByTitleExistAuthorAndCategory() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  
	  addPostsListPage.weSearchInputCategory("BrankoCat");
	  WebElement category = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  category.click();
	  Thread.sleep(5000);
	  addPostsListPage.checkTitleAuthor("Branko", "Polaznik Kursa");
	  Thread.sleep(5000);
	  addPostsListPage.checkCategory("BrankoCat");
	 
  	}
  
  @Test(priority=7)
  public void testTC3_6SearchByTitleExistAuthorAndNoExistCategory() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js07 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  
	  addPostsListPage.weSearchInputCategory("BrankoCatNema");
	  WebElement category = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  category.click();
	  
	  
	  js07.executeScript("window.scrollBy(0,500)");
	 // Thread.sleep(2000);
	  assertEquals(addPostsListPage.getTableInfoTextNoPosts(), "Showing 0 to 0 of 0 entries");
	 
  	}
  
  @Test(priority=8)
  public void testTC3_7SearchByTitleExistAuthorExistCategoryAndImportant() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js08 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  
	  addPostsListPage.weSearchInputCategory("BrankoCat");
	  WebElement category = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  category.click();
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();

	  js08.executeScript("window.scrollBy(0,3000)");
	  //Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  //Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=9)
  public void testTC3_8SearchByTitleExistAuthorExistCategoryImportantAndStatus() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js09 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  
	  addPostsListPage.weSearchInputCategory("BrankoCat");
	  WebElement category = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  category.click();
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      

	  js09.executeScript("window.scrollBy(0,3000)");
	 // Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=10)
  public void testTC3_9SearchByTitleExistAuthorExistCategoryImportantStatusAndWithTag() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js10 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("Branko");	
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  
	  addPostsListPage.weSearchInputCategory("BrankoCat");
	  WebElement category = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  category.click();
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      
      addPostsListPage.weSearchWithTag();
      actions
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      

	  js10.executeScript("window.scrollBy(0,3000)");
	  //Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  //Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=11)
  public void testTC4_1SearchNoExistAndAuthor() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js11 = (JavascriptExecutor) driver;
	  addPostsListPage.weSearchAuthorInputName("John Doe");
	  
	  WebElement author = driverWait.until(
	  	        ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']")));
	  author.click();
	  js11.executeScript("window.scrollBy(0,500)");
	  Thread.sleep(5000);
	  assertEquals(addPostsListPage.getTableInfoTextNoPosts(), "Showing 0 to 0 of 0 entries");
	  
  	}
  
  @Test(priority=12)
  public void testTC4_2SearchExistAuthor() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js11 = (JavascriptExecutor) driver;
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  js11.executeScript("window.scrollBy(0,3000)");
	  Thread.sleep(5000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	//  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	  
  	}
  
  @Test(priority=13)
  public void testTC4_3SearchExistAndAuthor() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js13 = (JavascriptExecutor) driver;
	  addPostsListPage.weSearchAuthorInputName("Polaznik Kursa");
	 
	  WebElement author = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  author.click();
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.clickOnweNumberPerPage();
      actions.sendKeys(Keys.DOWN)
      .sendKeys(Keys.DOWN)
      .sendKeys(Keys.DOWN)
      .sendKeys(Keys.DOWN)
      .sendKeys(Keys.ENTER)
      .build()
      .perform();
      Thread.sleep(5000);
	  js13.executeScript("window.scrollBy(0,7000)");
	  addPostsListPage.checkNumberofExistAuthor("Polaznik Kursa");
	  
  	}
  
  @Test(priority=14)
  public void testTC5_1SearchNoExistingCategory() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js14 = (JavascriptExecutor) driver;
	 
	  addPostsListPage.weSearchAuthorInputName("");
	  addPostsListPage.weSearchInputCategory("BrankoCatNema");
	  WebElement category = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  category.click(); 

	  js14.executeScript("window.scrollBy(0,500)");
	  //Thread.sleep(2000);
	  String expectedPattern = "Showing 0 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  //Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=15)
  public void testTC5_2SearchExistCategory() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js15 = (JavascriptExecutor) driver;
	 
	  addPostsListPage.weSearchAuthorInputName("");
	  addPostsListPage.weSearchInputCategory("BrankoCat");
	  WebElement category = driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"));
	  category.click();
	  
	  js15.executeScript("window.scrollBy(0,3000)");
	 // Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=16)
  public void testTC6_1SearchImportantYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js16 = (JavascriptExecutor) driver;
	 
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
	  
	  js16.executeScript("window.scrollBy(0,3000)");
	  //Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=17)
  public void testTC6_2SearchImportantNo() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js17 = (JavascriptExecutor) driver;
	 
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
      			.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
	  
	  js17.executeScript("window.scrollBy(0,3000)");
	 // Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=18)
  public void testTC7_1SearchEnabledYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js18 = (JavascriptExecutor) driver;
	  Actions actions = new Actions(driver);
	  addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
	  
	  js18.executeScript("window.scrollBy(0,3000)");
	  //Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=19)
  public void testTC7_2SearchEnabledNo() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js19 = (JavascriptExecutor) driver;
	  Actions actions = new Actions(driver);
	  addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN)
      .sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
	  	  

	  js19.executeScript("window.scrollBy(0,3000)");
	  Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=20)
  public void testTC8_1SearchNoExistingWithTag() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js20 = (JavascriptExecutor) driver;
	 
	  Actions actions = new Actions(driver);
	  addPostsListPage.weSearchWithTag();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
	  
			  

	  js20.executeScript("window.scrollBy(0,3000)");
	  Thread.sleep(2000);
	  String expectedPattern = "Showing 0 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=21)
  public void testTC8_2SearchExistWithTag() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js21 = (JavascriptExecutor) driver;
	  Actions actions = new Actions(driver);
	  addPostsListPage.weSearchWithTag();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
	  
	  js21.executeScript("window.scrollBy(0,3000)");
	 Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	 Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  
  @Test(priority=22)
  public void testTC8_3SearchExistWithTagThenDelete() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js22 = (JavascriptExecutor) driver;
	  Actions actions = new Actions(driver);
	  addPostsListPage.weSearchWithTag();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      WebElement deleteTagBrankoTag = driver.findElement(By.xpath("//ul[@class='select2-selection__rendered']/li[1]/span[@class='select2-selection__choice__remove']"));
      deleteTagBrankoTag.click();
    
	  js22.executeScript("window.scrollBy(0,3000)");
	  Thread.sleep(2000);
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries";
	  
	 Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	 
  	}
  @Test(priority=23)
  public void testTC9_clickOnweNumberPerPage25() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js23 = (JavascriptExecutor) driver;
	 
	  Actions actions = new Actions(driver);
      
      addPostsListPage.clickOnweNumberPerPage();
      actions.sendKeys(Keys.DOWN)
      
      .sendKeys(Keys.ENTER)
      .build()
      .perform();
      
      //Thread.sleep(5000);
	  js23.executeScript("window.scrollBy(0,7000)");
	  String expectedPattern = "Showing 1 to 25 of \\d+ entries";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	  Thread.sleep(2000);
	  boolean checkPrevios1 = addPostsListPage.checkElementExistence("//li[@id='entities-list-table_previous']");
	  Assert.assertTrue(checkPrevios1);
	  boolean checkButton1 = addPostsListPage.checkElementExistence("//a[.='1']");
	  Assert.assertTrue(checkButton1);
	  boolean checkButton2 = addPostsListPage.checkElementExistence("//a[.='2']");
	  Assert.assertTrue(checkButton2);
	  boolean checkButtonNext = addPostsListPage.checkElementExistence("//a[.='Next']");
	  Assert.assertTrue(checkButtonNext);
	  
  	}
  
  @Test(priority=24)
  public void testTC10_clickOnweNumberPerPage100() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js24 = (JavascriptExecutor) driver;
	 
	  Actions actions = new Actions(driver);
      
      addPostsListPage.clickOnweNumberPerPage();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
      
      .sendKeys(Keys.ENTER)
      .build()
      .perform();
      
      Thread.sleep(5000);
	  js24.executeScript("window.scrollBy(0,11000)");
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
	  Thread.sleep(2000);
	  boolean checkPrevios1 = addPostsListPage.checkElementExistence("//li[@id='entities-list-table_previous']");
	  Assert.assertTrue(checkPrevios1);
	  boolean checkButton1 = addPostsListPage.checkElementExistence("//a[.='1']");
	  Assert.assertTrue(checkButton1);
	  boolean checkButtonNext = addPostsListPage.checkElementExistence("//li[@id='entities-list-table_next']");
	  Assert.assertTrue(checkButtonNext);
	  
  	}
  
  @Test(priority=25)
  public void testTC11SearchAllPostsNoExistText() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js25 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchAllPosts("wwwwwww");	
	  
	  js25.executeScript("window.scrollBy(0,500)");
	 Thread.sleep(2000);
	  assertEquals(addPostsListPage.getTableInfoTextNoPosts(), "Showing 0 to 0 of 0 entries");
	 
  	}
  @Test(priority=26)
  public void testTC12SearchAllPostsExistText() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js26 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchAllPosts("Branko");	
	  
	  js26.executeScript("window.scrollBy(0,3000)");
	  Thread.sleep(5000);
	  
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
  	}
  
  @Test(priority=27)
  public void testTC12_1SearchAllPostsExistText_Date() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js27 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchAllPosts("2023-05-14");	
	  
	  js27.executeScript("window.scrollBy(0,3000)");
	  Thread.sleep(5000);
	  
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries \\(filtered from \\d+ total entries\\)";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
  	}
  
  @Test(priority=28)
  public void testTC13SearchAllPostsExistText_Date() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js28 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchAllPosts("2023-05-14");
	  
	  WebElement element = driver.findElement(By.xpath("//input[@class='form-control form-control-sm']"));
	 
	  element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
     
	  js28.executeScript("window.scrollBy(0,5000)");
	  
	  String expectedPattern = "Showing 1 to \\d+ of \\d+ entries";
	  
	  Thread.sleep(2000);
	  Assert.assertTrue(addPostsListPage.getTableInfoFullTextExistPosts().matches(expectedPattern));
  	}
  
  @Test(priority=29)
  public void testTC14_1clickeditPostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  
	  JavascriptExecutor js29 = (JavascriptExecutor) driver;
	  
	//precondition
	  addPostsListPage.clickOnweAddNewPost();	
	  AddPostPage addPostPage=new AddPostPage(driver,((FluentWait<WebDriver>) driverWait));
	  addPostPage.clickOnweChooseCategory("BrankoCat");
		addPostPage.clickOnweAddPostTitle("BrankoTitleBrankoTitle2");
		addPostPage.clickOnweAddPostDescription("BrankoTitleBrankoTitle2"
				+ "BrankoTitleBrankoTitle2BrankoTitleBrankoTitle2"
				+ "BrankoTitleBrankoTitle2BrankoTitleBrankoTitle2");
		addPostPage.clickOnweAddPostTag();
		Thread.sleep(2000);
		 addPostPage.clickOnweAddPostImpYes();
		 js29.executeScript("window.scrollBy(0,500)");
		 WebElement iframeElement3 = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
	        driver.switchTo().frame(iframeElement3);

	        // Find the <p> element inside the iframe
	        WebElement textareaElement5 = driver.findElement(By.tagName("p"));

	        // Set the value of the <textarea>
	        textareaElement5.sendKeys("bbb");
	        driver.switchTo().defaultContent();
	        
	        js29.executeScript("window.scrollBy(0,500)");
		addPostPage.clickOnweSaveAddNewPost();
		
		 //driver.navigate().to(PAGE_URL);
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle2");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js29.executeScript("window.scrollBy(0,500)");
      Thread.sleep(2000);
      addPostsListPage.clickOnweEditPost();
	  
      String currentUrl = driver.getCurrentUrl();
      String expectedUrl = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/edit/2972";
      Thread.sleep(2000);
      Assert.assertEquals(expectedUrl, currentUrl);
      
      //String expectedtextFromDeleteButton = "Delete Photo"; sa ovim kodom prolazi test bez Failures
      String expectedtextFromDeleteButton = "Choose New Photo";
      
	  Assert.assertTrue(addPostsListPage.wegetTextEditDeletePost().matches(expectedtextFromDeleteButton));
  	}
  
  @Test(priority=31)
  public void testTC14_2EditPostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js31 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle2");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js31.executeScript("window.scrollBy(0,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweEditPost();
	  
      Thread.sleep(5000);
      
      addPostsListPage.clickAndEditTitlePost("BrankoTitleBrankoTitleEdit");
      js31.executeScript("window.scrollBy(0,5000)");
      
      WebElement button = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		
      driverWait.until(ExpectedConditions.elementToBeClickable(button));
	 
      js31.executeScript("arguments[0].click();", button);
      
      WebElement element2 = driverWait.until(
  	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='toast-message']")));
      String toastEditText = addPostsListPage.wegetTextEditToastPost();
      
      Thread.sleep(5000);
      Assert.assertEquals(toastEditText, "Post has been edited");
      
  	}
  
  @Test(priority=30)
  public void testTC14_3CanceleditPostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js30 = (JavascriptExecutor) driver;
	  
		
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle2");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js30.executeScript("window.scrollBy(0,500)");
      Thread.sleep(2000);
      addPostsListPage.clickOnweEditPost();
      
      js30.executeScript("window.scrollBy(0,5000)");
      //addPostsListPage.clickOnweEditCancelPost();
      Thread.sleep(2000);
      WebElement button = driver.findElement(By.xpath("//a[.='Cancel']"));
		
      driverWait.until(ExpectedConditions.elementToBeClickable(button));
	 
      js30.executeScript("arguments[0].click();", button);
	  
      String currentUrl = driver.getCurrentUrl();
      String expectedUrl = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts";
      Thread.sleep(2000);
      Assert.assertEquals(expectedUrl, currentUrl);
      
      
  	}
  
  @Test(priority=32)
  public void testTC14_4CancelDeletePostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js32 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js32.executeScript("window.scrollBy(0,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweDeletePost();
      Thread.sleep(2000);
      addPostsListPage.clickOnweCancelDeletePost();
	 
      
      WebElement element2 = driverWait.until(
  	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      String tableinfoText = addPostsListPage.getTableInfoTextExistPosts();
      
      
      Assert.assertEquals(tableinfoText, "Showing 1 to");
      
  	}
  
  @Test(priority=33)
  public void testTC14_5DeletePostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js33 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle2");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js33.executeScript("window.scrollBy(0,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweDeletePost();
      Thread.sleep(2000);
      addPostsListPage.clickOnweAssureDeletePost();
	 
      
      WebElement element2 = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='toast-message']")));
        String toastEditText = addPostsListPage.wegetTextEditToastPost();
        
       Thread.sleep(5000);
        Assert.assertEquals(toastEditText, "Post has been deleted");
      
  	}
  
  @Test(priority=34)
  public void testTC14_6CancelEnablePostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js34 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js34.executeScript("window.scrollBy(0,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      Thread.sleep(2000);
      addPostsListPage.clickOnweEnablePost();
      Thread.sleep(2000);
      addPostsListPage.clickOnweCancelEnablePost();
	 
      
      WebElement element2 = driverWait.until(
  	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      String tableinfoText = addPostsListPage.getTableInfoTextExistPosts();
      
      
      Assert.assertEquals(tableinfoText, "Showing 1 to");
      
  	}
  
  @Test(priority=35)
  public void testTC14_7EnablePostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js35 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js35.executeScript("window.scrollBy(0,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      Thread.sleep(2000);
      addPostsListPage.clickOnweEnablePost();
     Thread.sleep(2000);
      addPostsListPage.clickOnweAssureEnablePost();
	 
      
      WebElement element2 = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='toast-message']")));
        String toastEditText = addPostsListPage.wegetTextEditToastPost();
        
        //Thread.sleep(5000);
        Assert.assertEquals(toastEditText, "Post has been enabled");
      
  	}
  
  @Test(priority=36)
  public void testTC14_8CancelUnImportingPostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js36 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle2");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js36.executeScript("window.scrollBy(200,500)");
      Thread.sleep(5000);
      WebElement element = driverWait.until(
  	        ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweUnimportingPost();
      Thread.sleep(2000);
      addPostsListPage.clickOnweCancelUnimportingPost();
	 
      
      WebElement element2 = driverWait.until(
  	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      String tableinfoText = addPostsListPage.getTableInfoTextExistPosts();
      
      
      Assert.assertEquals(tableinfoText, "Showing 1 to");
      
  	}
  
  @Test(priority=37)
  public void testTC_14_9UnimportingPostStatusDisabledImportanYes() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js37 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js37.executeScript("window.scrollBy(0,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweUnimportingPost();
      
      addPostsListPage.clickOnweAssureUnimportingPost();
	 
      
      WebElement element2 = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='toast-message']")));
        String toastEditText = addPostsListPage.wegetTextEditToastPost();
        
        //Thread.sleep(5000);
        Assert.assertEquals(toastEditText, "Post has been set as unimportant");
      
  	}
  
  @Test(priority=38)
  public void testTC15_1CancelImportingPostStatusDisabledImportanNo() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js36 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js36.executeScript("window.scrollBy(200,500)");
      
      WebElement element = driverWait.until(
  	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweImportingPost();
      Thread.sleep(2000);
      addPostsListPage.clickOnweCancelImportingPost();
	 
      
      WebElement element2 = driverWait.until(
  	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      String tableinfoText = addPostsListPage.getTableInfoTextExistPosts();
      
      
      Assert.assertEquals(tableinfoText, "Showing 1 to");
      
  	}
  
  @Test(priority=39)
  public void testTC_15_2ImportingPostStatusDisabledImportanNo() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js37 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("BrankoTitleBrankoTitle");	
	  addPostsListPage.weSearchImportant();
	  Actions actions = new Actions(driver);
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      addPostsListPage.weSearchStatus();
      actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN)
              .sendKeys(Keys.ENTER)
              .build()
              .perform();
      js37.executeScript("window.scrollBy(0,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweImportingPost();
      
      addPostsListPage.clickOnweAssureImportingPost();
	 
      
      WebElement element2 = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='toast-message']")));
        String toastEditText = addPostsListPage.wegetTextEditToastPost();
        
       // Thread.sleep(5000);
        Assert.assertEquals(toastEditText, "Post has been set as important");
      
  	}
  
  @Test(priority=40)
  public void testTC_16_1IView() throws InterruptedException  {
	  PostsListPage addPostsListPage=new PostsListPage(driver,((FluentWait<WebDriver>) driverWait));
	  JavascriptExecutor js40 = (JavascriptExecutor) driver;
	  addPostsListPage.clickOnweSearchTitle("view");	
	  //Thread.sleep(2000);
      js40.executeScript("window.scrollBy(200,500)");
     
      WebElement element = driverWait.until(
    	        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='entities-list-table_info']")));
      addPostsListPage.clickOnweViewPost();
      
      
	   // Wait for a few seconds to allow the browser tab to open
	      try {
	          Thread.sleep(10000);
	      } catch (InterruptedException e) {
	          e.printStackTrace();
	      }
      Assert.assertEquals(true, addPostsListPage.compareURLNewTab("https://testblog.kurs-qa.cubes.edu.rs/posts/single-post/2960/brankotitlebrankotitleview"));
  	}
  
}