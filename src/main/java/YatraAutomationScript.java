import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
		WebDriverWait wait = new WebDriverWait(wd,Duration.ofSeconds(20));
		
		wd.manage().window().maximize();
		
		wd.get("https://yatra.com");
		
		By popUpLocator = By.xpath("//div[contains(@class,\"style_popup\")][1]");
		
		try
		{
			WebElement popUpElement =wait.until(ExpectedConditions.visibilityOfElementLocated(popUpLocator));
			WebElement crossbutton=popUpElement.findElement(By.xpath(".//img[@alt=\"cross\"]"));
	        crossbutton.click();	
		}
		catch(TimeoutException e)
		{
			System.out.println("POp up not found");
		}
		
	
		
		By departureDateButtonLocator = By.xpath("//div[@aria-label=\"Departure Date inputbox\"]");
		
		//WebElement departureDateButton = wd.findElement(departureDateButtonLocator);
		
			
		WebElement departureDateButton=wait.until(ExpectedConditions.elementToBeClickable(departureDateButtonLocator));
		
		departureDateButton.click();
		
		WebElement currMonth=selectMonthFromCalender(wd,0);
		WebElement nextMonth =selectMonthFromCalender(wd,1);
		Thread.sleep(2000);
		String lowestPriceCurrMonth=getLowestPrice(currMonth);
		String lowestPriceNextMonth=getLowestPrice(nextMonth);
		System.out.println(lowestPriceCurrMonth+ " or  "+lowestPriceNextMonth);
		compareTwoMonthsPrice(lowestPriceCurrMonth,lowestPriceNextMonth);    
	
	    wd.quit();
	
		
		
	}
	
	public static void compareTwoMonthsPrice(String currprice,String nextPrice)
	{
		// get the index of 
		
		int currRsindex=currprice.indexOf("Rs");
		int nextRsindex=nextPrice.indexOf("Rs");
		String getcurrprice=currprice.substring(currRsindex+2);
		String getnextprice=nextPrice.substring(nextRsindex+2);
		
		int cp=Integer.parseInt(getcurrprice);
		int np=Integer.parseInt(getnextprice);
		
		if(cp<np)
		{
			System.out.println("Lowest price for 2 months is "+cp);
		}
		else if (cp==np)
		{
			System.out.println("Prices are same please choose accordingly ");
		}
		else
		{
			System.out.println("Lowest price for 2 months is "+np);
		}
		
	}
	
	public static String getLowestPrice(WebElement month)
	{
		By priceLocator = By.xpath(".//span[contains(@class,'custom-day-content')]");
		
		List<WebElement> junePriceList=month.findElements(priceLocator);
		
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
		String result =dateElement.getAttribute("aria-label")+" with price Rs"+lowestPrice;
		return result;
	}
	
	public static WebElement selectMonthFromCalender(WebDriver wd,int index)
	{
	By calenderMonthLocator = By.xpath("//div[@class=\"react-datepicker__month-container\"]");
		
		List<WebElement> calenderMonthList= wd.findElements(calenderMonthLocator);
	
	//List<WebElement> calenderMonthList=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calenderMonthLocator));
		
   //	System.out.println(calenderMonthList.size());
	
	WebElement month=calenderMonthList.get(index);
	return month;
	
	}
}
