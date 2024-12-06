package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppSettingRepository extends JpaRepository<AppSetting, Integer> {
    Optional<AppSetting> findByCode(String Code);
}
