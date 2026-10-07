package org.example.stepwave.web;

/**
 * То, что лежит в HttpSession после входа. Пароль сюда не кладём.
 */
public record SessionPrincipal(Long id, String username) {
}
