package one.dio.measuringdevicesmgmt.builder;

import java.time.LocalDate;

import lombok.Builder;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.enums.UnitOfMeasurement;

@Builder
public class MeasuringDeviceDTOBuilder {
    
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String description = "Thermometer";

    @Builder.Default    
    private UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.DEGREECELCIUS;

    @Builder.Default
    private String internalCode = "TEMP-4578";

    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

    public MeasuringDeviceDTO toDTO() {
        return new MeasuringDeviceDTO(id,
                                      description,
                                      unitOfMeasurement,
                                      internalCode,
                                      createdAt);
    }
}
