package com.bilicute.spacetime.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @所属包名: com.bilicute.spacetime.validation
 * @类名: IdentityValidationTest
 * @作者: i囡漫笔
 * @描述:
 * @创建时间: 2024-08-01 02:52
 */


@ExtendWith(MockitoExtension.class)
public class IdentityValidationTest {

    @Mock
    private ConstraintValidatorContext context;

    private IdentityValidation identityValidator;

    @BeforeEach
    public void setUp() {
        identityValidator = new IdentityValidation();
    }

    @Test
    public void testIsValidWithAdmin() {
        assertTrue(identityValidator.isValid("Admin", context));
    }

    @Test
    public void testIsValidWithUser() {
        assertTrue(identityValidator.isValid("User", context));
    }

    @Test
    public void testIsValidWithRemoved() {
        assertTrue(identityValidator.isValid("Removed", context));
    }

    @Test
    public void testIsValidWithInvalidIdentity() {
        assertFalse(identityValidator.isValid("无效身份", context));
    }

    @Test
    public void testIsValidWithNull() {
        assertFalse(identityValidator.isValid(null, context));
    }
}
