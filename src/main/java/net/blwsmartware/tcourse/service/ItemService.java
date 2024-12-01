package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.entity.Item;

public interface ItemService {
    Item save(Item name);
    Item getByID(long id); 
    void deleteByID(long id);
}
