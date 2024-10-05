package testpkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pagepkg.Parabank;

public class parabank {

	WebDriver driver;
	String url = "https://parabank.parasoft.com/parabank/index.htm";
	
	@BeforeTest
	public void setup()
	{
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
	}
		
	@Test(priority=0)
	public void responcecode() throws URISyntaxException, MalformedURLException, IOException
	{
		
		URI ob = new URI(url);
		HttpURLConnection con = (HttpURLConnection)ob.toURL().openConnection();
		con.connect();
		if(con.getResponseCode()==200)
		{
			System.out.println("Successfull:\t"+driver.getCurrentUrl());
		}
		else if(con.getResponseCode()==404)
		{
			System.out.println("blocked:\t"+driver.getCurrentUrl());
		}
		else
		{
			System.out.println("unknown responce code"+driver.getCurrentUrl());
		}
	}
	
	@Test(priority=1)
	public void logocheck()
	{
		WebElement a = driver.findElement(By.xpath("//*[@id=\"topPanel\"]/a[2]/img"));
		if(a.isDisplayed())
		{
			System.out.println("is displayed");
		}
		else
		{
			System.out.println("not displayed");
		}
		WebElement b = driver.findElement(By.xpath("//*[@id=\"topPanel\"]/a[1]/img"));
		if(b.isDisplayed())
		{
			System.out.println("is displayed");
		}
		else
		{
			System.out.println("not displayed");
		}
		b.click();
		String curl = driver.getCurrentUrl();
		String eurl = "https://parabank.parasoft.com/parabank/admin.htm";
		if(curl.equals(eurl))
		{
			System.out.println("static admin page");
		}
		else
		{
			System.out.println("dynamic admin page");
		}
	}
	
	@Test(priority=2)
	public void indexpage()
	{
		WebElement a = driver.findElement(By.xpath("//*[@id=\"topPanel\"]/a[2]/img"));
		a.click();
		String urlnow=driver.getCurrentUrl();
		String testurl = "https://parabank.parasoft.com/parabank/index.htm";
		if(urlnow.equals(testurl))
		{
			System.out.println("correct index url");
		}
		else
		{
			System.out.println("wrong index url");
		}
	}
	
	@Test(priority=3)
	public void hamburgercheck() throws InterruptedException
	{
		int count = 0;
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[1]/li[1]")).click();
		count++;
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[1]/li[2]")).click();
		count++;
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[1]/li[3]")).click();
		count++;
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[1]/li[4]")).click();
		count++;
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[1]/li[5]")).click();
		count++;
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[1]/li[6]")).click();
		count++;
		if(count==6)
		{
			System.out.println("all option is working");
		}
		else
		{
			System.out.println("Error");
		}
	}
	
