package com.bilyoner.bettingapp.mappers;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.BetSlipEntity;
import com.bilyoner.bettingapp.entities.EventEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-06T22:47:50+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class BetSlipMapperImpl implements BetSlipMapper {

    @Override
    public BetSlipEntity toEntity(BetSlipRequestDto dto, String customerId) {
        if ( dto == null && customerId == null ) {
            return null;
        }

        BetSlipEntity.BetSlipEntityBuilder betSlipEntity = BetSlipEntity.builder();

        if ( dto != null ) {
            betSlipEntity.event( betSlipRequestDtoToEventEntity( dto ) );
            betSlipEntity.betType( dto.getBetType() );
            betSlipEntity.betRate( dto.getBetRate() );
            betSlipEntity.betAmount( dto.getBetAmount() );
            betSlipEntity.quantity( dto.getQuantity() );
            betSlipEntity.totalAmount( dto.getTotalAmount() );
        }
        betSlipEntity.customerId( customerId );

        return betSlipEntity.build();
    }

    protected EventEntity betSlipRequestDtoToEventEntity(BetSlipRequestDto betSlipRequestDto) {
        if ( betSlipRequestDto == null ) {
            return null;
        }

        EventEntity.EventEntityBuilder eventEntity = EventEntity.builder();

        eventEntity.id( betSlipRequestDto.getEventId() );

        return eventEntity.build();
    }
}
