package uz.nt.firstspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import uz.nt.firstspring.repository.HistoryRepository;
import uz.nt.firstspring.service.mapper.HistoryMapper;
import uz.nt.firstspring.dto.HistoryDto;
import uz.nt.firstspring.dto.UserInfoDto;
import uz.nt.firstspring.entity.History;
import uz.nt.firstspring.service.HistoryService;
import uz.nt.firstspring.utils.ParseUtil;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    @Override
    public Long save(HistoryDto dto) {
        History entity = historyMapper.toEntity(dto);
        historyRepository.save(entity);

        return entity.getId();
    }

    @Override
    public Long createHistory(Object obj, String url, HttpMethod method) {
        HistoryDto dto = new HistoryDto();
        dto.setDate(new Date());
        dto.setOriginalData(ParseUtil.stringify(obj));
        dto.setUrl(url);
        dto.setRequestMethod(method.name());

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof UserInfoDto){
            UserInfoDto user = (UserInfoDto) o;
            dto.setUserId(user.getId());

            WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            dto.setRemoteAddress(details.getRemoteAddress());
        }

        return save(dto);
    }

    @Override
    public void updateHistory(Long historyId, Object response) {
        historyRepository.findById(historyId).ifPresent(h -> {
            h.setResponseData(ParseUtil.stringify(response));
            historyRepository.save(h);
        });
    }

    @Override
    public void errorHistory(Long historyId, Object response, String message) {
        historyRepository.findById(historyId).ifPresent(h -> {
            h.setResponseData(ParseUtil.stringify(response));
            h.setError(message);
            historyRepository.save(h);
        });
    }


}
