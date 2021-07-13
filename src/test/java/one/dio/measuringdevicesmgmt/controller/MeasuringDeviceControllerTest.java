package one.dio.measuringdevicesmgmt.controller;

import static one.dio.measuringdevicesmgmt.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import one.dio.measuringdevicesmgmt.builder.MeasuringDeviceDTOBuilder;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.exception.MeasuringDeviceNotFoundException;
import one.dio.measuringdevicesmgmt.service.MeasuringDeviceService;

@ExtendWith(MockitoExtension.class)
public class MeasuringDeviceControllerTest {
    
    private static final String MEASURING_DEVICE_API_URL_PATH = "/api/v1/devices";
    private static final String INVALID_MEASURING_DEVICE_INTERNAL_CODE = "DVC-12345";

    private MockMvc mockMvc;

    @Mock
    private MeasuringDeviceService measuringDeviceService;

    @InjectMocks
    private MeasuringDeviceController measuringDeviceController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(measuringDeviceController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTIsCalledThenAMeasuringDeviceIsCreated() throws Exception {
        // given
        MeasuringDeviceDTO measuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();

        // when
        when(measuringDeviceService.createMeasuringDevice(measuringDeviceDTO)).thenReturn(measuringDeviceDTO);

        // then
        mockMvc.perform(post(MEASURING_DEVICE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(measuringDeviceDTO)))
                .andExpect(jsonPath("$.description", is(measuringDeviceDTO.getDescription())))
                .andExpect(jsonPath("$.unitOfMeasurement", is(measuringDeviceDTO.getUnitOfMeasurement().toString())))
                .andExpect(jsonPath("$.internalCode", is(measuringDeviceDTO.getInternalCode())));
    }

    @Test
    void whenPOSTIsCalledWithEmptyRequiredFieldAnErrorIsReturned() throws Exception{
        // given
        MeasuringDeviceDTO measuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();
        measuringDeviceDTO.setInternalCode(null);

        // then
        mockMvc.perform(post(MEASURING_DEVICE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(measuringDeviceDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidInternalCodeThenOkStatusIsReturned() throws Exception {
        // give
        MeasuringDeviceDTO measuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();

        // when
        when(measuringDeviceService.findByInternalCode(measuringDeviceDTO.getInternalCode()))
            .thenReturn(measuringDeviceDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.get(MEASURING_DEVICE_API_URL_PATH + "/" + measuringDeviceDTO.getInternalCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(measuringDeviceDTO.getDescription())))
                .andExpect(jsonPath("$.internalCode", is(measuringDeviceDTO.getInternalCode())))
                .andExpect(jsonPath("$.unitOfMeasurement", is(measuringDeviceDTO.getUnitOfMeasurement().toString())));
    }

    @Test
    void whenGETisCalledWithoutRegisteredInternalCodeThenNotFoundStatusIsReturned() throws Exception{
        // given
        MeasuringDeviceDTO measuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();

        // when
        when(measuringDeviceService.findByInternalCode(measuringDeviceDTO.getInternalCode())).thenThrow(MeasuringDeviceNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(MEASURING_DEVICE_API_URL_PATH + "/" + measuringDeviceDTO.getInternalCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithMeasuringDevicesIsCalledThenOkStatusIsReturned() throws Exception {
        // give
        MeasuringDeviceDTO measuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();

        // when
        when(measuringDeviceService.listAll())
            .thenReturn(Collections.singletonList(measuringDeviceDTO));
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.get(MEASURING_DEVICE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is(measuringDeviceDTO.getDescription())))
                .andExpect(jsonPath("$[0].internalCode", is(measuringDeviceDTO.getInternalCode())))
                .andExpect(jsonPath("$[0].unitOfMeasurement", is(measuringDeviceDTO.getUnitOfMeasurement().toString())));
    }

    @Test
    void whenGETListWithoutMeasuringDevicesIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        MeasuringDeviceDTO measuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();

        // when
        when(measuringDeviceService.listAll()).thenReturn(Collections.singletonList(measuringDeviceDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(MEASURING_DEVICE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenPUTIsCalledWithValidInternalCodeThenMeasuringDeviceIsUpdated() throws Exception {
        // given
        MeasuringDeviceDTO toUpdateMeasuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();        
        MeasuringDeviceDTO expectedUpdatedMeasuringDeviceDTO = toUpdateMeasuringDeviceDTO;
        toUpdateMeasuringDeviceDTO.setInternalCode("Changed");

        doReturn(expectedUpdatedMeasuringDeviceDTO)
            .when(measuringDeviceService).updateByInternalCode(toUpdateMeasuringDeviceDTO.getInternalCode(), expectedUpdatedMeasuringDeviceDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(MEASURING_DEVICE_API_URL_PATH + "/" + toUpdateMeasuringDeviceDTO.getInternalCode())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(toUpdateMeasuringDeviceDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is(expectedUpdatedMeasuringDeviceDTO.getDescription())))
            .andExpect(jsonPath("$.internalCode", is(expectedUpdatedMeasuringDeviceDTO.getInternalCode())))
            .andExpect(jsonPath("$.unitOfMeasurement", is(expectedUpdatedMeasuringDeviceDTO.getUnitOfMeasurement().toString())));
    }

    @Test
    void whenDELETEIsCalledWithValidInternalCodeThenNoContentStatusIsReturned() throws Exception {
        // given
        MeasuringDeviceDTO measuringDeviceDTO = MeasuringDeviceDTOBuilder.builder().build().toDTO();

        // when
        doNothing().when(measuringDeviceService).deleteByInternalCode(measuringDeviceDTO.getInternalCode());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(MEASURING_DEVICE_API_URL_PATH + "/" + measuringDeviceDTO.getInternalCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidInternalCodeThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(MeasuringDeviceNotFoundException.class)
            .when(measuringDeviceService).deleteByInternalCode(INVALID_MEASURING_DEVICE_INTERNAL_CODE);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(MEASURING_DEVICE_API_URL_PATH + "/" + INVALID_MEASURING_DEVICE_INTERNAL_CODE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
