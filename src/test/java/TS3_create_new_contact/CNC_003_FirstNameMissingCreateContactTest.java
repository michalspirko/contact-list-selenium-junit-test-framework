package TS3_create_new_contact;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.AddContactPage;
import pages.MyContactsPage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CNC_003_FirstNameMissingCreateContactTest extends BaseTest {

    //Fields
    private MyContactsPage myContactsPage;
    private AddContactPage addContactPage;
    private static final String EMAIL = System.getenv("EMAIL");
    private static final String PASSWORD = System.getenv("PASSWORD");

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS3_create_new_contact/CNC_003_FirstNameMissingCreateContact_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void firstNameMissingCreateContactTest(String firstName, String lastName, String expectedError, String expectedURL,
                                                  String expectedTitle, String expectedHeading) {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Step 3 - Open 'Add Contact' page
        addContactPage = myContactsPage.openAddContact();

        //Action - Add contact
        myContactsPage = addContactPage.addContact(firstName, lastName);

        //Fetch actual values
        String error = myContactsPage.getErrorText();
        String url = myContactsPage.getURL(expectedURL);
        String title = myContactsPage.getTitle(expectedTitle);
        String h1Heading = myContactsPage.getHeading(expectedHeading);

        //Assertions
        assertAll(
                //URL is correct
                () -> assertEquals(expectedURL,url,"The URL is incorrect."),
                //Title of the page is correct
                () -> assertEquals(expectedTitle,title,"The title is incorrect."),
                //H1 heading of the page is correct
                () -> assertEquals(expectedHeading,h1Heading,"The heading is incorrect."),
                //Error message displayed as expected
                () -> assertEquals(expectedError, error,"The error message was incorrect/absent."));
    }

}
