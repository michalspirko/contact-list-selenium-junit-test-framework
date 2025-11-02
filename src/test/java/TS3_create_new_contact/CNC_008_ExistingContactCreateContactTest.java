package TS3_create_new_contact;

import base.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.AddContactPage;
import pages.MyContactsPage;
import utils.APICalls;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CNC_008_ExistingContactCreateContactTest extends BaseTest {

    //Fields
    private MyContactsPage myContactsPage;
    private AddContactPage addContactPage;
    private APICalls apiCalls;
    private static final String EMAIL = System.getenv("EMAIL");
    private static final String PASSWORD = System.getenv("PASSWORD");

    //Setup specific for test
    @BeforeEach
    void setUpTestSpecific() throws InterruptedException {
        // Initialize apiCalls object
        apiCalls = new APICalls();
    }

    //Cleanup specific for test
    @AfterEach
    void tearDownTestSpecific() throws Exception {
        //Delete contact if the test fails and the contact gets created
        String contactID1 = myContactsPage.getFirstContactID();
        apiCalls.deleteContact(driver, contactID1);
        myContactsPage.refreshPage();
        String contactID2 = myContactsPage.getFirstContactID();
        apiCalls.deleteContact(driver, contactID2);
        apiCalls.closeHttpClient();
    }

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS3_create_new_contact/CNC_008_ExistingContactCreateContact_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void existingContactCreateContactTest(String firstName, String lastName, String birthDate, String email,
                                                    String phone, String street1, String street2, String city,
                                                    String stateProvince, String postalCode, String country,
                                                    String expectedError, String expectedURL, String expectedTitle,
                                                    String expectedHeading)
            throws IOException, InterruptedException {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Step 3 - Add contact via API
        apiCalls.createContact(driver,firstName,lastName,birthDate,email, phone, street1,street2, city,stateProvince,
                postalCode, country);
        myContactsPage.refreshPage();

        //Step 4 - Open 'Add Contact' page
        addContactPage = myContactsPage.openAddContact();

        //Action - Add contact
        myContactsPage = addContactPage.addContact(firstName, lastName, email);

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
                () -> assertEquals(expectedError, error, "The error message is incorrect/absent."));
    }
}
