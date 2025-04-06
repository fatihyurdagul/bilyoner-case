package com.bilyoner.bettingapp.controllers;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.enums.BetType;
import com.bilyoner.bettingapp.services.BetSlipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BetSlipController.class)
@AutoConfigureMockMvc
class BetSlipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BetSlipService betSlipService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateBetSlip() throws Exception {
        // given
        BetSlipRequestDto request = new BetSlipRequestDto();
        request.setEventId(1L);
        request.setBetRate(new BigDecimal("1.75"));
        request.setBetAmount(new BigDecimal("100"));
        request.setQuantity(1);
        request.setBetType(BetType.AWAY_WIN);

        // when
        doNothing().when(betSlipService).createBetSlip(any(), eq("fatih"));

        // then
        mockMvc.perform(post("/api/v1/bet-slips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Customer-Id", "fatih")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
