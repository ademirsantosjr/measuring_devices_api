package one.dio.measuringdevicesmgmt.controller;

import static one.dio.measuringdevicesmgmt.utils.JsonConvertionUtils.asJsonString;
//import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import one.dio.measuringdevicesmgmt.builder.MeasuringDeviceDTOBuilder;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.service.MeasuringDeviceService;

@ExtendWith(MockitoExtension.class)
public class MeasuringDeviceControllerTest {
    
    private static final String MEASURING_DEVICE_API_URL_PATH = "/api/v1/devices";

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
}
