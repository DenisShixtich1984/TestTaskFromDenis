import com.github.javafaker.Faker;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginAlfaTests {
    private LoginPage loginPage;
    private Faker faker;
    private final String Login = "Login";
    private final String Password = "Password";
    private final String EmptySpace = "";

    private final String textEnterAlfaTest = "Вход в Alfa-Test выполнен";
    private final String textErrorMessageWrongData = "Введены неверные данные";

    @BeforeTest
    public void setUp() {
        loginPage = new LoginPage(DriverManager.getDriver());
        faker = new Faker();
    }


    @Test
    public void testLogin() {
        loginPage.enterUsername(Login);
        loginPage.enterPassword(Password);
        loginPage.clickConfirmButton();

        ConditionFactory awaitility = Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .pollInterval(Duration.ofSeconds(2)).ignoreExceptions();

        awaitility.until(() -> loginPage.isTextCompletedDisplayed());

        Assert.assertEquals(loginPage.getTextCompleted(), textEnterAlfaTest);

    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] provideInvalidCredentials() {
        return new Object[][]{
                {faker.internet().domainName(), Password},
                {Login, faker.internet().domainName()},
                {Login, EmptySpace},
                {EmptySpace, Password},
                {"Логин", faker.internet().domainName()}};
    }

    @Test(dataProvider = "invalidCredentials")
    public void testInvalidLoginAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickConfirmButton();

        ConditionFactory awaitility = Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .pollInterval(Duration.ofSeconds(2)).ignoreExceptions();

        awaitility.until(() -> loginPage.isErrorTextAfterWrongDataDisplayed());

        Assert.assertEquals(loginPage.getErrorTextWrongData(), textErrorMessageWrongData);
    }

    @Test
    public void AlfaTestMaxLengthOfLogin() {
        String maxLengthLogin = "A".repeat(51);
        loginPage.enterUsername(maxLengthLogin);
        Assert.assertEquals(loginPage.getEnteredUsername().length(), 51);

    }

    @AfterTest
    public void tearDown() {
        DriverManager.closeDriver();
    }
}
