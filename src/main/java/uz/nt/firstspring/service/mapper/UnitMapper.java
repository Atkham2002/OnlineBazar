package uz.nt.firstspring.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.firstspring.dto.UnitDto;
import uz.nt.firstspring.entity.Unit;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    UnitDto toDto(Unit unit);
    Unit toEntity(UnitDto unitDto);
}
