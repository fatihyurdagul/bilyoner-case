package com.bilyoner.bettingapp.mappers;

import com.bilyoner.bettingapp.dto.request.EventRequestDto;
import com.bilyoner.bettingapp.dto.response.EventResponseDto;
import com.bilyoner.bettingapp.entities.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventEntity toEntity(EventRequestDto dto);

    EventResponseDto toResponseDto(EventEntity entity);
}
