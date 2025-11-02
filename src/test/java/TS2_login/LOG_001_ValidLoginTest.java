package TS2_login;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.MyContactsPage;

import static org.junit.jupiter.api.Assertions.*;

public class LOG_001_ValidLoginTest extends BaseTest {
    //Fields
    private MyContactsPage myContactsPage;
    private static final String EMAIL = System.getenv("EMAIL");
    private static final String PASSWORD = System.getenv("PASSWORD");

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS2_login/LOG_001_ValidLogin_data.csv", numLinesToSkip = 1,
            delimiter = ';')
    void validLoginTest(String expectedURL, String expectedTitle, String expectedHeading) {

        //Action - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Fetch actual values
        String url = myContactsPage.getURL(expectedURL);
        String title = myContactsPage.getTitle(expectedTitle);
        String h1Heading = myContactsPage.getHeading(expectedHeading);
        Boolean isLogoutButtonDisplayed = myContactsPage.isLogoutButtonDisplayed();
        Boolean isAddNewContactDisplayed = myContactsPage.isAddNewContactButtonDisplayed();


        //Assertions
        assertAll(
                //URL is correct
                () -> assertEquals(expectedURL,url,"The URL is incorrect."),
                //Title of the page is correct
                () -> assertEquals(expectedTitle,title,"The title is incorrect."),
                //H1 heading of the page is correct
                () -> assertEquals(expectedHeading,h1Heading,"The heading is incorrect."),
                //Logout button displayed
                () -> assertTrue(isLogoutButtonDisplayed,
                        "The logout button wasn't displayed."),
                //Add a New Contact button displayed
                () -> assertTrue(isAddNewContactDisplayed,
                        "The 'Add a New Contact' button wasn't displayed."));
    }
}
