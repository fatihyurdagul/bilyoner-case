package com.bilyoner.bettingapp.mappers;

import com.bilyoner.bettingapp.dto.request.EventRequestDto;
import com.bilyoner.bettingapp.dto.response.EventResponseDto;
import com.bilyoner.bettingapp.entities.EventEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-06T22:47:50+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventEntity toEntity(EventRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        EventEntity.EventEntityBuilder eventEntity = EventEntity.builder();

        eventEntity.league( dto.getLeague() );
        eventEntity.homeTeam( dto.getHomeTeam() );
        eventEntity.awayTeam( dto.getAwayTeam() );
        eventEntity.homeWinRate( dto.getHomeWinRate() );
        eventEntity.drawRate( dto.getDrawRate() );
        eventEntity.awayWinRate( dto.getAwayWinRate() );
        eventEntity.startTime( dto.getStartTime() );

        return eventEntity.build();
    }

    @Override
    public EventResponseDto toResponseDto(EventEntity entity) {
        if ( entity == null ) {
            return null;
        }

        EventResponseDto eventResponseDto = new EventResponseDto();

        eventResponseDto.setId( entity.getId() );
        eventResponseDto.setLeague( entity.getLeague() );
        eventResponseDto.setHomeTeam( entity.getHomeTeam() );
        eventResponseDto.setAwayTeam( entity.getAwayTeam() );
        eventResponseDto.setHomeWinRate( entity.getHomeWinRate() );
        eventResponseDto.setDrawRate( entity.getDrawRate() );
        eventResponseDto.setAwayWinRate( entity.getAwayWinRate() );
        eventResponseDto.setStartTime( entity.getStartTime() );

        return eventResponseDto;
    }
}
