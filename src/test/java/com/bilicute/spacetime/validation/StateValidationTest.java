package com.bilicute.spacetime.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class StateValidationTest {


    @Mock
    private ConstraintValidatorContext context;

    private StateValidation stateValidator;


    @BeforeEach
    public void setUp() {
        stateValidator = new StateValidation();
    }

    @Test
    public void testIsValidWithDraft() {
        assertTrue(stateValidator.isValid("草稿", context));
    }

    @Test
    public void testIsValidWithPublished() {
        assertTrue(stateValidator.isValid("已发布", context));
    }

    @Test
    public void testIsValidWithInvalidState() {
        assertFalse(stateValidator.isValid("无效状态", context));
    }

    @Test
    public void testIsValidWithNull() {
        assertFalse(stateValidator.isValid(null, context));
    }

}
