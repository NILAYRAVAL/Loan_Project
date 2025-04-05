package com.tus.loans.service.impl;

import com.tus.loans.dto.LoansDto;
import com.tus.loans.entity.Loans;
import com.tus.loans.repository.LoansRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoansServiceImplTest {

    @Mock
    private LoansRepository loansRepository;

    @InjectMocks
    private LoansServiceImpl loansService;

    private Loans loanEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loanEntity = new Loans();
        loanEntity.setLoanId(1L);
        loanEntity.setLoanNumber("123456789012");
        loanEntity.setMobileNumber("1234567890");
        loanEntity.setLoanType("Home");
        loanEntity.setTotalLoan(500000);
        loanEntity.setAmountPaid(100000);
        loanEntity.setOutstandingAmount(400000);
    }

    @Test
    void testCreateLoan_whenNotExists() {
        when(loansRepository.findByMobileNumber("1234567890")).thenReturn(Optional.empty());
        when(loansRepository.save(any())).thenReturn(loanEntity);

        assertDoesNotThrow(() -> loansService.createLoan("1234567890"));
    }

    @Test
    void testFetchLoan_success() {
        when(loansRepository.findByMobileNumber("1234567890")).thenReturn(Optional.of(loanEntity));

        LoansDto dto = loansService.fetchLoan("1234567890");

        assertEquals("1234567890", dto.getMobileNumber());
        assertEquals("123456789012", dto.getLoanNumber());
    }

    @Test
    void testUpdateLoan_success() {
        LoansDto dto = new LoansDto("1234567890", "123456789012", "Home", 600000, 200000, 400000);

        when(loansRepository.findByLoanNumber(dto.getLoanNumber())).thenReturn(Optional.of(loanEntity));
        when(loansRepository.save(any())).thenReturn(loanEntity);

        assertTrue(loansService.updateLoan(dto));
    }

    @Test
    void testDeleteLoan_success() {
        when(loansRepository.findByMobileNumber("1234567890")).thenReturn(Optional.of(loanEntity));
        doNothing().when(loansRepository).deleteById(1L);

        assertTrue(loansService.deleteLoan("1234567890"));
    }
}
