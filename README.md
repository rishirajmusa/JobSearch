
# Job Alert Automation with Selenium and ChromeDriver

This project is a job search automation tool built using **Selenium** and 
**ChromeDriver**. It automatically searches for the latest job postings from 
specific websites and sends email notifications when new jobs match your criteria.


## 🚀 Features

- Automated job search using Selenium  
- Customizable search filters (keywords, location, etc.)  
- Scrapes latest job listings from target websites  
- Sends email notifications with job details  
- Scheduled execution support (via cron or task scheduler)  


## 🛠️ Technologies Used

- **Java**  
- **Selenium WebDriver**  
- **ChromeDriver**  
- **Gmail SMTP API** (or any mail sender)  
- **Maven**  

## ✅ Requirements

- macOS or Linux (tested)  
- Java 11+  
- Maven  
- Chrome browser installed  
- [ChromeDriver](https://googlechromelabs.github.io/chrome-for-testing/)  
  installed and accessible via `/usr/local/bin`  

## 🔧 Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/rishirajmusa/Naukri.git
cd Naukri
````

### 2. Add ChromeDriver to your system (if not done already)

Download ChromeDriver for macOS ARM64 from:
[https://googlechromelabs.github.io/chrome-for-testing/](https://googlechromelabs.github.io/chrome-for-testing/)

Extract it and move it to `/usr/local/bin`:

```bash
sudo mv chromedriver /usr/local/bin/
sudo chmod +x /usr/local/bin/chromedriver
```

### 3. Configure email settings

Update `application.properties` with:

* Your sender email
* Your email password or app-specific password
* The recipient email address

### 4. Build the project

```bash
mvn clean package
```


## 🏃 Running the Project

After packaging, create a shell script (e.g., `run-job-alert.sh`) with the
following content:

```bash
#!/bin/bash

# Start the Java job in background
java -jar NaukriSearch-0.0.1-SNAPSHOT.jar &

# Get the PID
pid=$!

# Prevent sleep until that PID ends
caffeinate -w $pid



```

Make the script executable:

```bash
chmod +x run-job-alert.sh
```

Run it using:

```bash
./run-job-alert.sh
```

> ✅ **Note for macOS users**:
> If you're running the script continuously (e.g., 24X7), make sure to
> disable **automatic logout** in macOS system settings. Otherwise, your
> background process may be interrupted.


## 📬 Email Notification

Each time new jobs are found, the system will:

* Format job listings (title, company, link)
* Send an email with these details to your configured address


## 🕒 Scheduling (Optional)

You can schedule this project to run automatically using:

### On macOS (with `launchd`)

Create a `.plist` file to run the JAR on a schedule using `launchd`.

### Or via cron (Linux):

```bash
0 * * * * cd /path/to/project && mvn exec:java \
-Dexec.mainClass="com.rishiraj.jobalert.Main"
```


## 📄 License

This project is licensed under the [MIT License](LICENSE).


## 🙋‍♂️ Author

Made with ❤️ by [Rishi Raj](https://github.com/rishirajmusa)

