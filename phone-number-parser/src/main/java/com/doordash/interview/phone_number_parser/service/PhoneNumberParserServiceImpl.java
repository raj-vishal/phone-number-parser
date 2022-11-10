package com.doordash.interview.phone_number_parser.service;

import com.doordash.interview.phone_number_parser.model.PhoneAndType;
import com.doordash.interview.phone_number_parser.model.PhoneAndTypeResponseVO;
import com.doordash.interview.phone_number_parser.model.RawPhoneNumRequestVO;
import com.doordash.interview.phone_number_parser.repository.PhoneNumberParserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhoneNumberParserServiceImpl implements PhoneNumberParserService {

    @Autowired
    private PhoneNumberParserRepo phoneNumberParserRepo;

    /**
     * Overridden Function that first parses and then persists Phone numbers and phone Type in the Database table
     * @param rawRequestVO
     * @return
     */
    @Override
    public PhoneAndTypeResponseVO savePhoneNumbers(RawPhoneNumRequestVO rawRequestVO) {
        log.info("Inside savePhoneNumbers in Service Implementation: {}", rawRequestVO);
        Map<String, String> phoneNumMap = parseAndMapPhoneNumbers(rawRequestVO, new HashMap<String, String>());
        // Populate and persist the phone details
        PhoneAndTypeResponseVO responseVO = populatePhoneAndTypeResponse(phoneNumMap, new PhoneAndTypeResponseVO());
        persistAndSavePhoneNumbers(responseVO.getResults());
        log.info("Response savePhoneNumbers in Service Implementation: {}", responseVO);
        return responseVO;
    }

    private void persistAndSavePhoneNumbers(List<PhoneAndType> results) {
        results.stream().forEach(res -> {
            log.info("Result row from DB : [res={}]", res);
            phoneNumberParserRepo.save(res);
        });
    }

    /**
     * Function to populate the response object
     *  - If Phone details exist in DB, then populate from the DB response, and increment the 'Occurrence'
     *  - if Phone details returned empty/null, then populate from the request object
     * @param phoneNumMap
     * @param responseVO
     * @return
     */
    private PhoneAndTypeResponseVO populatePhoneAndTypeResponse(Map<String, String> phoneNumMap, PhoneAndTypeResponseVO responseVO) {
        List<PhoneAndType> results = new ArrayList<PhoneAndType>();
        phoneNumMap.forEach((phoneType, number) -> {
            PhoneAndType objectInDB = phoneNumberParserRepo.findByPhoneNumberAndPhoneType(number, phoneType);
            PhoneAndType phoneAndTypeObj = new PhoneAndType();
            if(null == objectInDB) {
                phoneAndTypeObj.setPhoneNumber(number);
                phoneAndTypeObj.setPhoneType(phoneType);
                phoneAndTypeObj.setOccurrences(1);
            } else {
                phoneAndTypeObj = objectInDB;
                phoneAndTypeObj.setOccurrences(objectInDB.getOccurrences() +1);
            }
            results.add(phoneAndTypeObj);
        });
        responseVO.setResults(results);
        return responseVO;
    }

    /**
     * Function to parse the input request with plain json string having phone numbers and phone types in given format
     * Eg: (format: “(Home) 415-415-4155 (Cell) 415-514-5145”)
     * @param requestVO
     * @param phoneNumMap
     * @return
     */
    private Map<String, String> parseAndMapPhoneNumbers(RawPhoneNumRequestVO requestVO, HashMap<String, String> phoneNumMap) {
        String phoneNumString = requestVO.getRawPhoneNumbers();
        log.info("Indide parseAndMapPhoneNumbers : [phoneNumString : {}]", phoneNumString);
        // Avoid using Regular Expression to parse the json string into a map having phone numbers and phone type
        List<String> phoneNumList = Collections.list(new StringTokenizer(phoneNumString, "() "))
                .stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
        // Convert the phone number string with '-', into a regular number string
        phoneNumList.stream()
                .filter(str -> str.contains("-"))
                .map(str -> phoneNumMap.put(phoneNumList.get(phoneNumList.indexOf(str)-1), str.replaceAll("-", "")))
                .collect(Collectors.toList());
        return phoneNumMap;
    }
}
