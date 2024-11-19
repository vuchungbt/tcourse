package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppSettingRepository extends JpaRepository<AppSetting, Integer> {
}
