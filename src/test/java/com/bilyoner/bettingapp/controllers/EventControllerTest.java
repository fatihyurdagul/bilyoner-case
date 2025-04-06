package com.bilyoner.bettingapp.controllers;

import com.bilyoner.bettingapp.dto.request.EventRequestDto;
import com.bilyoner.bettingapp.dto.response.ExceptionResponseDto;
import com.bilyoner.bettingapp.eventEmitters.ServerSentEventManager;
import com.bilyoner.bettingapp.exceptions.ApiExceptionType;
import com.bilyoner.bettingapp.services.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private ServerSentEventManager emitterManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateEventWhenCustomerIdCorrect() throws Exception {
        // given
        EventRequestDto dto = new EventRequestDto();
        dto.setHomeTeam("Galatasaray");
        dto.setAwayTeam("Fenerbahçe");
        dto.setLeague("Süper Lig");
        dto.setHomeWinRate(new BigDecimal("2.10"));
        dto.setDrawRate(new BigDecimal("3.30"));
        dto.setAwayWinRate(new BigDecimal("2.90"));
        dto.setStartTime(LocalDateTime.now().plusDays(1));

        // when
        doNothing().when(eventService).createEvent(any());

        // then
        mockMvc.perform(post("/api/v1/events")
                        .header("X-Customer-Id", "fatih")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateEventWhenCustomerIdNotCorrect() throws Exception {
        // given
        EventRequestDto dto = new EventRequestDto();
        dto.setHomeTeam("Galatasaray");
        dto.setAwayTeam("Fenerbahçe");
        dto.setLeague("Süper Lig");
        dto.setHomeWinRate(new BigDecimal("2.10"));
        dto.setDrawRate(new BigDecimal("3.30"));
        dto.setAwayWinRate(new BigDecimal("2.90"));
        dto.setStartTime(LocalDateTime.now().plusDays(1));

        // when
        doNothing().when(eventService).createEvent(any());

        // then
        MvcResult response = mockMvc.perform(post("/api/v1/events")
                        .header("X-Customer-Id", "wrong-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized()).andReturn();

        ApiExceptionType exceptionType = ApiExceptionType.UNAUTHORIZED_CUSTOMER;
        ExceptionResponseDto expectedResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ExceptionResponseDto.class);
        assertEquals(exceptionType.getCode(), expectedResponse.getCode());
        assertEquals(exceptionType, expectedResponse.getType());
    }

    @Test
    void shouldExceptionWhenFieldsAreMissing() throws Exception {
        // given
        EventRequestDto dto = new EventRequestDto();
        dto.setHomeTeam("Galatasaray");
        dto.setLeague("Süper Lig");
        dto.setHomeWinRate(new BigDecimal("2.10"));
        dto.setAwayWinRate(new BigDecimal("2.90"));
        dto.setStartTime(LocalDateTime.now().plusDays(1));

        // when
        doNothing().when(eventService).createEvent(any());

        // then
        ApiExceptionType exceptionType = ApiExceptionType.FIELD_VALIDATION_ERROR;
        MvcResult response = mockMvc.perform(post("/api/v1/events")
                        .header("X-Customer-Id", "fatih")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError()).andReturn();


        ExceptionResponseDto expectedResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ExceptionResponseDto.class);
        assertEquals(exceptionType.getCode(), expectedResponse.getCode());
        assertEquals(exceptionType, expectedResponse.getType());
    }
}
