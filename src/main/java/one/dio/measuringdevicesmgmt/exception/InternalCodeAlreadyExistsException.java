package one.dio.measuringdevicesmgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InternalCodeAlreadyExistsException extends Exception {
    
    public InternalCodeAlreadyExistsException(String internalCode) {
        super(String.format("There is already a Measuring Device resgistered with code %s", internalCode));
    }
}
