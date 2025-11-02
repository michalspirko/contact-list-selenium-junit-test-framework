package TS1_registration;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.MyContactsPage;
import pages.SignUpPage;

import static org.junit.jupiter.api.Assertions.*;

public class REG_010_ExistingEmailSignUpTest extends BaseTest {

    //Fields
    private SignUpPage signupPage;
    private MyContactsPage myContactsPage;

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS1_registration/REG_010_ExistingEmailSignup_data.csv", numLinesToSkip = 1,
            delimiter = ';')
    void existingEmailSignUpTest(String firstName, String lastName, String email, String password, String expectedError,
                                 String expectedURL, String expectedTitle, String expectedHeading) {
        //Step 2 - Open 'Signup Page'
        signupPage = loginPage.openSignupPage();

        //Action - Signup with an empty string for password
        myContactsPage = signupPage.missingPasswordSignUp(firstName, lastName, email, password);

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
                () -> assertEquals(expectedError, error,"The error message was incorrect/absent.")
        );
    }
}
