package com.doordash.interview.phone_number_parser.controller;

import com.doordash.interview.phone_number_parser.model.RawPhoneNumRequestVO;
import com.doordash.interview.phone_number_parser.service.PhoneNumberParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PhoneNumberParserController.class)
public class PhoneNumberParserControllerTest {

    @InjectMocks
    PhoneNumberParserController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    PhoneNumberParserService service;

    @Test
    public void test_savePhoneNumbers() throws Exception {

        String rawJsonString = "(Home) 415-415-4153 (Cell) 415-123-4561";
        RawPhoneNumRequestVO requestVO = new RawPhoneNumRequestVO();
        requestVO.setRawPhoneNumbers(rawJsonString);
        mvc.perform(post("/phone-number-parsing/phone-numbers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                //.content(rawJsonString))
                .content(new ObjectMapper().writeValueAsString(requestVO)))
                .andExpect(status().isOk());
        verify(service).savePhoneNumbers(ArgumentMatchers.eq(requestVO));
    }


}
