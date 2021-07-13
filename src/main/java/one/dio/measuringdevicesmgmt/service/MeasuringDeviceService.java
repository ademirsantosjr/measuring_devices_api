package one.dio.measuringdevicesmgmt.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.entity.MeasuringDevice;
import one.dio.measuringdevicesmgmt.exception.InternalCodeAlreadyExistsException;
import one.dio.measuringdevicesmgmt.exception.MeasuringDeviceNotFoundException;
import one.dio.measuringdevicesmgmt.mapper.MeasuringDeviceMapper;
import one.dio.measuringdevicesmgmt.repository.MeasuringDeviceRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeasuringDeviceService {
    
    private MeasuringDeviceRepository measuringDeviceRepository;

    private final MeasuringDeviceMapper measuringDeviceMapper = MeasuringDeviceMapper.INSTANCE;

    public MeasuringDeviceDTO createMeasuringDevice(MeasuringDeviceDTO measuringDeviceDTO) throws InternalCodeAlreadyExistsException {
        verifyIfInternalCodeAlreadyExists(measuringDeviceDTO.getInternalCode());
        MeasuringDevice measuringDevice = measuringDeviceMapper.toModel(measuringDeviceDTO);
        MeasuringDevice savedMeasuringDevice = measuringDeviceRepository.save(measuringDevice);
        return measuringDeviceMapper.toDTO(savedMeasuringDevice);
    }

    public MeasuringDeviceDTO findByInternalCode(String internalCode) throws MeasuringDeviceNotFoundException{
        MeasuringDevice foundMeasuringDevice = measuringDeviceRepository.findByInternalCode(internalCode).orElseThrow(() -> new MeasuringDeviceNotFoundException(internalCode));
        return measuringDeviceMapper.toDTO(foundMeasuringDevice);
    }

    public List<MeasuringDeviceDTO> listAll() {
        return measuringDeviceRepository.findAll()
                                        .stream()
                                        .map(measuringDeviceMapper::toDTO)
                                        .collect(Collectors.toList());
    }

    public MeasuringDeviceDTO updateByInternalCode(String internalCode, MeasuringDeviceDTO measuringDeviceDTO) throws MeasuringDeviceNotFoundException, InternalCodeAlreadyExistsException {
        verifyIfExists(internalCode);
        MeasuringDevice measuringDeviceToUpdate = measuringDeviceMapper.toModel(measuringDeviceDTO);
        MeasuringDevice updatedMeasuringDevice = measuringDeviceRepository.save(measuringDeviceToUpdate);
        return measuringDeviceMapper.toDTO(updatedMeasuringDevice);
    }

    @Transactional
    public void deleteByInternalCode(String internalCode) throws MeasuringDeviceNotFoundException {
        verifyIfExists(internalCode);
        measuringDeviceRepository.deleteByInternalCode(internalCode);
    }

    private void verifyIfInternalCodeAlreadyExists(String internalCode) throws InternalCodeAlreadyExistsException {
        Optional<MeasuringDevice> optSavedMeasuringDevice = measuringDeviceRepository.findByInternalCode(internalCode);
        if (optSavedMeasuringDevice.isPresent()) {
            throw new InternalCodeAlreadyExistsException(internalCode);
        }
    }

    private MeasuringDevice verifyIfExists(String internalCode) throws MeasuringDeviceNotFoundException {
        return measuringDeviceRepository.findByInternalCode(internalCode)
            .orElseThrow(() -> new MeasuringDeviceNotFoundException(internalCode));
    }
}
