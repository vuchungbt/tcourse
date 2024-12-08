package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.repository.CardRepository;
import net.blwsmartware.tcourse.service.CardService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CardServiceImpl implements CardService {
    CardRepository cardRepository;

    @Override
    public void delete(long id) {
        cardRepository.deleteById(id);
    }
}
