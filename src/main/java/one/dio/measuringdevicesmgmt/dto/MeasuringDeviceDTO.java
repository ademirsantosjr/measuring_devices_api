package one.dio.measuringdevicesmgmt.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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

    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    @Valid
    private UnitOfMeasurement unitOfMeasurement;

    @NotEmpty
    private String internalCode;

    private LocalDate createdAt;
}
