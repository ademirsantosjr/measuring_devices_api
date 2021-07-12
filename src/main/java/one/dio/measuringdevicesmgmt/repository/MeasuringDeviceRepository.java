package one.dio.measuringdevicesmgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import one.dio.measuringdevicesmgmt.entity.MeasuringDevice;

public interface MeasuringDeviceRepository extends JpaRepository<MeasuringDevice, Long>{

    Optional<MeasuringDevice> findByInternalCode(String internalCode);
    
}
