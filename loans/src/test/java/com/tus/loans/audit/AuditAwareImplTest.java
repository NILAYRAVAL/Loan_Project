package com.tus.loans.audit;

//import com.tus.loans.AuditAwareImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AuditAwareImplTest {

    @Test
    void getCurrentAuditor_shouldReturnAdmin() {
        AuditAwareImpl auditor = new AuditAwareImpl();
        Optional<String> result = auditor.getCurrentAuditor();
        assertThat(result).isPresent().contains("admin");
    }
}
