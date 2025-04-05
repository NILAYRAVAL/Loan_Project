package com.tus.loans.exception;

import com.tus.loans.dto.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleLoanAlreadyExistsException_shouldReturnBadRequest() {
        LoanAlreadyExistsException ex = new LoanAlreadyExistsException("Loan exists");
        ServletWebRequest request = new ServletWebRequest(new MockHttpServletRequest());

        var response = handler.handleLoanAlreadyExistsException(ex, request);
        ErrorResponseDto body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body.getErrorMessage()).isEqualTo("Loan exists");
    }

    @Test
    void handleResourceNotFoundException_shouldReturnNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Loan", "ID", "123");
        ServletWebRequest request = new ServletWebRequest(new MockHttpServletRequest());

        var response = handler.handleResourceNotFoundException(ex, request);
        ErrorResponseDto body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(body).isNotNull();
        assertThat(body.getErrorMessage()).contains("Loan not found");
    }

    @Test
    void handleGlobalException_shouldReturnServerError() {
        Exception ex = new Exception("Something went wrong");
        ServletWebRequest request = new ServletWebRequest(new MockHttpServletRequest());

        var response = handler.handleGlobalException(ex, request);
        ErrorResponseDto body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(body.getErrorMessage()).contains("Something went wrong");
    }
}
