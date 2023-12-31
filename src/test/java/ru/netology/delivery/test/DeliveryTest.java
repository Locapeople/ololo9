package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully plan two meetings on different days")
    void shouldSuccessfulPlanAndReplanMeeting() {
        // общие данные
        generateAndInputCommonFields("ru");
        // данные по дате события
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // дата 1
        inputDateAndBook(firstMeetingDate);
        checkForSuccessNotification(firstMeetingDate);
        // дата 2
        inputDateAndRebook(secondMeetingDate);
        checkForSuccessNotification(secondMeetingDate);
    }

    void generateAndInputCommonFields(String locale) {
        var validUser = DataGenerator.Registration.generateUser(locale);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
    }

    void inputDateAndBook(String date) {
        updateCalendarInput(date);
        $(byText("Запланировать")).click();
    }

    void inputDateAndRebook(String date) {
        inputDateAndBook(date);
        // перепланировать
        $("[data-test-id=replan-notification]").shouldBe(visible);
        $(byText("Перепланировать")).click();
    }

    void updateCalendarInput(String date) {
        // очистить значение даты
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        // записать в поле новую дату
        $("[data-test-id=date] input").setValue(date);
    }

    void checkForSuccessNotification(String date) {
        $("[data-test-id=success-notification]")
                .shouldBe(visible)
                .shouldHave(exactText("Успешно!\nВстреча успешно запланирована на " + date));
        $("[data-test-id=success-notification] > button").click();
    }
}
