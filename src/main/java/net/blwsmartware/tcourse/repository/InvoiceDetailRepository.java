package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
}
