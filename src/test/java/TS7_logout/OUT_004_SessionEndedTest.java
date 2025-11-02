package TS7_logout;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.MyContactsPage;

import static org.junit.jupiter.api.Assertions.*;

public class OUT_004_SessionEndedTest extends BaseTest {
    //Fields
    private MyContactsPage myContactsPage;
    private static final String EMAIL = System.getenv("EMAIL");
    private static final String PASSWORD = System.getenv("PASSWORD");


    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS7_logout/OUT_004_SessionEnded_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void sessionEndedTest(String expectedURL, String expectedTitle, String expectedHeading) {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Step 3 - Logout
        loginPage = myContactsPage.logout();

        //Action - Go to previous page
        loginPage.navigateBack();

        //Fetch actual values
        String url = loginPage.getURL(expectedURL);
        String title = loginPage.getTitle(expectedTitle);
        String h1Heading = loginPage.getHeading(expectedHeading);
        Boolean isLogoutButtonLoaded = myContactsPage.isLogoutButtonDisplayed();
        Boolean isAddNewContactButtonLoaded = myContactsPage.isAddNewContactButtonDisplayed();


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
                        "The logout button was loaded."),
                //Add a New Contact button not displayed
                () -> assertFalse(isAddNewContactButtonLoaded,
                "The 'Add a New Contact' button wasn't loaded."));
    }

}
