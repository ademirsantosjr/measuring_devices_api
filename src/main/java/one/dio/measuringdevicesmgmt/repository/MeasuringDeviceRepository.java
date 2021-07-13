package one.dio.measuringdevicesmgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.measuringdevicesmgmt.entity.MeasuringDevice;

@Repository
public interface MeasuringDeviceRepository extends JpaRepository<MeasuringDevice, Long>{

    Optional<MeasuringDevice> findByInternalCode(String internalCode);
    void deleteByInternalCode(String internalCode);
}
