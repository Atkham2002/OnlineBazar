package uz.nt.firstspring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NumberUtil {

    public Long toLong(Object o) {
        try {
            String value = String.valueOf(o);
            return Long.parseLong(value);
        }catch (Exception e){
            log.error("Error while parsing object to Long:: {}", e.getMessage());
            return null;
        }
    }
}
