package org.example.stepwave.service;

import java.security.SecureRandom;

/**
 * Одноразовые коды подтверждения. Math.random() здесь не подходит:
 * он предсказуем, а код защищает смену пароля.
 */
public final class CodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private CodeGenerator() {
    }

    public static String sixDigits() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }
}
