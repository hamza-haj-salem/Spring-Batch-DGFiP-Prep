package springBatch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeclarationServiceTest {

    @Test
    void shouldAddTwoNumbers() {

        int result = 5 + 3;

        assertEquals(8, result);
    }

    @Test
    void shouldReturnTrue() {

        String company = "ABC GROUP";

        assertTrue(company.startsWith("ABC"));
    }
}