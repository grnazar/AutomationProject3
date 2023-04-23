import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

    public class CarGurus {
        @Test
        public void AutomateCargurusSite() throws InterruptedException {

            WebDriver driver = new EdgeDriver();
            System.out.println("Testing starts");
            driver.manage().window().maximize();
            driver.get("https://www.cargurus.com");


            driver.findElement(By.className("ft-homepage-search__tabs__used-car")).click();

            String actualMake = driver.findElement(By.cssSelector("#carPickerUsed_makerSelect > option")).getText();
            String exeptedMake = "All Makes";
            Assert.assertEquals(actualMake, exeptedMake);

            driver.findElement(By.id("carPickerUsed_makerSelect")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"carPickerUsed_makerSelect\"]/optgroup[2]/option[52]")).click();


            String actualModel = driver.findElement(By.cssSelector("#carPickerUsed_modelSelect > option")).getText();
            String exeptedModel = "All Models";
            Assert.assertEquals(actualModel, exeptedModel);

            Select select = new Select(driver.findElement(By.id("carPickerUsed_modelSelect")));
            List<WebElement> options = select.getOptions();
            List<String> expectedOptions = Arrays.asList("All Models", "Aventador", "Gallardo", "Huracan", "Urus", "400GT", "Centenario", "Countach", "Diablo", "Espada", "Murcielago");
            List<String> actualOptions = new ArrayList<String>();
            for (WebElement option : options) {
                actualOptions.add(option.getText());
            }
            Assert.assertEquals(actualOptions, expectedOptions);


            select.selectByVisibleText("Gallardo");


            driver.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031");
            driver.findElement(By.id("dealFinderForm_0")).click();
            Thread.sleep(2000);


            List<WebElement> searchResults = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]"));
            Assert.assertEquals(searchResults.size(), 15);


            for (WebElement result : searchResults) {
                String title = result.findElement(By.xpath("//*[@id=\"cargurus-listing-search\"]/div/div/div[2]/div[2]/div[1]/div/div")).getText();
                Assert.assertTrue(title.contains("Lamborghini Gallardo"));
            }


            List<WebElement> prices = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]//span[@class='cg-dealFinder-result-price']"));
            List<String> originalPrices = new ArrayList<String>();
            for (WebElement price : prices) {
                originalPrices.add(price.getText());
            }
            List<String> sortedPrices = new ArrayList<String>(originalPrices);
            Collections.sort(sortedPrices);
            Assert.assertEquals(originalPrices, sortedPrices);


            List<WebElement> mileage = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]//span[@class='cg-dealFinder-result-mileage']"));
            List<String> originalMileage = new ArrayList<String>();
            for (WebElement mile : mileage) {
                originalMileage.add(mile.getText());
            }
            List<String> sortedMileage = new ArrayList<String>(originalMileage);
            Collections.sort(sortedMileage);
            Collections.reverse(sortedMileage);
            Assert.assertEquals(originalMileage, sortedMileage);


            WebElement coupeAwdCheckbox = driver.findElement(By.cssSelector("#cargurus-listing-search > div > div > div.Km1Vfz > div.hhhhHl > div.Fu6p_D > div:nth-child(2) > fieldset:nth-child(5) > ul > li:nth-child(1)"));
            coupeAwdCheckbox.click();
            Thread.sleep(2000);
            boolean allResultsContainCoupeAwd = true;
            for (WebElement result : driver.findElements(By.xpath("//div[@class='cg-listingCard']"))) {
                if (!result.getText().contains("Coupe AWD")) {
                    allResultsContainCoupeAwd = false;
                    break;
                }
            }


            if (allResultsContainCoupeAwd) {
                System.out.println("All results contain Coupe AWD.");
            } else {
                System.out.println("Not all results contain Coupe AWD.");
            }


            List<WebElement> lastResult = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]"));
            lastResult.get(lastResult.size() - 1).click();
            Thread.sleep(2000);


            driver.navigate().back();
            Thread.sleep(2000);
            List<WebElement> viewed = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]//span[@class='cg-dealFinder-result-viewed']"));
            for (WebElement view : viewed) {
                String viewText = view.getText();
                Assert.assertTrue(viewText.contains("Viewed"));
            }
            driver.quit();

        }
    }
