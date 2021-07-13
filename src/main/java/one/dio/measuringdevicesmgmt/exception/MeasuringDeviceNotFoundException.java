package one.dio.measuringdevicesmgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeasuringDeviceNotFoundException extends Exception{
    
    public MeasuringDeviceNotFoundException(String internalCode) {
        super(String.format("Measuring Device with Internal Code %s Not Found", internalCode));
    }
}
