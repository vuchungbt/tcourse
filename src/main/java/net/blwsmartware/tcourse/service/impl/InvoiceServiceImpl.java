package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.post.InvoiceRequest;
import net.blwsmartware.tcourse.entity.Invoice;
import net.blwsmartware.tcourse.entity.InvoiceDetail;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.repository.InvoiceDetailRepository;
import net.blwsmartware.tcourse.repository.InvoiceRepository;
import net.blwsmartware.tcourse.repository.PostRepository;
import net.blwsmartware.tcourse.repository.UserRepository;
import net.blwsmartware.tcourse.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    InvoiceRepository invoiceRepository;
    InvoiceDetailRepository invoiceDetailRepository;
    UserRepository userRepository;
    PostRepository postRepository;

    @Override
    public Invoice addInvoice(InvoiceRequest invoice) {

        User created = userRepository.findByUsername(invoice.getCreateBy())
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        Invoice ivs = Invoice.builder()
                .total(invoice.getTotal())
                .created(created)
                .build();
        Invoice ivs1 = invoiceRepository.save(ivs);
        List<InvoiceDetail> details = invoice.getPosts().stream()
                .map(invoiceDetailRequest -> InvoiceDetail.builder()
                        .invoice(ivs1)
                        .item(postRepository.findById(invoiceDetailRequest.getId()).orElseThrow())
                        .price(invoiceDetailRequest.getPrice())
                        .build())
                .toList();
        List<InvoiceDetail> detail1 = invoiceDetailRepository.saveAll(details);
        ivs1.setDetailList(detail1);
        return invoiceRepository.save(ivs1);
    }

    @Override
    public Invoice getByID(long id) {
        return  invoiceRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND));
    }

    @Override
    public List<Invoice> getByCreatedID(long id) {
        return invoiceRepository.findByCreatedId(id);
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public void delete(long id) {
        invoiceRepository.deleteById(id);
    }
}
