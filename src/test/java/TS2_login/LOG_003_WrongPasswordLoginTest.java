package TS2_login;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.MyContactsPage;

import static org.junit.jupiter.api.Assertions.*;

public class LOG_003_WrongPasswordLoginTest extends BaseTest {

    //Fields
    private MyContactsPage myContactsPage;
    private static final String EMAIL = System.getenv("EMAIL");

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS2_login/LOG_003_WrongPasswordLogin_data.csv", numLinesToSkip = 1,
            delimiter = ';')
    void wrongPasswordLoginTest(String password, String expectedError, String expectedURL, String expectedTitle,
                                String expectedHeading) {

        //Action - Login
        myContactsPage = loginPage.login(EMAIL, password);

        //Fetch actual values
        String error = myContactsPage.getErrorText();
        String url = myContactsPage.getURL(expectedURL);
        String title = myContactsPage.getTitle(expectedTitle);
        String h1Heading = myContactsPage.getHeading(expectedHeading);
        Boolean isLogoutButtonDisplayed = myContactsPage.isLogoutButtonDisplayed();

        //Assertions
        assertAll(
                //URL is correct
                () -> assertEquals(expectedURL,url,"The URL is incorrect."),
                //Title of the page is correct
                () -> assertEquals(expectedTitle,title,"The title is incorrect."),
                //H1 heading of the page is correct
                () -> assertEquals(expectedHeading,h1Heading,"The heading is incorrect."),
                //Logout button not displayed
                () -> assertFalse(isLogoutButtonDisplayed,
                        "The logout button was displayed."),
                //Error message displayed as expected
                () -> assertEquals(expectedError, error,"The error message was incorrect/absent."));

    }


}
