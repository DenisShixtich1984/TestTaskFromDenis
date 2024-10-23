import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LoginPage {

    private AndroidDriver driver;
    private final By usernameField = By.id("com.alfabank.qapp:id/etUsername");
    private final By passwordField = By.id("com.alfabank.qapp:id/etPassword");
    private final By buttonConfirm = By.id("com.alfabank.qapp:id/btnConfirm");
    private final By textAlfaTestBeforeEntered = By.className("android.widget.TextView");
    private final By textAlfaTestCompleted = By.xpath("//android.widget.TextView[@text='Вход в Alfa-Test выполнен']");
    private final By errorTextAfterWrongData = By.id("com.alfabank.qapp:id/tvError");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickConfirmButton() {
        driver.findElement(buttonConfirm).click();
    }

    public String getTextBeforeEntered() {
        return driver.findElement(textAlfaTestBeforeEntered).getText();
    }

    public boolean isTextCompletedDisplayed() {
        try {
            return driver.findElement(textAlfaTestCompleted).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorTextAfterWrongDataDisplayed() {
        try {
            return driver.findElement(errorTextAfterWrongData).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTextCompleted() {
        return driver.findElement(textAlfaTestCompleted).getText();
    }

    public String getErrorTextWrongData() {
        return driver.findElement(errorTextAfterWrongData).getText();
    }

    public String getEnteredUsername() {
        return driver.findElement(usernameField).getText();
    }
}
