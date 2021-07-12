package one.dio.measuringdevicesmgmt.service;

import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.equalTo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import one.dio.measuringdevicesmgmt.builder.MeasuringDeviceDTOBuilder;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.entity.MeasuringDevice;
import one.dio.measuringdevicesmgmt.exception.InternalCodeAlreadyExistsException;
import one.dio.measuringdevicesmgmt.mapper.MeasuringDeviceMapper;
import one.dio.measuringdevicesmgmt.repository.MeasuringDeviceRepository;

@ExtendWith(MockitoExtension.class)
public class MeasuringDeviceServiceTest {
    
    @Mock
    private MeasuringDeviceRepository measuringDeviceRepository;

    private MeasuringDeviceMapper measuringDeviceMapper = MeasuringDeviceMapper.INSTANCE;

    @InjectMocks
    private MeasuringDeviceService measuringDeviceService;

    @Test
    void whenMeasuringDeviceInformedThenItShouldBeCreated() throws InternalCodeAlreadyExistsException {
        // given
        MeasuringDeviceDTO expectedMeasuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();
        MeasuringDevice expectedSavedMeasuringDevice = measuringDeviceMapper.toModel(expectedMeasuringDeviceDTO);

        // when
        when(measuringDeviceRepository.findByInternalCode(expectedMeasuringDeviceDTO.getInternalCode())).thenReturn(Optional.empty());
        when(measuringDeviceRepository.save(expectedSavedMeasuringDevice)).thenReturn(expectedSavedMeasuringDevice);

        // then
        MeasuringDeviceDTO createdMeasuringDeviceDTO = measuringDeviceService.createMeasuringDevice(expectedMeasuringDeviceDTO);

        assertThat(createdMeasuringDeviceDTO.getInternalCode(), is(equalTo(expectedMeasuringDeviceDTO.getInternalCode())));
        assertThat(createdMeasuringDeviceDTO.getDescription(), is(equalTo(expectedMeasuringDeviceDTO.getDescription())));
        assertThat(createdMeasuringDeviceDTO.getUnitOfMeasurement(), is(equalTo(expectedMeasuringDeviceDTO.getUnitOfMeasurement())));
    }

    @Test
    void whenInformedAlreadyExistingMeasuringDeviceThenAnExceptionShouldBeThrown() {
        // given
        MeasuringDeviceDTO expectedMeasuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();
        MeasuringDevice duplicatedMeasuringDevice = measuringDeviceMapper.toModel(expectedMeasuringDeviceDTO);

        // when
        when(measuringDeviceRepository.findByInternalCode(expectedMeasuringDeviceDTO.getInternalCode())).thenReturn(Optional.of(duplicatedMeasuringDevice));
        
        // then
        assertThrows(InternalCodeAlreadyExistsException.class,
            () -> measuringDeviceService.createMeasuringDevice(expectedMeasuringDeviceDTO));
    }
}
