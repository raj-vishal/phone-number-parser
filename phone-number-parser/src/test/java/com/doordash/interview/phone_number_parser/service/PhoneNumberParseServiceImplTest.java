package com.doordash.interview.phone_number_parser.service;

import com.doordash.interview.phone_number_parser.model.PhoneAndType;
import com.doordash.interview.phone_number_parser.model.PhoneAndTypeResponseVO;
import com.doordash.interview.phone_number_parser.model.PhoneTypeEnum;
import com.doordash.interview.phone_number_parser.model.RawPhoneNumRequestVO;
import com.doordash.interview.phone_number_parser.repository.PhoneNumberParserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PhoneNumberParseServiceImplTest.class})
public class PhoneNumberParseServiceImplTest {

    @Mock
    PhoneNumberParserRepo repo;

    @InjectMocks
    PhoneNumberParserServiceImpl service;

    @Test
    public void test_savePhoneNumbersWhenCellTypeExists() {
        RawPhoneNumRequestVO rawPhoneNumRequestVO = new RawPhoneNumRequestVO();
        rawPhoneNumRequestVO.setRawPhoneNumbers("(Home) 415-415-4153 (Cell) 415-123-4561");
        when(repo.findByPhoneNumberAndPhoneType("4151234561", PhoneTypeEnum.Cell)).thenReturn(new PhoneAndType(1L, "4151234561", PhoneTypeEnum.Cell, 1));
        when(repo.save(any(PhoneAndType.class))).thenReturn(new PhoneAndType(1L, "4151234561", PhoneTypeEnum.Cell, 2));
        PhoneAndTypeResponseVO responseVO = service.savePhoneNumbers(rawPhoneNumRequestVO);
        assertEquals(2, responseVO.getResults().get(1).getOccurrences());
        assertEquals(PhoneTypeEnum.Cell, responseVO.getResults().get(1).getPhoneType());
    }

    @Test
    public void test_savePhoneNumbersWhenHomeTypeExists() {
        RawPhoneNumRequestVO rawPhoneNumRequestVO = new RawPhoneNumRequestVO();
        rawPhoneNumRequestVO.setRawPhoneNumbers("(Home) 415-415-4153 (Cell) 415-123-4561");
        when(repo.findByPhoneNumberAndPhoneType("4154154153", PhoneTypeEnum.Home)).thenReturn(new PhoneAndType(2L, "4154154153", PhoneTypeEnum.Home, 1));
        when(repo.save(any(PhoneAndType.class))).thenReturn(new PhoneAndType(2L, "4154154153", PhoneTypeEnum.Home, 2));
        PhoneAndTypeResponseVO responseVO = service.savePhoneNumbers(rawPhoneNumRequestVO);
        assertEquals(2, responseVO.getResults().get(0).getOccurrences());
        assertEquals(PhoneTypeEnum.Home, responseVO.getResults().get(0).getPhoneType());
    }
}
