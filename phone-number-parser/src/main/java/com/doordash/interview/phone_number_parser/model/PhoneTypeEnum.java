package com.doordash.interview.phone_number_parser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PhoneTypeEnum {
    Cell("cell"),
    Home("home");
    @Getter
    private String value;
}
