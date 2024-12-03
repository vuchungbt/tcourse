package net.blwsmartware.tcourse.service;


import net.blwsmartware.tcourse.entity.AppSetting;

public interface AppSettingService {
    AppSetting save(AppSetting setting);
    AppSetting getByID(int id);
    void deleteByID(int id);
}
