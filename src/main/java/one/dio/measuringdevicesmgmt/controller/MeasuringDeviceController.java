package one.dio.measuringdevicesmgmt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.exception.InternalCodeAlreadyExistsException;
import one.dio.measuringdevicesmgmt.exception.MeasuringDeviceNotFoundException;
import one.dio.measuringdevicesmgmt.service.MeasuringDeviceService;

@RestController
@RequestMapping("/api/v1/devices")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeasuringDeviceController {
    
    private MeasuringDeviceService measuringDeviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeasuringDeviceDTO createMeasuringDevice(@RequestBody @Valid MeasuringDeviceDTO measuringDeviceDTO) throws InternalCodeAlreadyExistsException{
        return measuringDeviceService.createMeasuringDevice(measuringDeviceDTO);
    }

    @GetMapping("/{internalCode}")
    public MeasuringDeviceDTO findByInternalCode(@PathVariable String internalCode) throws MeasuringDeviceNotFoundException {
        return measuringDeviceService.findByInternalCode(internalCode);
    }

    @GetMapping
    public List<MeasuringDeviceDTO> listAll() {
        return measuringDeviceService.listAll();
    }

    @PutMapping("/{internalCode}")
    public MeasuringDeviceDTO updateByInternalCode(@PathVariable String internalCode, @RequestBody MeasuringDeviceDTO measuringDeviceDTO) throws MeasuringDeviceNotFoundException, InternalCodeAlreadyExistsException {
        return measuringDeviceService.updateByInternalCode(internalCode, measuringDeviceDTO);
    }

    @DeleteMapping("/{internalCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByInternalCode(@PathVariable String internalCode) throws MeasuringDeviceNotFoundException {
        measuringDeviceService.deleteByInternalCode(internalCode);
    }
}
