package org.example.stepwave.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;

@Controller
public class ChatController {

    // Обработка запроса чата
    @PostMapping("/chat")
    @ResponseBody
    public ResponseEntity<?> chat(@RequestBody ChatRequest chatRequest) {
        String question = chatRequest.getQuestion();
        String answer = getAnswer(question); // Получаем ответ от бота на основе вопроса

        return ResponseEntity.ok(new ChatResponse(answer));
    }

    // Метод для получения ответа бота на вопрос
    private String getAnswer(String question) {
        question = question.toLowerCase(); // Приводим вопрос к нижнему регистру

        // Приветствия
        if (question.contains("привет") || question.contains("Привет")) {
            return "Добро пожаловать в StepWave! Чем мы можем вам помочь?";
        } else if (question.contains("здравствуйте") || question.contains("Здравствуйте")) {
            return "Здравствуйте! Мы рады вас приветствовать в StepWave. Чем можем быть полезны?";

            // Вопросы о магазине StepWave
        } else if (question.contains("добро пожаловать")) {
            return "Добро пожаловать в StepWave – ваш надежный партнер в мире кроссовок!";
        } else if (question.contains("что такое stepwave")) {
            return "StepWave – это онлайн-магазин, где стиль встречается с комфортом. Мы специализируемся на продаже качественной и оригинальной обуви от ведущих мировых брендов.";
        } else if (question.contains("почему выбрать stepwave")) {
            return "Почему выбирают StepWave?\n- Широкий ассортимент\n- Гарантия качества\n- Доступные цены\n- Отличный сервис";
        } else if (question.contains("где я могу купить обувь")) {
            return "Вы можете купить обувь в нашем онлайн-магазине StepWave! Мы предлагаем кроссовки на любой вкус.";

            // Вопросы о доставке и возврате
        } else if (question.contains("доставка")) {
            return "Мы предлагаем удобные условия доставки:\n- Курьерская доставка по Алматы и другим регионам Казахстана\n- Сроки доставки: 1-3 рабочих дня по городу и 3-7 по регионам";
        } else if (question.contains("возврат")) {
            return "Срок возврата товара: в течение 14 дней с момента получения заказа. Для этого свяжитесь с нами по телефону +7 (771) 455-00-44 или по email info@stepwave.kz.";
        }

        // Если не найден ответ
        return "Извините, я не понимаю ваш запрос.";
    }

    // Классы для запроса и ответа
    public static class ChatRequest {
        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }

    public static class ChatResponse {
        private String answer;

        public ChatResponse(String answer) {
            this.answer = answer;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}