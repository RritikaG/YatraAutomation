import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YatraAutomationScript {

	public static void main(String[] args) throws InterruptedException
	{
		ChromeOptions chromeoption = new ChromeOptions();
		chromeoption.addArguments("--disable-notifications");
		WebDriver wd = new ChromeDriver(chromeoption);
		
		wd.manage().window().maximize();
		
		wd.get("https://yatra.com");
		
	
		
		By departureDateButtonLocator = By.xpath("//div[@aria-label=\"Departure Date inputbox\"]");
		
		//WebElement departureDateButton = wd.findElement(departureDateButtonLocator);
		
		WebDriverWait wait = new WebDriverWait(wd,Duration.ofSeconds(20));
		
		WebElement departureDateButton=wait.until(ExpectedConditions.elementToBeClickable(departureDateButtonLocator));
		
		departureDateButton.click();
		
		By calenderMonthLocator = By.xpath("//div[@class=\"react-datepicker__month-container\"]");
		
		List<WebElement> calenderMonthList= wd.findElements(calenderMonthLocator);
	
	//List<WebElement> calenderMonthList=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calenderMonthLocator));
		
	System.out.println(calenderMonthList.size());
	
	WebElement JuneMonth=calenderMonthList.get(0);
	
	Thread.sleep(2000);
	
	By juneDateLocator = By.xpath(".//div[contains(@class,\"react-datepicker__day\")]");
	By priceLocator = By.xpath(".//span[contains(@class,'custom-day-content')]");
	
	List<WebElement> junePriceList=JuneMonth.findElements(priceLocator);
	
	int lowestPrice=Integer.MAX_VALUE;
	WebElement priceElement = null;
	for(WebElement ele:junePriceList)
	{
		//System.out.println(ele.getText());
		String pricestring=ele.getText();
		if(pricestring.length()>0) {
		pricestring=pricestring.replace("₹", "").replace(",","");
		int price=Integer.parseInt(pricestring);
		
		if(price<lowestPrice)
		{
			lowestPrice=price;
			priceElement=ele;
			
		}
		}
		
	}
	
	WebElement dateElement = priceElement.findElement(By.xpath(".//../.."));
	System.out.println("Lowest price in this month is "+lowestPrice+ " on "+dateElement.getAttribute("aria-label"));
	
	
	
	
	wd.quit();
	
	
	
	
	
		
		
		
	
	
		
		
		
	}
}
