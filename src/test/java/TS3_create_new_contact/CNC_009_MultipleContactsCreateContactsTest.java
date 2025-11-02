package TS3_create_new_contact;

import base.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.AddContactPage;
import pages.MyContactsPage;
import utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CNC_009_MultipleContactsCreateContactsTest extends BaseTest {
    private MyContactsPage myContactsPage;
    private AddContactPage addContactPage;
    private APICalls apiCalls;
    private List<Object[]> addedContacts = new ArrayList<>();

    private static final String EMAIL = System.getenv("EMAIL");
    private static final String PASSWORD = System.getenv("PASSWORD");

    // This method provides the data source for the parameterized test
    public static Stream<Arguments> dataProvider() throws IOException, SQLException {
        String dataSource = ConfigLoader.getDataSource();

        switch (dataSource) {
            case "csv":
                return CSVReader.fetchDataFromCSV().stream().map(row -> Arguments.of((Object) row));
            case "mysql":
                MySQLReader mySQLReader = new MySQLReader();
                return mySQLReader.fetchDataFromMySQL().stream().map(row -> Arguments.of((Object) row));
            case "postgresql":
                PostgreSQLReader postgreSQLReader = new PostgreSQLReader();
                return postgreSQLReader.fetchDataFromPostgreSQL().stream().map(row -> Arguments.of((Object) row));
            default:
                throw new IllegalArgumentException("Unsupported data source: " + dataSource);
        }
    }

    // Setup specific to test
    @BeforeAll
    void setUpOnce() throws InterruptedException {
        //Step 2 - Login
        myContactsPage = loginPage.login(EMAIL, PASSWORD);
        // Initialize apiCalls object
        apiCalls = new APICalls();
    }

    @AfterAll
    void tearDownTestSpecific() throws Exception {
        //Delete contact
        for (int i = 0; i < addedContacts.size(); i++) {
            String contactID = myContactsPage.getFirstContactID();
            apiCalls.deleteContact(driver, contactID);
            myContactsPage.refreshPage();
        }
        apiCalls.closeHttpClient();
    }

    //Test
    @ParameterizedTest
    @MethodSource("dataProvider")
    void addMultipleContactsTest(Object[] contactData) {

        //Assign fetched values
        String firstName = contactData[0] != null ? (String) contactData[0] : "";
        String lastName = contactData[1] != null ? (String) contactData[1] : "";
        String dateOfBirth = contactData[2] != null ? contactData[2].toString() : "";
        String email = contactData[3] != null ? (String) contactData[3] : "";
        String phone = contactData[4] != null ? (String) contactData[4] : "";
        String streetAddress1 = contactData[5] != null ? (String) contactData[5] : "";
        String streetAddress2 = contactData[6] != null ? (String) contactData[6] : "";
        String city = contactData[7] != null ? (String) contactData[7] : "";
        String stateProvince = contactData[8] != null ? (String) contactData[8] : "";
        String postalCode = contactData[9] != null ? (String) contactData[9] : "";
        String country = contactData[10] != null ? (String) contactData[10] : "";

        //Create values as displayed in the contact list table
        String fullName = firstName + " " + lastName;
        String cityStateProvincePostalCode = String.join(" ", Arrays.stream(new String[]{city, stateProvince, postalCode})
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList()));
        String fullAddress = String.join(" ", Arrays.stream(new String[]{streetAddress1, streetAddress2})
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList()));

        //Step 3 - Open 'Add Contact' page
        addContactPage = myContactsPage.openAddContact();

        //Action - Add contact
        myContactsPage = addContactPage.addContact(firstName,lastName,dateOfBirth,email,phone,streetAddress1,
                streetAddress2, city,stateProvince,postalCode,country);

        //Add contact to array list
        addedContacts.add(contactData);

        //Actual number of rows
        int actualRowsCount = myContactsPage.countTableRows();

        //Expected number of rows
        int expectedRowCount = addedContacts.size() + 1;

        //Evaluate actual presence of data
        boolean isNamePresent = myContactsPage.isTextDisplayedInAnyTd(fullName);
        boolean isBirthDatePresent = myContactsPage.isTextDisplayedInAnyTd(dateOfBirth);
        boolean isEmailPresent = myContactsPage.isTextDisplayedInAnyTd(email);
        boolean isPhonePresent = myContactsPage.isTextDisplayedInAnyTd(phone);
        boolean isAddressPresent = myContactsPage.isTextDisplayedInAnyTd(fullAddress);
        boolean isCityStateProvincePostalCodePresent = myContactsPage.isTextDisplayedInAnyTd(cityStateProvincePostalCode);
        boolean isCountryPresent = myContactsPage.isTextDisplayedInAnyTd(country);
        boolean isLogoutButtonDisplayed = myContactsPage.isLogoutButtonDisplayed();
        boolean isAddNewContactDisplayed = myContactsPage.isAddNewContactButtonDisplayed();


        //Assertions
        assertAll(
                () -> assertEquals(expectedRowCount, actualRowsCount,"Not all contacts were added."),
                //Name is present
                () -> assertTrue(isNamePresent, "First Name is not present"),
                //Birthdate is present
                () -> assertTrue(isBirthDatePresent, "Birthdate is not present"),
                //Email is present
                () -> assertTrue(isEmailPresent, "Email is not present"),
                //Phone is present
                () -> assertTrue(isPhonePresent, "Phone is not present"),
                //Address is present
                () -> assertTrue(isAddressPresent, "Address is not present"),
                //City, State/Province and Postal Code are present
                () -> assertTrue(isCityStateProvincePostalCodePresent, "City/state/province is not present"),
                //Country is present
                () -> assertTrue(isCountryPresent, "Country is not present"),
                //Logout button displayed
                () -> assertTrue(isLogoutButtonDisplayed,
                        "The logout button wasn't loaded."),
                //Add a New Contact button displayed
                () -> assertTrue(isAddNewContactDisplayed,
                        "The 'Add a New Contact' button wasn't loaded."));
    }
}
