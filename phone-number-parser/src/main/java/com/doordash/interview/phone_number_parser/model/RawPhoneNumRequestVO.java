package com.doordash.interview.phone_number_parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RawPhoneNumRequestVO {
    @JsonProperty("raw_phone_numbers")
    private String rawPhoneNumbers;
}
