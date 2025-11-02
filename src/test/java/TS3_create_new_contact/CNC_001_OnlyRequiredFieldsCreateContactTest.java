package TS3_create_new_contact;

import base.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.AddContactPage;
import pages.MyContactsPage;
import utils.APICalls;

import static org.junit.jupiter.api.Assertions.*;

public class CNC_001_OnlyRequiredFieldsCreateContactTest extends BaseTest {

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
        //Delete contact
        String contactID = myContactsPage.getFirstContactID();
        apiCalls.deleteContact(driver, contactID);
        apiCalls.closeHttpClient();
    }

    // Test
    @ParameterizedTest
    @CsvFileSource(resources = "/TS3_create_new_contact/CNC_001_OnlyRequiredFieldsCreateContact_data.csv",
            numLinesToSkip = 1, delimiter = ';')
    void onlyRequiredFieldsCreateContactTest(String firstName, String lastName, String expectedFullName,
                                                    String expectedURL, String expectedTitle, String expectedHeading) {

        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);

        //Step 3 - Open 'Add Contact' page
        addContactPage = myContactsPage.openAddContact();

        //Action - Add contact
        myContactsPage = addContactPage.addContact(firstName, lastName);

        //Fetch actual values
        String fullName = myContactsPage.getFullNameText();
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
                //Contact name displayed & correct
                () -> assertEquals(expectedFullName, fullName,
                        "The contact name is different/absent."),
                //Logout button displayed
                () -> assertTrue(isLogoutButtonDisplayed,
                        "The logout button wasn't displayed."),
                //Add a New Contact button displayed
                () -> assertTrue(isAddNewContactDisplayed,
                        "The 'Add a New Contact' button wasn't displayed."));
    }
}
