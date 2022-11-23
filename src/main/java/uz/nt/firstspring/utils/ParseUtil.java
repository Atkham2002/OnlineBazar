package uz.nt.firstspring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class ParseUtil {

    public static HashMap<String, Integer> parseParameters(Integer page, Integer size){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);

        return map;
    }

    public static String stringify(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing Object to JSON String :: {}", e.getMessage());
            return "Error while parsing: " + e.getMessage();
        }
    }
}
