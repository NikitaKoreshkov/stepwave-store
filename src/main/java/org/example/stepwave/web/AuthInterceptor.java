package org.example.stepwave.web;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Пускает дальше только запросы с авторизованной сессией.
 * Для /api/* отвечает 401, страницы уводит на вход.
 */
public class AuthInterceptor implements HandlerInterceptor {

    public static final String SESSION_USER = "currentUser";

    private final boolean apiPaths;

    public AuthInterceptor(boolean apiPaths) {
        this.apiPaths = apiPaths;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER) != null) {
            return true;
        }
        if (apiPaths) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Требуется вход в аккаунт\"}");
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
        return false;
    }
}
