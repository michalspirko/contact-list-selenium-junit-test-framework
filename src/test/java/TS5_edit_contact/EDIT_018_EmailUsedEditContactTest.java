package TS5_edit_contact;

import base.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.ContactDetailsPage;
import pages.EditContactPage;
import pages.MyContactsPage;
import utils.APICalls;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EDIT_018_EmailUsedEditContactTest extends BaseTest {

    //Fields
    private MyContactsPage myContactsPage;
    private ContactDetailsPage contactDetailsPage;
    private EditContactPage editContactPage;

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
        myContactsPage = contactDetailsPage.openMyContactPage();
        String contactID1 = myContactsPage.getFirstContactID();
        apiCalls.deleteContact(driver, contactID1);
        myContactsPage.refreshPage();
        String contactID2 = myContactsPage.getFirstContactID();
        apiCalls.deleteContact(driver, contactID2);
        apiCalls.closeHttpClient();
    }

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS5_edit_contact/EDIT_018_EmailUsedEditContact_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void existingContactCreateContactTest(String firstName, String lastName, String birthDate, String email,
                                                 String phone, String street1, String street2, String city,
                                                 String stateProvince, String postalCode, String country,
                                                 String anotherFirstName, String anotherLastName, String anotherBirthDate,
                                                 String anotherEmail, String anotherPhone, String anotherStreet1,
                                                 String anotherStreet2, String anotherCity, String anotherStateProvince,
                                                 String anotherPostalCode, String anotherCountry, String expectedError,
                                                 String expectedURL, String expectedHeading)
            throws IOException, InterruptedException {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Step 3 - Add 2 contacts via API
        apiCalls.createContact(driver, firstName, lastName, birthDate, email, phone,
                street1, street2, city, stateProvince, postalCode, country);
        apiCalls.createContact(driver, anotherFirstName, anotherLastName, anotherBirthDate, anotherEmail, anotherPhone,
                anotherStreet1, anotherStreet2, anotherCity, anotherStateProvince, anotherPostalCode, anotherCountry);
        myContactsPage.refreshPage();

        //Step 4 - Open 'Contact Details' page
        contactDetailsPage = myContactsPage.openContactDetails();

        //Step 5 - Open 'Edit Contact' page
        editContactPage = contactDetailsPage.openEditContactPage();

        //Action - Edit Email
        contactDetailsPage = editContactPage.editEmailField(anotherEmail);

        //Fetch actual values
        String url = contactDetailsPage.getURL(expectedURL);
        String h1Heading = contactDetailsPage.getHeading(expectedHeading);
        String error = editContactPage.getErrorText();

        //Assertions
        assertAll(
                //URL is correct
                () -> assertEquals(expectedURL, url, "The URL is incorrect."),
                //H1 heading of the page is correct
                () -> assertEquals(expectedHeading, h1Heading, "The heading is incorrect."),
                //Error message displayed as expected
                () -> assertEquals(expectedError, error, "The error message was incorrect/absent."));
    }
}
