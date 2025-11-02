package TS4_display_contact_details;

import base.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.ContactDetailsPage;
import pages.MyContactsPage;
import utils.APICalls;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DCD_002_SomeFieldsEmptyDisplayContactDetailsTest extends BaseTest {

    //Fields
    private MyContactsPage myContactsPage;
    private ContactDetailsPage contactDetailsPage;
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
    }

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS4_display_contact_details/DCD_002_SomeFieldsEmptyDisplayContactDetails_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void someFieldsEmptyContactDisplayTest(String expectedFirstName, String expectedLastName,
                                                 String expectedBirthDate, String expectedEmail,
                                                 String expectedPhone, String expectedStreet1, String expectedStreet2,
                                                 String expectedCity, String expectedStateProvince,
                                                 String expectedPostalCode, String expectedCountry,
                                                 String expectedURL, String expectedHeading)
            throws IOException, InterruptedException {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Step 3 - Add contact via API
        apiCalls.createContact(driver, expectedFirstName, expectedLastName, expectedBirthDate, expectedEmail, expectedPhone,
                expectedStreet1, expectedStreet2, expectedCity, expectedStateProvince,
                expectedPostalCode, expectedCountry);
        myContactsPage.refreshPage();


        //Action - Open 'Contact Details' page
        contactDetailsPage = myContactsPage.openContactDetails();

        //Fetch actual values
        String url = contactDetailsPage.getURL(expectedURL);
        String h1Heading = contactDetailsPage.getHeading(expectedHeading);
        String firstName = contactDetailsPage.getFirstName();
        String lastName = contactDetailsPage.getLastName();
        Boolean editButtonLoaded = contactDetailsPage.isEditButtonDisplayed();
        Boolean deleteButtonLoaded = contactDetailsPage.isDeleteButtonDisplayed();
        Boolean returnButtonLoaded = contactDetailsPage.isReturnButtonDisplayed();


        //Assertions
        assertAll(
                //URL is correct
                () -> assertEquals(expectedURL, url, "The URL is incorrect."),
                //H1 heading of the page is correct
                () -> assertEquals(expectedHeading, h1Heading, "The heading is incorrect."),
                //Edit button is loaded
                () -> assertTrue(editButtonLoaded,"The Edit button wasn't loaded."),
                //Delete button is loaded
                () -> assertTrue(deleteButtonLoaded,"The Delete button wasn't loaded."),
                //Return button is loaded
                () -> assertTrue(returnButtonLoaded,"The Return button wasn't loaded."),
                //Contact Details are correct
                () -> assertEquals(expectedFirstName, firstName, "The first name is incorrect"),
                () -> assertEquals(expectedLastName, lastName, "The last name is incorrect."));
    }
}
