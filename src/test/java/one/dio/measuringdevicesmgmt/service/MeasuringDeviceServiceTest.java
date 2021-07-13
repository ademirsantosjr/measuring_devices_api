package one.dio.measuringdevicesmgmt.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;

import java.util.Collections;
import java.util.List;
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
import one.dio.measuringdevicesmgmt.exception.MeasuringDeviceNotFoundException;
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

    @Test
    void whenValidInternalCodeIsGivenThenReturnAMeasuringDevice() throws MeasuringDeviceNotFoundException{
        // given
        MeasuringDeviceDTO expectedFoundMeasuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();
        MeasuringDevice expectedFoundMeasuringDevice = measuringDeviceMapper.toModel(expectedFoundMeasuringDeviceDTO);

        // when
        when(measuringDeviceRepository.findByInternalCode(expectedFoundMeasuringDevice.getInternalCode()))
            .thenReturn(Optional.of(expectedFoundMeasuringDevice));
        
        // then
        MeasuringDeviceDTO foundMeasuringDeviceDTO = measuringDeviceService.findByInternalCode(expectedFoundMeasuringDeviceDTO.getInternalCode());

        assertThat(foundMeasuringDeviceDTO, is(equalTo(expectedFoundMeasuringDeviceDTO)));
    }

    @Test
    void whenANotExistingInternalCodeIsGivenThenThrowAnException() {
        // given
        MeasuringDeviceDTO expectedFoundMeasuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();

        // when
        when(measuringDeviceRepository.findByInternalCode(expectedFoundMeasuringDeviceDTO.getInternalCode()))
            .thenReturn(Optional.empty());
        
        // then
        assertThrows(MeasuringDeviceNotFoundException.class,
            () -> measuringDeviceService.findByInternalCode(expectedFoundMeasuringDeviceDTO.getInternalCode()));
    }

    @Test
    void whenListMeasuringDeviceIsCalledThenReturnAListOfMeasuringDevices() {
        // given
        MeasuringDeviceDTO expectedFoundMeasuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();
        MeasuringDevice expectedFoundMeasuringDevice = measuringDeviceMapper.toModel(expectedFoundMeasuringDeviceDTO);

        // when
        when(measuringDeviceRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundMeasuringDevice));

        // then
        List<MeasuringDeviceDTO> foundListMeasuringDevicesDTO = measuringDeviceService.listAll();

        assertThat(foundListMeasuringDevicesDTO, is(not(empty())));
        assertThat(foundListMeasuringDevicesDTO.get(0), is(equalTo(expectedFoundMeasuringDeviceDTO)));
    }

    @Test
    void whenAnEmptyListOfMeasuringDevicesIsCalledThenReturnAnEmptyListOfMeasuringDevices() {
        //when
        when(measuringDeviceRepository.findAll()).thenReturn(Collections.emptyList());

        //then
        List<MeasuringDeviceDTO> foundListMeasuringDeviceDTO = measuringDeviceService.listAll();

        assertThat(foundListMeasuringDeviceDTO, is(empty()));        
    }

    @Test
    void whenExclusionIsCalledWithValidInternalCodeThenAMeasuringDeviceShouldBeDeleted() throws MeasuringDeviceNotFoundException {
        //given
        MeasuringDeviceDTO expectedDeletedMeasuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();
        MeasuringDevice expectedDeletedMeasuringDevice = measuringDeviceMapper.toModel(expectedDeletedMeasuringDeviceDTO);

        // when
        when(measuringDeviceRepository.findByInternalCode(expectedDeletedMeasuringDeviceDTO.getInternalCode()))
            .thenReturn(Optional.of(expectedDeletedMeasuringDevice));
        doNothing().when(measuringDeviceRepository).deleteByInternalCode(expectedDeletedMeasuringDeviceDTO.getInternalCode());

        // then
        measuringDeviceService.deleteByInternalCode(expectedDeletedMeasuringDeviceDTO.getInternalCode());

        verify(measuringDeviceRepository, times(1)).findByInternalCode(expectedDeletedMeasuringDeviceDTO.getInternalCode());
        verify(measuringDeviceRepository, times(1)).deleteByInternalCode(expectedDeletedMeasuringDeviceDTO.getInternalCode());
    }
}
