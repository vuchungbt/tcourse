package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByCreatedId(Long createdId);
}
