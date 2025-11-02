package TS6_delete_contact;

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

public class DEL_001_SuccessfulContactDeleteTest extends BaseTest {

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
        apiCalls.closeHttpClient();
    }

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS6_delete_contact/DEL_001_SuccessfulContactDelete_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void contactDeleteTest(String firstName, String lastName, String dateOfBirth, String email,
                                                    String phone, String street1, String street2, String city,
                                                    String stateProvince, String postalCode, String country,
                                                    String expectedURL, String expectedTitle, String expectedHeading)
            throws IOException, InterruptedException {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Step 3 - Add contact via API
        apiCalls.createContact(driver, firstName, lastName, dateOfBirth, email, phone, street1, street2, city,
                stateProvince, postalCode, country);
        myContactsPage.refreshPage();

        //Step 4 - Open 'Contact Details' page
        contactDetailsPage = myContactsPage.openContactDetails();

        //Action - Delete Contact
        myContactsPage = contactDetailsPage.deleteContact();

        //Fetch actual values
        Boolean isContactPresent = myContactsPage.isContactDisplayedInTable();
        String url = myContactsPage.getURL(expectedURL);
        String title = myContactsPage.getTitle(expectedTitle);
        String h1Heading = myContactsPage.getHeading(expectedHeading);
        Boolean isAddNewContactDisplayed = myContactsPage.isAddNewContactButtonDisplayed();
        Boolean isLogoutButtonDisplayed = myContactsPage.isLogoutButtonDisplayed();



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
                        "The logout button wasn't loaded."),
                //Add a New Contact button displayed
                () -> assertTrue(isAddNewContactDisplayed,
                        "The 'Add a New Contact' button wasn't loaded."),
                //The contact is absent after deletion
                () -> assertFalse(isContactPresent,"The contact is present after deletion."));
    }
}
