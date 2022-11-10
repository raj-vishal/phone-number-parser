package com.doordash.interview.phone_number_parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class PhoneAndType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("phone_type")
    private String phoneType;
    private int occurrences;

}
