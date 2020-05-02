package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String fileString;
        StringBuffer result = new StringBuffer("");
        Matcher matcher;

        Pattern pattern = Pattern.compile("(\\d{4}) (\\d{4}) (\\d{4}) (\\d{4})");
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"))) {
            fileString = bufReader.readLine();
            matcher = pattern.matcher(fileString);
            while (matcher.find()) {
                matcher.appendReplacement(result, "$1 **** **** $4");
            }
            matcher.appendTail(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String fileString;
        StringBuffer result = new StringBuffer("");
        Matcher matcher;

        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"))) {
            fileString = bufReader.readLine();
            matcher = pattern.matcher(fileString);
            if (matcher.find()) {
                matcher.appendReplacement(result, String.format("%.0f", paymentAmount));
            }
            if (matcher.find()) {
                matcher.appendReplacement(result, String.format("%.0f", balance));

            }
            matcher.appendTail(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
