package TS7_logout;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.ContactDetailsPage;
import pages.MyContactsPage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OUT_001_SuccessfulLogoutTest extends BaseTest {

    //Fields
    private MyContactsPage myContactsPage;
    private ContactDetailsPage contactDetailsPage;

    private static final String EMAIL = System.getenv("EMAIL");
    private static final String PASSWORD = System.getenv("PASSWORD");


    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS7_logout/OUT_001_SuccessfulLogout_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void logoutTest(String expectedURL, String expectedTitle, String expectedHeading)
            throws IOException, InterruptedException {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Action - Logout
        loginPage = myContactsPage.logout();

        //Fetch actual values
        String url = myContactsPage.getURL(expectedURL);
        String title = myContactsPage.getTitle(expectedTitle);
        String h1Heading = myContactsPage.getHeading(expectedHeading);
        Boolean isLogoutButtonLoaded = myContactsPage.isLogoutButtonDisplayed();

        //Assertions
        assertAll(
                //URL is correct
                () -> assertEquals(expectedURL,url,"The URL is incorrect."),
                //Title of the page is correct
                () -> assertEquals(expectedTitle,title,"The title is incorrect."),
                //H1 heading of the page is correct
                () -> assertEquals(expectedHeading,h1Heading,"The heading is incorrect."),
                //Logout button not displayed
                () -> assertFalse(isLogoutButtonLoaded,
                        "The logout button was loaded."));
    }

}
