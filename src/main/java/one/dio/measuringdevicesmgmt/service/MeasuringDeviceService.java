package one.dio.measuringdevicesmgmt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.entity.MeasuringDevice;
import one.dio.measuringdevicesmgmt.mapper.MeasuringDeviceMapper;
import one.dio.measuringdevicesmgmt.repository.MeasuringDeviceRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeasuringDeviceService {
    
    private final MeasuringDeviceRepository measuringDeviceRepository;

    private final MeasuringDeviceMapper measuringDeviceMapper = MeasuringDeviceMapper.INSTANCE;

    public MeasuringDeviceDTO createMeasuringDevice(MeasuringDeviceDTO measuringDeviceDTO) {
        MeasuringDevice measuringDevice = measuringDeviceMapper.toModel(measuringDeviceDTO);
        MeasuringDevice savedMeasuringDevice = measuringDeviceRepository.save(measuringDevice);
        return measuringDeviceMapper.toDTO(savedMeasuringDevice);
    }

    public List<MeasuringDeviceDTO> listAll() {
        return measuringDeviceRepository.findAll()
                                        .stream()
                                        .map(measuringDeviceMapper::toDTO)
                                        .collect(Collectors.toList());
    }

}
