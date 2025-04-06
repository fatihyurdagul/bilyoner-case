package com.bilyoner.bettingapp.mappers;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.BetSlipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BetSlipMapper {

    @Mapping(target = "event.id", source = "dto.eventId")
    @Mapping(target = "customerId", source = "customerId")
    BetSlipEntity toEntity(BetSlipRequestDto dto, String customerId);
}
