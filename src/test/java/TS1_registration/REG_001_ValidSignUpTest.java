package TS1_registration;

import base.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.MyContactsPage;
import pages.SignUpPage;
import utils.APICalls;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class REG_001_ValidSignUpTest extends BaseTest {

    // Fields
    private APICalls apiCalls;
    private MyContactsPage myContactsPage;
    private SignUpPage signupPage;

    // Teardown specific for test
    @AfterEach
    void tearDownTestSpecific() throws IOException, InterruptedException {
        //Delete user
        apiCalls = new APICalls();
        apiCalls.deleteUser(driver);
        apiCalls.closeHttpClient();
    }

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS1_registration/REG_001_ValidSignUp_data.csv", numLinesToSkip = 1,
            delimiter = ';')
    void validSignUpTest(String firstName, String lastName, String email, String password, String expectedURL,
                                String expectedTitle, String expectedHeading) {
        //Step 2 - Open 'Signup Page'
        signupPage = loginPage.openSignupPage();

        //Action - Signup
        myContactsPage = signupPage.validSignup(firstName, lastName, email, password);

        //Fetch actual values
        String url = myContactsPage.getURL(expectedURL);
        String title = myContactsPage.getTitle(expectedTitle);
        String h1Heading = myContactsPage.getHeading(expectedHeading);
        Boolean isAddNewContactButtonDisplayed = myContactsPage.isAddNewContactButtonDisplayed();
        Boolean isLogoutButtonDisplayed = myContactsPage.isLogoutButtonDisplayed();

        // Assertions
        assertAll(
                // URL is correct
                () -> assertEquals(expectedURL,url,"The URL is incorrect."),
                // Title of the page is correct
                () -> assertEquals(expectedTitle,title,"The title is incorrect."),
                // H1 heading of the page is correct
                () -> assertEquals(expectedHeading,h1Heading,"The heading is incorrect."),
                // Add a New Contact button displayed
                () -> assertTrue(isAddNewContactButtonDisplayed,
                        "The 'Add a New Contact' button wasn't displayed."),
                // Logout button is visible
                () -> assertTrue(isLogoutButtonDisplayed,
                        "The logout button wasn't displayed."));
    }
}

