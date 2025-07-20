package com.example.demo.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;


	
	@Service
	public class SortingHelper {
	    public void sorting(WebDriverWait wait) {
	        WebElement sortDropdownButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.id("filter-sort")
	        ));
	        sortDropdownButton.click();

	        WebElement dateOption = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//ul[@data-filter-id='sort']//li/a[@data-id='filter-sort-f']")
	        ));
	        dateOption.click();
	    }
	}


