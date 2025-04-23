package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;
        JavascriptExecutor js;
        String url = "https://www.youtube.com/";
        Wrappers wrapper;
        WebDriverWait wait;
        SoftAssert softAssert;

        @Test(enabled = true, priority = 1)
        public void testCase01() throws InterruptedException {
                System.out.println("Start testCase01");
                wrapper.goToURLAndWait(driver, url);
                String currentURL = driver.getCurrentUrl();
                // Comparing the url
                Assert.assertEquals(currentURL, url, "URL is not matching");
                WebElement aboutElement = driver.findElement(By.xpath("//a[contains(text(),'About')]"));
                // Scrolling to vew the element
                wrapper.click(aboutElement, driver);
                WebElement msgElement = driver
                                .findElement(By.xpath("//section[@class='ytabout__content']"));
                js.executeScript("arguments[0].scrollIntoView(true)", msgElement);
                // Waiting for message to apear on the screen after clicking about
                wait.until(ExpectedConditions.visibilityOf(msgElement));
                String msgText = msgElement.getText();
                // Printing the message
                System.out.println(msgText);
                System.out.println("End testCase01");

        }

        @Test(enabled = true, priority = 2)
        public void testCase02() throws InterruptedException {
                System.out.println("Start testCase02");
                wrapper.goToURLAndWait(driver, url);
                WebElement moviesElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//yt-formatted-string[contains(text(),'Movies')]")));
                // Clicking on the movies
                wrapper.click(moviesElement, driver);
                System.out.println("Applying thred sleep for 3 sec");
                Thread.sleep(3000);
                // xpath of Top Selling
                WebElement topSellingContainer = driver
                                .findElement(By.xpath("//div[@id='dismissible'][.//span[text()='Top selling']]"));
                WebElement arrowforTopSelling = topSellingContainer
                                .findElement(By.xpath(".//button[contains(@aria-label, 'Next')]"));
                js.executeScript("arguments[0].scrollIntoView(true)", arrowforTopSelling);
                wait.until(ExpectedConditions.visibilityOf(arrowforTopSelling));
                // clicking on arrow till it disappear
                wrapper.clickArrowUntilDisapear(arrowforTopSelling, driver);
                // getting all the movies in top selling
                List<WebElement> allMovies = topSellingContainer.findElements(By
                                .xpath(".//ytd-grid-movie-renderer[contains(@class, 'style-scope yt-horizontal-list-renderer')]"));
                // getting last movie from the movies
                WebElement lastMovie = allMovies.get(allMovies.size() - 1);
                // getting rating web element
                WebElement ratingElement = lastMovie
                                .findElement(By.xpath(
                                                ".//div[@class='badge  badge-style-type-simple style-scope ytd-badge-supported-renderer style-scope ytd-badge-supported-renderer']/p"));
                // getting text from the element
                String ratingText = ratingElement.getText().trim();
                System.out.println(ratingText);
                // comparing if text is same asone of the valid ratings:
                // 'A','U/A','U','U/A13+','U/A16+'
                softAssert.assertTrue(
                                ratingText.equals("A") ||
                                                ratingText.equals("U/A") ||
                                                ratingText.equals("U") ||
                                                ratingText.equals("U/A13+") ||
                                                ratingText.equals("U/A16+"),
                                "Movie rating is not marked as one of the valid ratings: 'A','U/A','U','U/A13+','U/A16+'");
                List<String> validCategories = Arrays.asList("Comedy", "Animation", "Drama");
                // getting the category element from the last movie
                String categoryTextRaw = lastMovie.findElement(By.xpath(
                                ".//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"))
                                .getText().trim();
                // Extracting only category part from the entire text
                String category = categoryTextRaw.split("[^A-Za-z]")[0].trim();
                System.out.println(category);
                // Comparing if category is one of the valid categories
                softAssert.assertTrue(validCategories.contains(category), "Movie category is invalid: " + category);
                softAssert.assertAll();
                System.out.println("End testCase02");

        }

        @Test(enabled = true, priority = 3)
        public void testCase03() throws InterruptedException {
                System.out.println("Start testCase03");
                wrapper.goToURLAndWait(driver, url);
                WebElement musicElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                "//yt-formatted-string[@class='title style-scope ytd-guide-entry-renderer'][normalize-space()='Music']")));
                wrapper.click(musicElement, driver);
                System.out.println("Applying thred sleep for 3 sec");
                Thread.sleep(3000);
                // getting the indias hit container element
                WebElement indasHitContainer = driver.findElement(By.xpath(
                                "//div[@id='dismissible'][.//span[contains(text(), \"India's Biggest Hits\")]]"));
                // scrolling to view the Indias hits section
                js.executeScript("arguments[0].scrollIntoView(true)", indasHitContainer);
                // getting all the albus into one list
                List<WebElement> albums = indasHitContainer.findElements(By.xpath(".//ytd-rich-item-renderer"));
                // List for only displayed albums for identifying rightmost album
                List<WebElement> displayedAlbums = new ArrayList<>();
                for (WebElement album : albums) {
                        if (album.isDisplayed()) {
                                displayedAlbums.add(album);
                        }
                }
                // getting rightmost/last displayed element from the list
                WebElement rightMostAlbum = displayedAlbums.get(displayedAlbums.size() - 1);
                // getting and print playlist name
                String playlistName = rightMostAlbum.findElement(By.xpath(
                                ".//span[@class='yt-core-attributed-string yt-core-attributed-string--white-space-pre-wrap']"))
                                .getText();
                System.out.println("Rightmost playlist name: " + playlistName);
                // getting number of album text from the album
                String numOfTrackText = rightMostAlbum.findElement(By.xpath(".//div[@class='badge-shape-wiz__text']"))
                                .getText();
                // converting number text into int value and removing other text
                int numOfTracks = Integer.parseInt(numOfTrackText.replaceAll("\\D+", ""));
                // comparing if number of tracks is equal or less than 50 or not
                softAssert.assertTrue(numOfTracks <= 50, "Track count is more than 50");
                softAssert.assertAll();
                System.out.println("End testCase03");

        }

        @Test(enabled = true, priority = 4)
        public void testCase04() throws InterruptedException {
                System.out.println("Start testCase04");
                wrapper.goToURLAndWait(driver, url);
                // Clicking on news tab
                WebElement newsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                "//yt-formatted-string[@class='title style-scope ytd-guide-entry-renderer'][normalize-space()='News']")));
                wrapper.click(newsElement, driver);
                System.out.println("Applying thred sleep for 3 sec");
                Thread.sleep(3000);
                // getting the whole news container into one element
                WebElement latestNewsContainer = driver.findElement(
                                By.xpath("//div[@id='dismissible'][.//span[contains(text(), 'Latest news posts')]]"));
                // getting all the news post element from the container in one list
                List<WebElement> allNews = latestNewsContainer.findElements(By.xpath(".//div[@id='dismissible']"));
                System.out.println(allNews.size());
                int totalLikes = 0;
                // limiting the size of container till 3 element as per requirnment
                int maxPosts = Math.min(allNews.size(), 3);
                // running loop for post for getting details
                for (int i = 0; i < maxPosts; i++) {
                        WebElement post = allNews.get(i);
                        // Extracting title from the each post till loop ends
                        String title = post.findElement(By
                                        .xpath("//a[@class='yt-simple-endpoint style-scope ytd-post-renderer']//span"))
                                        .getText();
                        System.out.println("Title " + (i + 1) + ": " + title);
                        // Extracting body from the each post till loop ends
                        String body = post.findElement(By.xpath(".//yt-formatted-string[@id='home-content-text']"))
                                        .getText();
                        System.out.println("Body " + (i + 1) + ": " + body);
                        // Extracting likes from the each post till loop ends
                        WebElement likeElement = post.findElement(By.xpath(".//span[@id='vote-count-middle']"));
                        String likesText = likeElement.getText();
                        long likes = 0;
                        // if we dont have likes till now it will simply continue the execution
                        // wrraper methed will give likes according to the condition if we have likes in
                        // K,M,B
                        likes = wrapper.convertToNumericValue(likesText);
                        System.out.println("Likes: " + likes);
                        System.out.println("-------------------");
                        // adding likes of each post in total likes
                        totalLikes += likes;
                }
                System.out.println("Total Likes for first 3 posts: " + totalLikes);
                System.out.println("End testCase04");
        }

        @Test(enabled = true, priority = 5)
        public void testCase05() throws InterruptedException {
                System.out.println("Start testCase05");
                // storing all the text to be searched into a array
                String[] searchItems = { "Movies", "Music", "Games", "India", "UK" };
                // running loop for all the searchItems
                for (String keyword : searchItems) {
                        wrapper.goToURLAndWait(driver, url);
                        System.out.println("\nSearching for: " + keyword);
                        WebElement searchBox = wait
                                        .until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath("//input[@name='search_query']")));
                        wrapper.sendKeys(searchBox, keyword);
                        searchBox.sendKeys(Keys.ENTER);
                        System.out.println("waiting for page load for 3 secound");
                        Thread.sleep(3000); // initial wait after search
                        // initalizing totalVies and scroll
                        long totalViews = 0;
                        int scrolls = 0;
                        // if views is >= 10cr will stop
                        while (totalViews < 100_000_000) {
                                // getting all the views element into a list
                                List<WebElement> viewElements = driver
                                                .findElements(By.xpath("//span[contains(text(),'views')]"));
                                // rinning loop for each views element
                                for (WebElement element : viewElements) {
                                        String text = element.getText().toLowerCase();
                                        // for each element it will convert the text into numeric value according to the
                                        // condition and store in totalViews
                                        totalViews += wrapper.convertViewsToNumber(text);
                                        // it will break the loop when we reached 10 cr
                                        if (totalViews >= 100000000)
                                                break;
                                }
                                System.out.println("views so far: " + totalViews);
                                js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
                                Thread.sleep(2000); // Wait for new content to load
                                scrolls++;
                                if (scrolls > 20) {
                                        System.out.println("Too many scrolls, breaking for keyword: " + keyword);
                                        break;
                                }
                        }
                        System.out.println("Reached 10 Crores+ views for: " + keyword);
                }
                System.out.println("End testCase05");
        }

        @BeforeTest
        public void startBrowser() {
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
                js = (JavascriptExecutor) driver;
                softAssert = new SoftAssert();
                wrapper = new Wrappers();
                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}