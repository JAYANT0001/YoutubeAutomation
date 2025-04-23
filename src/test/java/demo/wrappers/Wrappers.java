package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    

     
    public boolean click(WebElement element, WebDriver driver) {
        try {
            if (element != null && element.isDisplayed()) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);

                element.click();
                return true;
            } else {
                System.out.println("Element not found or not visible.");
                return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
            return false;
        } catch (ElementNotInteractableException e) {
            System.out.println("Element is not interactable: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred while clicking the element: " + e.getMessage());
            return false;
        }
    }

    public boolean sendKeys(WebElement element, String text) {
        try {

            if (element != null && element.isDisplayed()) {

                element.clear();

                element.sendKeys(text);
                System.out.println("Text entered successfully.");
                return true;
            } else {
                System.out.println("Element not found or not visible.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while entering text: " + e.getMessage());
            return false;
        }
    }

    public boolean navigateToURL(String url, WebDriver driver) {
        try {

            String currentURL = driver.getCurrentUrl();

            if (!currentURL.equals(url)) {
                driver.get(url);
                System.out.println("Navigated to the new URL: " + url);
                return true;
            } else {
                System.out.println(
                        "The given URL is the same as the current URL. No navigation needed.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while navigating to the URL: " + e.getMessage());
            return false;
        }
    }

    public WebElement findElementWithRetry(By by, WebDriver driver) {
        int retryCount = 0;
        WebElement element = null;

        while (retryCount < 3) {
            try {
                element = driver.findElement(by);
                System.out.println("Element found on attempt " + (retryCount + 1));
                return element;
            } catch (NoSuchElementException e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed. Retrying...");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println("Element not found after 3 attempts.");
        return null;
    }

   
    public void clickArrowUntilDisapear(WebElement arrowElemet, WebDriver driver) throws InterruptedException{
        while(true){
            try{
            if(arrowElemet.isEnabled()&&arrowElemet.isDisplayed()){
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", arrowElemet);
                arrowElemet.click();
                Thread.sleep(3000);
            }else{
                 break;
            }
        }catch (NoSuchElementException e) {
            break; 
        }
    }

    }

    public long convertToNumericValue(String value){
        value = value.trim().toUpperCase();
        char lastChar = value.charAt(value.length()-1);
        int multiplier = 1;
        switch(lastChar){
            case 'K':
            multiplier =1000;
            break;
            case 'M':
            multiplier = 1000000;
            break;
            case 'B':
            multiplier = 1000000000;
            break;
            default:
            if (Character.isDigit(lastChar)) {
                return Long.parseLong(value);
            }
            throw new IllegalArgumentException("Invalid format:" + value);
        }
        String numericPart = value.substring(0, value.length()-1);
        double number = Double.parseDouble(numericPart);

        return (long) (number*multiplier);
    }

    public void goToURLAndWait(WebDriver driver, String url){
        driver.navigate().to(url);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

    public long convertViewsToNumber(String viewText) {
        viewText = viewText.replace("views", "").trim();
        try {
            if (viewText.endsWith("K")) {
                return (long) (Double.parseDouble(viewText.replace("K", "")) * 1000);
            } else if (viewText.endsWith("M")) {
                return (long) (Double.parseDouble(viewText.replace("M", "")) * 1000000);
            } else if (viewText.endsWith("B")) {
                return (long) (Double.parseDouble(viewText.replace("B", "")) * 1000000000);
            } else {
                return Long.parseLong(viewText.replaceAll("\\D", ""));
            }
        } catch (Exception e) {
            return 0; 
        }
    }
}


