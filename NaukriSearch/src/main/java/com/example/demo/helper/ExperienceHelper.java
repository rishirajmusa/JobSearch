package com.example.demo.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
public class ExperienceHelper {

	public void experienceFiller(WebDriverWait wait) throws InterruptedException {
		WebElement experienceDD = wait.until(ExpectedConditions.elementToBeClickable(
		     By.id("experienceDD")
		 ));
		 experienceDD.click();

		 // Select the "Fresher" option
		 WebElement fresherOption = wait.until(ExpectedConditions.elementToBeClickable(
		     By.xpath("//div[contains(@class,'dropdownContainer')]//div[span[text()='Fresher']]")
		 ));
		 fresherOption.click();


		 // Click search button
		 wait.until(ExpectedConditions.elementToBeClickable(
				 By.xpath("//button[span[text()='Search']]")
		 )).click();
		 
		 Thread.sleep(3000);
	}
}
