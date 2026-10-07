package org.example.stepwave.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message); // Отправляем письмо
            System.out.println("HTML письмо отправлено успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для формирования ссылки сброса пароля
    public String generatePasswordResetLink(String token) {
        return baseUrl + "/api/email/reset-password-form?token=" + token;
    }

    // Метод для отправки письма с кодом подтверждения (HTML)
    public void sendConfirmationCodeEmail(String to, String confirmationCode) {
        String subject = "Код подтверждения для StepWave";

        // Генерация ссылки для подтверждения с кодом
        String confirmationUrl = baseUrl + "/api/email/verify-code?code=" + confirmationCode;

        // HTML-содержание письма
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"ru\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Код подтверждения</title>" +
                "<style>" +
                "    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
                "    .container { max-width: 600px; margin: 50px auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; }" +
                "    h1 { color: #007bff; font-size: 28px; }" +
                "    h2 { color: #007bff; font-size: 36px; margin: 20px 0; }" +
                "    p { font-size: 16px; color: #555; line-height: 1.6; }" +
                "    a { color: #007bff; text-decoration: none; font-weight: bold; }" +
                "    a:hover { text-decoration: underline; }" +
                "    .footer { margin-top: 30px; font-size: 14px; color: #777; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<h1>Код подтверждения для StepWave</h1>" +
                "<p>Ваш код подтверждения:</p>" +
                "<h2>" + confirmationCode + "</h2>" +
                "<p>Этот код действителен в течение 10 минут. Если вы не запрашивали подтверждение, просто проигнорируйте это письмо.</p>" +
                "<p>Для продолжения, перейдите по следующей <a href=\"" + confirmationUrl + "\">ссылке</a> и введите код, который мы отправили вам.</p>" +
                "<div class=\"footer\">" +
                "<p>С уважением,<br>Команда StepWave</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        // Отправка письма
        sendHtmlEmail(to, subject, htmlContent);
    }

    // Новый метод для отправки письма с токеном сброса пароля (HTML)
    public void sendPasswordResetToken(String to, String token) {
        String subject = "Сброс пароля";

        // Формируем ссылку для сброса пароля
        String resetUrl = generatePasswordResetLink(token);

        // HTML-содержание письма с инлайновыми стилями
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"ru\">" +
                "<head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Сброс пароля</title></head>" +
                "<body>" +
                "<div style=\"text-align: center; font-family: Arial, sans-serif; color: #333;\">" +
                "<h1 style=\"color: #007bff;\">Сброс пароля для вашего аккаунта в StepWave</h1>" +
                "<p>Если это не вы, проигнорируйте это письмо.</p>" +
                "<p>Вы запросили сброс пароля для вашего аккаунта в компании StepWave. Чтобы продолжить, перейдите по следующей ссылке:</p>" +
                "<a href=\"" + resetUrl + "\" style=\"display: inline-block; padding: 12px 25px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;\">Сбросить пароль</a>" +
                "<p>Ссылка будет действительна в течение 24 часов.</p>" +
                "<p>С уважением,<br>Команда StepWave</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        // Отправка HTML-письма
        sendHtmlEmail(to, subject, htmlContent);
    }
}