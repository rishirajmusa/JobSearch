package com.example.demo.Service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.helper.DesignationHelp;
import com.example.demo.helper.ExperienceHelper;
import com.example.demo.helper.GetJobHelper;
import com.example.demo.helper.LoginHelp;
import com.example.demo.helper.SortingHelper;
@Service
public class JobCheckService {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final LoginHelp loginHelp;
    private final DesignationHelp designationHelp;
    private final ExperienceHelper experienceHelper;
    private final SortingHelper sortingHelper;
    private final GetJobHelper getJobHelper;
    private final EmailService emailService;
    private boolean alreadyLoggedIn = false;
    private static final Logger logger = LoggerFactory.getLogger(JobCheckService.class);

    //Constructor injection
    public JobCheckService(WebDriver driver,
                               WebDriverWait wait,
                               LoginHelp loginHelp,
                               DesignationHelp designationHelp,
                               ExperienceHelper experienceHelper,
                               SortingHelper sortingHelper,
                               GetJobHelper getJobHelper,
                               EmailService emailService) {
        this.driver = driver;
        this.wait = wait;
        this.loginHelp = loginHelp;
        this.designationHelp = designationHelp;
        this.experienceHelper = experienceHelper;
        this.sortingHelper = sortingHelper;
        this.getJobHelper = getJobHelper;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000)
    public void run() {
        if (!alreadyLoggedIn) {
            try {
                logger.info("Trying to login...");
                Thread.sleep(3000);
                driver.get("https://www.naukri.com/mnjuser/login");
                Thread.sleep(3000);

                try {
                    driver.findElement(By.cssSelector(".crossIcon")).click();
                    Thread.sleep(3000);
                } catch (Exception e) {
                    logger.warn("Popup close failed: " + e.getMessage());
                }

                try {
                    loginHelp.login(driver);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    logger.error("Login failed", e);
                }

                try {
                    designationHelp.designationFiller(wait);
                } catch (Exception e) {
                    logger.error("Designation fill failed", e);
                }

                try {
                    experienceHelper.experienceFiller(wait);
                } catch (Exception e) {
                    logger.error("Experience fill failed", e);
                }

                try {
                    sortingHelper.sorting(wait);
                } catch (Exception e) {
                    logger.error("Sorting failed", e);
                }

                Thread.sleep(4000);

                try {
                    getJobHelper.selectJavaJob(driver, emailService);
                } catch (Exception e) {
                    logger.error("Job selection failed", e);
                }

                alreadyLoggedIn = true;

            } catch (Exception e) {
                logger.error("Unexpected error during login", e);
            }
        } else {
            try {
                logger.info("Already logged in. Refreshing...");
                driver.navigate().refresh();
                Thread.sleep(3000);

                try {
                    sortingHelper.sorting(wait);
                } catch (Exception e) {
                    logger.warn("Sorting failed after refresh. Trying login again...", e);

                    try {
                        driver.get("https://www.naukri.com/mnjuser/login");
                        Thread.sleep(3000);
                        loginHelp.login(driver);
                        Thread.sleep(3000);
                    } catch (Exception loginEx) {
                        logger.error("Login retry failed", loginEx);
                    }

                    try {
                        designationHelp.designationFiller(wait);
                    } catch (Exception e1) {
                        logger.error("Designation fill failed", e1);
                    }

                    try {
                        experienceHelper.experienceFiller(wait);
                    } catch (Exception e2) {
                        logger.error("Experience fill failed", e2);
                    }
                }

                Thread.sleep(4000);

                try {
                    sortingHelper.sorting(wait);
                } catch (Exception e) {
                    logger.error("Final sorting failed", e);
                }

                try {
                    getJobHelper.selectJavaJob(driver, emailService);
                } catch (Exception e) {
                    logger.error("Job selection or email failed", e);
                }

            } catch (Exception e) {
                logger.error("Unexpected error in refresh phase", e);
            }
        }
    }
}









