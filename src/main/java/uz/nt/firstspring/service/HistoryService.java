package uz.nt.firstspring.service;

import org.springframework.http.HttpMethod;
import uz.nt.firstspring.dto.HistoryDto;

public interface HistoryService {

    Long save(HistoryDto dto);
    Long createHistory(Object obj, String url, HttpMethod method);

    void updateHistory(Long historyId, Object response);

    void errorHistory(Long historyId, Object response, String message);
}
