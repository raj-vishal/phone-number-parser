package com.doordash.interview.phone_number_parser.controller;

import com.doordash.interview.phone_number_parser.model.PhoneAndTypeResponseVO;
import com.doordash.interview.phone_number_parser.model.RawPhoneNumRequestVO;
import com.doordash.interview.phone_number_parser.service.PhoneNumberParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone-number-parsing")
@Slf4j
public class PhoneNumberParserController {

    @Autowired
    private PhoneNumberParserService phoneNumberParserService;

    @PostMapping("/phone-numbers")
    public PhoneAndTypeResponseVO savePhoneNumbers(@RequestBody RawPhoneNumRequestVO requestVO) {
        PhoneAndTypeResponseVO responseVO = phoneNumberParserService.savePhoneNumbers(requestVO);
        return responseVO;

    }
}
