package com.tus.loans.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tus.loans.dto.LoansDto;
import com.tus.loans.service.ILoansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoansController.class)
class LoansControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ILoansService loansService;

    private LoansDto loansDto;

    @BeforeEach
    void setUp() {
        loansDto = new LoansDto();
        loansDto.setMobileNumber("1234567890");
        loansDto.setLoanNumber("123456789012");
        loansDto.setLoanType("Home");
        loansDto.setTotalLoan(500000);
        loansDto.setAmountPaid(100000);
        loansDto.setOutstandingAmount(400000);
    }

    @Test
    void testCreateLoan() throws Exception {
        mockMvc.perform(post("/api/loans")
                        .param("mobileNumber", "1234567890"))
                .andExpect(status().isCreated());
    }

    @Test
    void testFetchLoan() throws Exception {
        Mockito.when(loansService.fetchLoan(anyString())).thenReturn(loansDto);

        mockMvc.perform(get("/api/loans")
                        .param("mobileNumber", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mobileNumber").value("1234567890"));
    }

    @Test
    void testUpdateLoan() throws Exception {
        Mockito.when(loansService.updateLoan(Mockito.any())).thenReturn(true);

        mockMvc.perform(put("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loansDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteLoan() throws Exception {
        Mockito.when(loansService.deleteLoan(anyString())).thenReturn(true);

        mockMvc.perform(delete("/api/loans")
                        .param("mobileNumber", "1234567890"))
                .andExpect(status().isOk());
    }
}
