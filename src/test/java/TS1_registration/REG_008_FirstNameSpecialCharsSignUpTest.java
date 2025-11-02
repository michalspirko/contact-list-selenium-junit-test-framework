package TS1_registration;

import base.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.MyContactsPage;
import pages.SignUpPage;
import utils.APICalls;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class REG_008_FirstNameSpecialCharsSignUpTest extends BaseTest {
    //Fields
    private APICalls apiCalls;
    private MyContactsPage myContactsPage;
    private SignUpPage signupPage;

    //Setup specific for test
    @BeforeEach
    void setUpTestSpecific() throws InterruptedException {
        // Initialize apiCalls object
        apiCalls = new APICalls();
    }

    //Cleanup specific for test
    @AfterEach
    void tearDownTestSpecific() throws IOException, InterruptedException {
        //Delete user if the test fails
        apiCalls.deleteUser(driver);
        apiCalls.closeHttpClient();
    }

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS1_registration/REG_008_FirstNameSpecialCharsSignUp_data.csv", numLinesToSkip = 1,
            delimiter = ';')
    void firstNameSpecialCharsSignUpTest(String firstName, String lastName, String email, String password,
                                                String expectedError, String expectedURL,
                                                String expectedTitle, String expectedHeading) {
        //Step 2 - Open 'Signup Page'
        signupPage = loginPage.openSignupPage();

        //Action - Signup
        myContactsPage = signupPage.validSignup(firstName, lastName, email, password);

        //Fetch actual values
        String error = signupPage.getErrorText();
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
                () -> assertEquals(expectedError, error, "The error message was incorrect/absent."));
    }
}
