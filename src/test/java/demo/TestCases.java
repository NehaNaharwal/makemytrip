package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    @Test
    public void testCase01() throws InterruptedException{
        System.out.println("Start of testCase01");
        driver.get("https://www.flipkart.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@role, 'button') and contains(@class, '_30XB9F')]"))).click();
            
        } catch (Exception e) {
            System.out.println("No pop up appeared");
        }
        Thread.sleep(1000);
        //click on search
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title, 'Search for Products, Brands and More')]"));
        searchBox.click();
        //search for washing machine
        searchBox.sendKeys("Washing Machine");
        searchBox.sendKeys(Keys.RETURN);
        //sort by popularity and print the count of items with rating less than or equal to 4 stars
        List <WebElement> ratings = driver.findElements(By.xpath("//span[contains(@id, 'productRating')]"));
        int count = 0;
        for(WebElement rating : ratings){
            double textRating = Double.parseDouble(rating.getText());
            if(textRating <= 4.0){
                count++;
            }
        }

        System.out.println(count);
        System.out.println("End of testCase01");
    }

    @Test
    public void testCase02() throws InterruptedException{
        System.out.println("Start of testCase01");
        driver.get("https://www.flipkart.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@role, 'button') and contains(@class, '_30XB9F')]"))).click();
            
        } catch (Exception e) {
            System.out.println("No pop up appeared");
        }
        Thread.sleep(1000);
        //click on search
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title, 'Search for Products, Brands and More')]"));
        searchBox.click();
        //search for iphone
        searchBox.sendKeys("iPhone");
        searchBox.sendKeys(Keys.RETURN);
        //Titles and discount % of items with more than 17% discount
        List<WebElement> iteams = driver.findElements(By.xpath("//a[contains(@class, 'CGtC98')]"));
        for(WebElement iteam : iteams){
            WebElement title = driver.findElement(By.xpath("//div[contains(@class, 'KzDlHZ')]"));
            String titleText = title.getText();
            WebElement discount = driver.findElement(By.xpath("//div[contains(@class, 'UkUFwK')]"));
            String discountText = discount.getText();
            int discountValue = Integer.parseInt(discountText.replaceAll("[^0-9]", ""));
            if(discountValue>17){
                System.out.println("Title:"+ titleText + "Discount:"+ discountText);
            }
        }

        System.out.println("End of testCase02");


    }

    @Test
    public void testCase03() throws InterruptedException{
        System.out.println("Start of testCase03");
        driver.get("https://www.flipkart.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@role, 'button') and contains(@class, '_30XB9F')]"))).click();
            
        } catch (Exception e) {
            System.out.println("No pop up appeared");
        }
        Thread.sleep(1000);
        //click on search
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title, 'Search for Products, Brands and More')]"));
        searchBox.click();
        //search for coffee mug
        searchBox.sendKeys("Coffee Mug");
        searchBox.sendKeys(Keys.RETURN);
        //select 4 stars and above
        driver.findElement(By.xpath("//*[@id='container']/div/div[3]/div/div[1]/div/div/div/section[5]/div[2]/div/div[2]")).click();
        Thread.sleep(1000);
        //Title and image URL of the 5 items with highest number of reviews
        List<WebElement> items = driver.findElements(By.xpath("//div[contains(@class, 'slAVV4')]"));
        List<String[]> itemList = new ArrayList<>();
        for(WebElement item: items){
            WebElement title = driver.findElement(By.xpath("//a[@class='wjcEIp']"));
            WebElement url = driver.findElement(By.xpath("//img[contains(@class, 'DByuf4')]"));
            WebElement review = driver.findElement(By.xpath("//span[contains(@class, 'Wphh3N')]"));

            String titleText = title.getText();
            String imageUrl = url.getAttribute("src");
            String reviewsText = review.getText();

            //numerical value of the reviews
            int numberOfReviews = Integer.parseInt(reviewsText.replaceAll("[^0-9]", ""));

            //Add item details to the list
            itemList.add(new String[]{titleText, imageUrl, String.valueOf(numberOfReviews)});

        }
        //Sort items by number of reviews and select the top 5
        itemList.sort((item1, item2) -> Integer.compare(Integer.parseInt(item2[2]), Integer.parseInt(item1[2])));
        List<String[]> topFiveItems = itemList.stream().limit(5).collect(Collectors.toList());

        //Titles and Image URLs of the top 5 items
        for (String[] item : topFiveItems) {
            System.out.println("Title: " + item[0] + ", Image URL: " + item[1]);
        }

    }

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}