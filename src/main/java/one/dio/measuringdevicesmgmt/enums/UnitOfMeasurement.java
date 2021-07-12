package one.dio.measuringdevicesmgmt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitOfMeasurement {

    MILLIMETER("mm"),
    KILOGRAM("kg"),
    DEGREECELCIUS("°C");

    private final String unitOfMeasurement;
}
