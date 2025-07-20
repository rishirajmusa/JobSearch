package com.example.demo.helper;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.Service.EmailService;

@Service
public class GetJobHelper {

    private static final Logger logger = LoggerFactory.getLogger(GetJobHelper.class);
    private String lastJobTitle = "";

    public void selectJavaJob(WebDriver driver, EmailService emailService) {
        try {
            WebElement topJobCard = driver.findElement(By.cssSelector("div.cust-job-tuple"));
            WebElement titleElement = topJobCard.findElement(By.cssSelector("h2 a.title"));

            String currentJobTitle = titleElement.getText().trim();
            String jobLink = ((RemoteWebElement) titleElement).getDomAttribute("href");

            logger.info("Top Job: {}", currentJobTitle);

            WebElement firstJobCard = driver.findElement(By.cssSelector("div.srp-jobtuple-wrapper"));
            List<WebElement> skillElements = firstJobCard.findElements(By.cssSelector("div.row5 ul.tags-gt li.tag-li"));

            List<String> skills = new ArrayList<>();
            for (WebElement skill : skillElements) {
                skills.add(skill.getText().toLowerCase());
            }

            boolean isJavaRelated = isJavaSkill(skills) || containsJavaKeywords(currentJobTitle);

            if (!currentJobTitle.equalsIgnoreCase(lastJobTitle) && isJavaRelated) {
                lastJobTitle = currentJobTitle;

                String subject = "🧠 New Java Job Found!";
                String body = "Title: " + currentJobTitle + "\n\nApply here: " + jobLink;
                emailService.sendEmail(subject, body);

                logger.info("Email sent for new Java job: {}", currentJobTitle);
            } else {
                logger.info("Job already processed or not Java related.");
            }
        } catch (Exception e) {
            logger.error("Error while processing job: ", e);
        }
    }

    private boolean isJavaSkill(List<String> skills) {
        return skills.stream().anyMatch(skill ->
            skill.contains("java") ||
            skill.contains("corejava") ||
            skill.contains("core java") ||
            skill.contains("advanced java") ||
            skill.contains("java developer") ||
            skill.contains("java programming") ||
            skill.contains("jdk") ||
            skill.contains("spring") ||
            skill.contains("spring boot") ||
            skill.contains("rest api") ||
            skill.contains("oops") ||
            skill.contains("object oriented programming") ||
            skill.contains("sql") ||
            skill.contains("development") ||
            skill.contains("object") ||
            skill.contains("software") ||
            skill.contains("programming") ||
            skill.contains("software development")
        );
    }

    private boolean containsJavaKeywords(String title) {
        String lower = title.toLowerCase();
        return lower.matches(".*\\b(java|software developer|software engineer|backend|"
                + "application|eclipse|computer science|spring boot|"
                + "microservices|spring|sql|mysql|coding|development)\\b.*");
    }
}
