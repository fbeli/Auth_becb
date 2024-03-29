package com.becb.localauthserver.core;

import java.time.LocalDateTime;

public class SupportService {

    public boolean isValidInDate(LocalDateTime requestTime){

        LocalDateTime check = LocalDateTime.now().minusMinutes(31);

        if(requestTime.isAfter(check)){
            return true;

        } return false;

    }
}
