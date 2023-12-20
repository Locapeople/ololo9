package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusDays(shift);
        return newDate.format(f);
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone.replaceAll("[\\(\\)-]", "");
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    DataGenerator.generateCity(locale),
                    DataGenerator.generateName(locale),
                    DataGenerator.generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;

        public UserInfo(String city, String name, String phone) {
            this.city = city;
            this.name = name;
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }
}