	@Test(priority=5)
	public void logintest() throws IOException, InterruptedException
	{
		Parabank ob = new Parabank(driver);
		FileInputStream f = new FileInputStream("C:\\Users\\nobil\\OneDrive\\loginparabank.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(f);
		XSSFSheet sheet = wb.getSheet("sheet1");
		int n = sheet.getLastRowNum();
		for(int i=1;i<=n;i++)
		{
			String user = sheet.getRow(i).getCell(0).getStringCellValue();
			String pass = sheet.getRow(i).getCell(1).getStringCellValue();
			System.out.println("user:"+user+"\tpasswrd:"+pass);
			ob.setvalue(user, pass);
			ob.select();
			String code = driver.getCurrentUrl();
			String check2 = "https://parabank.parasoft.com/parabank/login.htm";
			String check1 = "https://parabank.parasoft.com/parabank/index.htm";
			if(code.equals(check1))
			{
				System.out.println("fail");
			}
			else if(code.equals(check2))
			{
				System.out.println("fail");
			}
			else
			{
				System.out.println("pass");
				WebElement logout = driver.findElement(By.xpath("//*[@id=\"leftPanel\"]/ul/li[8]/a"));
				logout.click();
				Thread.sleep(2000);
			}
		}
	}
	
	@Test(priority=4)
	public void registercheck() throws InterruptedException
	{
		driver.get(url);
		WebElement a = driver.findElement(By.xpath("//*[@id=\"loginPanel\"]/p[2]/a"));
		a.click();
		String code = driver.getCurrentUrl();
		String check = "https://parabank.parasoft.com/parabank/register.htm";
		if(code.equals(check))
		{
			System.out.println("pass");
			WebElement fname = driver.findElement(By.xpath("//*[@id=\"customer.firstName\"]"));
			fname.sendKeys("fgh");
			WebElement lname = driver.findElement(By.xpath("//*[@id=\"customer.lastName\"]"));
			lname.sendKeys("123");
			WebElement address = driver.findElement(By.xpath("//*[@id=\"customer.address.street\"]"));
			address.sendKeys("abc address");
			WebElement city = driver.findElement(By.xpath("//*[@id=\"customer.address.city\"]"));
			city.sendKeys("abc.city");
			WebElement state = driver.findElement(By.xpath("//*[@id=\"customer.address.state\"]"));
			state.sendKeys("abc state");
			WebElement pin = driver.findElement(By.xpath("//*[@id=\"customer.address.zipCode\"]"));
			pin.sendKeys("123123");
			WebElement phone = driver.findElement(By.xpath("//*[@id=\"customer.phoneNumber\"]"));
			phone.sendKeys("1231231230");
			WebElement ssd = driver.findElement(By.xpath("//*[@id=\"customer.ssn\"]"));
			ssd.sendKeys("123");
			WebElement uname = driver.findElement(By.xpath("//*[@id=\"customer.username\"]"));
			uname.sendKeys("fgh@gmail.com");
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,500)");
			WebElement pass = driver.findElement(By.xpath("//*[@id=\"customer.password\"]"));
			pass.sendKeys("fgh@126");
			WebElement cpass = driver.findElement(By.xpath("//*[@id=\"repeatedPassword\"]"));
			cpass.sendKeys("fgh@126");
			WebElement register = driver.findElement(By.xpath("//*[@id=\"customerForm\"]/table/tbody/tr[13]/td[2]/input"));
			register.click();
//			WebElement logout = driver.findElement(By.xpath("//*[@id=\"leftPanel\"]/ul/li[8]/a"));
//			logout.click();
		}
		else
		{
			System.out.println("fail");
			Thread.sleep(2000);
		}
//		WebElement fname = driver.findElement(By.xpath("//*[@id=\"customer.firstName\"]"));
//		fname.sendKeys("fgh");
//		WebElement lname = driver.findElement(By.xpath("//*[@id=\"customer.lastName\"]"));
//		lname.sendKeys("123");
//		WebElement address = driver.findElement(By.xpath("//*[@id=\"customer.address.street\"]"));
//		address.sendKeys("abc address");
//		WebElement city = driver.findElement(By.xpath("//*[@id=\"customer.address.city\"]"));
//		city.sendKeys("abc.city");
//		WebElement state = driver.findElement(By.xpath("//*[@id=\"customer.address.state\"]"));
//		state.sendKeys("abc state");
//		WebElement pin = driver.findElement(By.xpath("//*[@id=\"customer.address.zipCode\"]"));
//		pin.sendKeys("123123");
//		WebElement phone = driver.findElement(By.xpath("//*[@id=\"customer.phoneNumber\"]"));
//		phone.sendKeys("1231231230");
//		WebElement ssd = driver.findElement(By.xpath("//*[@id=\"customer.ssn\"]"));
//		ssd.sendKeys("123");
//		WebElement uname = driver.findElement(By.xpath("//*[@id=\"customer.username\"]"));
//		uname.sendKeys("fgh@gmail.com");
//		JavascriptExecutor jse = (JavascriptExecutor)driver;
//		jse.executeScript("window.scrollBy(0,500)");
//		WebElement pass = driver.findElement(By.xpath("//*[@id=\"customer.password\"]"));
//		pass.sendKeys("fgh@126");
//		WebElement cpass = driver.findElement(By.xpath("//*[@id=\"repeatedPassword\"]"));
//		cpass.sendKeys("fgh@126");
//		WebElement register = driver.findElement(By.xpath("//*[@id=\"customerForm\"]/table/tbody/tr[13]/td[2]/input"));
//		register.click();
//		WebElement logout = driver.findElement(By.xpath("//*[@id=\"leftPanel\"]/ul/li[8]/a"));
//		logout.click();
	}
	
