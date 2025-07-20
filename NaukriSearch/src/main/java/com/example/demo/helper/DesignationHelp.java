package com.example.demo.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
public class DesignationHelp {

	
	public void designationFiller(WebDriverWait wait) {
		WebElement searchWrapper = wait.until(ExpectedConditions.elementToBeClickable(
		    By.cssSelector("span.nI-gNb-sb__placeholder")
		));
		searchWrapper.click();

		// Step 2: Now wait for the actual input box to become visible
		WebElement inputBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
		    By.cssSelector("input.suggestor-input")
		));

		// Step 3: Send your keywords
		inputBox.sendKeys("Java Developer");
	}
}
