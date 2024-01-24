package com.chubaievskyi.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class IpnValidatorTest {

    private IpnValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new IpnValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567890", "1234567891", "1234567892", "1234567893", "1234567894",
            "1234567895", "1234567896", "1234567897", "1234567898"})
    void isValidWithInvalidValues(String value) {
        assertFalse(validator.isValid(value, context));
    }

    @Test
    void isValidWithValidValue() {
        assertTrue(validator.isValid("1234567899", context));
    }
}