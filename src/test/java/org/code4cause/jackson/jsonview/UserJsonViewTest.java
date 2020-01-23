/**
 *
 */
package org.code4cause.jackson.jsonview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cause4code.jackson.jsonview.dto.User;
import org.cause4code.jackson.jsonview.view.View;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author amipatil
 *
 */
public class UserJsonViewTest {

    private static ObjectMapper objectMapper;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        objectMapper = buildObjectMapper();
    }

    @Test
    public void serializeUserWithExternalViewTest() throws JsonProcessingException {
        // GIVEN
        final User mockedUser = getMockedUser();

        // WHEN
        final String serializedValue = objectMapper
                .writerWithView(View.UserView.External.class)
                .writeValueAsString(mockedUser);

        // THEN
        final List<String> expectedFields = Arrays.asList("firstName", "lastName", "id");
        expectedFields.stream().forEach(field -> {
            assertTrue(serializedValue.contains(field));
        });
        final List<String> otherFields = Arrays.asList("ssn", "ssn", "mobileNo");
        otherFields.stream().forEach(field -> {
            assertFalse(serializedValue.contains(field));
        });
    }

    @Test
    public void serializeUserWithIntenalViewTest() throws JsonProcessingException {
        // GIVEN
        final User mockedUser = getMockedUser();

        // WHEN
        final String serializedValue = objectMapper
                .writerWithView(View.UserView.Internal.class)
                .writeValueAsString(mockedUser);

        // THEN
        final List<String> expectedFields = Arrays.asList("firstName", "lastName", "id", "ssn", "ssn", "mobileNo");
        expectedFields.stream().forEach(field -> {
            assertTrue(serializedValue.contains(field));
        });
    }

    @Test
    public void deserializeUserWithExternalView() throws JsonProcessingException {
        // GIVEN
        final String serializedExternalUserJson = "{\"id\":2,\"firstName\":\"Foo\",\"lastName\":\"Bar\",\"ssn\":\"mockedSsn\",\"dob\":1579799822543,\"mobileNo\":\"34234234\"}";

        // WHEN
        final User user = objectMapper
                .readerWithView(View.UserView.External.class)
                .forType(User.class)
                .readValue(serializedExternalUserJson);

        System.out.println(user);
        assertNotNull(user.getFirstName());
        assertNotNull(user.getId());
        assertNotNull(user.getLastName());
        assertNull(user.getSsn());
        assertNull(user.getMobileNo());
        assertNull(user.getDob());
    }

    @Test
    public void deserializeUserWithInternalView() throws JsonProcessingException {
        // GIVEN
        final String serializedInternalUserJson = "{\"id\":2,\"firstName\":\"Foo\",\"lastName\":\"Bar\",\"ssn\":\"mockedSsn\",\"dob\":1579799822543,\"mobileNo\":\"34234234\"}";

        // WHEN
        final User user = objectMapper
                .readerWithView(View.UserView.Internal.class)
                .forType(User.class)
                .readValue(serializedInternalUserJson);

        System.out.println(user);
        assertNotNull(user.getFirstName());
        assertNotNull(user.getId());
        assertNotNull(user.getLastName());
        assertNotNull(user.getSsn());
        assertNotNull(user.getMobileNo());
        assertNotNull(user.getDob());
    }

    private User getMockedUser() {
        final User user = new User();
        user.setId(2);
        user.setSsn("mockedSsn");
        user.setMobileNo("34234234");
        user.setLastName("Bar");
        user.setFirstName("Foo");
        user.setDob(Calendar.getInstance());
        return user;
    }

    private static ObjectMapper buildObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();

        // Exclude properties not annotated with any view from serialization.
        return objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    }
}
