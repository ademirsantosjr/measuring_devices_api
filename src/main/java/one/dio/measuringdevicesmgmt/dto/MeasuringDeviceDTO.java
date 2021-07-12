package one.dio.measuringdevicesmgmt.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

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

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UnitOfMeasurement unitOfMeasurement;

    @NotNull
    private String internalCode;

    private LocalDate createdAt;
}
