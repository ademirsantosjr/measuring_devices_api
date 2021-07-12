package one.dio.measuringdevicesmgmt.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.dio.measuringdevicesmgmt.enums.UnitOfMeasurement;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasuringDeviceDTO {        

    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement unitOfMeasurement;

    private String internalCode;

    private LocalDate createdAt;
}
