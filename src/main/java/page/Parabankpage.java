package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Parabankpage {

	WebDriver driver;
	By email = By.xpath("//*[@id=\"loginPanel\"]/form/div[1]/input");
	By Pass = By.xpath("//*[@id=\"loginPanel\"]/form/div[2]/input");
	By login = By.xpath("//*[@id=\"loginPanel\"]/form/div[3]/input");
	
	public Parabankpage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void setvalue(String user,String pass)
	{
		driver.findElement(email).clear();
		driver.findElement(email).sendKeys(user);
		driver.findElement(Pass).clear();
		driver.findElement(Pass).sendKeys(pass);
	}
	
	public void select()
	{
		driver.findElement(login).click();
	}
}
