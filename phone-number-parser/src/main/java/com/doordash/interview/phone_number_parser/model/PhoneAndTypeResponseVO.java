package com.doordash.interview.phone_number_parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAndTypeResponseVO {
    private List<PhoneAndType> results;
}
