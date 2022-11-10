package com.doordash.interview.phone_number_parser.service;

import com.doordash.interview.phone_number_parser.model.PhoneAndTypeResponseVO;
import com.doordash.interview.phone_number_parser.model.RawPhoneNumRequestVO;
import org.springframework.stereotype.Service;

public interface PhoneNumberParserService {
    PhoneAndTypeResponseVO savePhoneNumbers(RawPhoneNumRequestVO requestVO);
}
