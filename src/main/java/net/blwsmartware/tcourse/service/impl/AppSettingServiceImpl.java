package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.entity.AppSetting;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.repository.AppSettingRepository;
import net.blwsmartware.tcourse.service.AppSettingService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppSettingServiceImpl implements AppSettingService {
    AppSettingRepository appSettingRepository;
    @Override
    public AppSetting save(AppSetting setting) {
        return appSettingRepository.save(setting);
    }

    @Override
    public AppSetting getByID(int id) {
        return appSettingRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND) );
    }

    @Override
    public void deleteByID(int id) {
        appSettingRepository.deleteById(id);
    }
}