	@Test(priority=6)
	public void buttonvisibilitycheck()
	{
		int count = 0;
		WebElement button1 = driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[2]/li[1]/a"));
		if(button1.isEnabled())
		{
			System.out.println("enabled");
			count++;
		}
		WebElement button2 = driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[2]/li[2]/a"));
		if(button2.isEnabled())
		{
			System.out.println("enabled");
			count++;
		}
		WebElement button3 = driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[2]/li[3]/a"));
		if(button3.isEnabled())
		{
			System.out.println("enabled");
			count++;
		}
		if(count==3)
		{
			System.out.println("3 option is working");
		}
		else
		{
			System.out.println("Error");
		}
	}
	
	@Test(priority=7)
	public void buttonclickcheck()
	{
		WebElement button1 = driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[2]/li[1]/a"));
		button1.click();
		String b1url = "https://parabank.parasoft.com/parabank/index.htm";
		String b1curl = driver.getCurrentUrl();
		if(b1curl.contains(b1url))
		{
			System.out.println("button 1 is clickable");
		}
		else
		{
			System.out.println("error");
		}
		WebElement button2 = driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[2]/li[2]/a"));
		button2.click();
		String b2url = "https://parabank.parasoft.com/parabank/about.htm";
		String b2curl = driver.getCurrentUrl();
		if(b2url.equalsIgnoreCase(b2curl))
		{
			System.out.println("button 2 is clickable");
		}
		else
		{
			System.out.println("error");
		}
		WebElement button3 = driver.findElement(By.xpath("//*[@id=\"headerPanel\"]/ul[2]/li[3]/a"));
		button3.click();
		String b3url = "https://parabank.parasoft.com/parabank/contact.htm";
		String b3curl = driver.getCurrentUrl();
		if(b3url.equalsIgnoreCase(b3curl))
		{
			System.out.println("button 3 is clickable");
		}
		else
		{
			System.out.println("error");
		}
	}
	
	@Test(priority=8)
	public void linktest() throws MalformedURLException, URISyntaxException, IOException
	{
		List<WebElement> li = driver.findElements(By.tagName("a"));
		System.out.println(li.size());//number of links
		for(int i=0; i<li.size();i++)
		{
			WebElement ele =  li.get(i);
			String link = ele.getAttribute("href");
			verify(link);
		}
	}
	
	private void verify(String link){
		try {
			URI ob = new URI(link);
			HttpURLConnection con = (HttpURLConnection)ob.toURL().openConnection();
			if(con.getResponseCode()==200)
			{
				System.out.println("Successfull:\t"+link);
			}
			else if(con.getResponseCode()==404)
			{
				System.out.println("broken:\t"+link);
			}
			else
			{
				System.out.println("linked:"+link);
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test(priority=9)
	public void screenshot() throws IOException
	{
		WebElement checkavailability = driver.findElement(By.xpath("//*[@id=\"mainPanel\"]"));
		File button = checkavailability.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(button,new File("./screenshot/frameimage.png"));
	}
	
	@Test(priority=10)
	public void asserttitlecomparison()
	{
		String act = driver.getTitle();
		String exp = "ParaBank | Customer Care";
		Assert.assertEquals(act, exp,"failed");
	}
	
	@Test(priority=11)
	public void mousehover()
	{
		driver.get(url);
		WebElement read = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/p[1]/a"));
		Actions act = new Actions(driver);
		act.moveToElement(read);
		act.click();
		act.perform();
	}
	
	@Test(priority=12)
	public void newwindow()
	{
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get(url);
		driver.close();
	}
	
	@AfterTest
	public void end()
	{
		driver.quit();
	}
}
