package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.post.InvoiceRequest;
import net.blwsmartware.tcourse.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice addInvoice(InvoiceRequest invoice);
    Invoice getByID(long id);
    List<Invoice> getByCreatedID(long id);
    List<Invoice> getAll();
    void delete(long id);
}
