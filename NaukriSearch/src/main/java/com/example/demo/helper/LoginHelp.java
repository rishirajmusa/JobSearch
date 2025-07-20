package com.example.demo.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.Service.JobCheckService;

@Service
public class LoginHelp {
	
	@Value("${login.username}")
    private String user;

    @Value("${login.password}")
    private String password;

    private static final Logger logger = LoggerFactory.getLogger(JobCheckService.class);
	public void login(WebDriver driver) throws InterruptedException {
		//System.out.println("Trying to Login");
		WebElement emailField = driver.findElement(By.id("usernameField")); // or real ID
		emailField.sendKeys(user);

		WebElement passwordField = driver.findElement(By.id("passwordField")); // or real ID
		passwordField.sendKeys(password);
		WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		submitBtn.click();
		//System.out.println("Logged in");
		logger.info("Logged in");
		Thread.sleep(3000);
	}
}
