package com.jediorganizer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Basic integration test for the Jedi Organizer application.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.data.mongodb.host=localhost",
    "spring.data.mongodb.port=27017",
    "spring.data.mongodb.database=test-jedi-organizer"
})
class JediOrganizerApplicationTests {

    /**
     * Test that the Spring Boot context loads successfully.
     */
    @Test
    void contextLoads() {
        // This test will pass if the application context loads without errors
    }
}
