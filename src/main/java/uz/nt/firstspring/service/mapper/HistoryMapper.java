package uz.nt.firstspring.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.firstspring.dto.HistoryDto;
import uz.nt.firstspring.entity.History;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    History toEntity(HistoryDto dto);
    HistoryDto toDto(History entity);
}
