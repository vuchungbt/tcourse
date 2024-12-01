package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.entity.Discount;

public interface DiscountService {
    Discount create(Discount request);
    Discount getByID(long id);
    void delete(long id);
   // DataResponse<Tag> getAll(Integer pageNumber, Integer pageSize, String sortBy);
}
