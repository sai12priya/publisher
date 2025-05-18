package com.tcs.training.Entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class InvoiceNumberGenerator {
    private static final String PREFIX = "INV-";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String generate() {
        String datePart = LocalDate.now().format(DATE_FORMAT);
        String randomPart = String.format("%06d", new Random().nextInt(999999));
        return PREFIX + datePart + "-" + randomPart;
    }
}