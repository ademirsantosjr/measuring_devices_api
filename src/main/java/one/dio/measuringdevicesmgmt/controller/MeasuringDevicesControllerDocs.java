package one.dio.measuringdevicesmgmt.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.exception.InternalCodeAlreadyExistsException;
import one.dio.measuringdevicesmgmt.exception.MeasuringDeviceNotFoundException;

@Api("Manages measuring devices by unique code")
public interface MeasuringDevicesControllerDocs {
    
    @ApiOperation(value = "Measuring Device creation operation")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created measuring device with success"),
        @ApiResponse(code = 400, message = "Missing required fields")
    })
    MeasuringDeviceDTO createMeasuringDevice(MeasuringDeviceDTO measuringDeviceDTO) throws InternalCodeAlreadyExistsException;

    @ApiOperation(value = "Returns measuring device found by a given unique code")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Found measuring device with success"),
        @ApiResponse(code = 404, message = "Not found measuring device with given unique code")
    })
    MeasuringDeviceDTO findByInternalCode(String internalCode) throws MeasuringDeviceNotFoundException;

    @ApiOperation(value = "Returns all measuring devices in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all measuring devices in the database")
    })
    List<MeasuringDeviceDTO> listAll();

    @ApiOperation(value = "Deletes a measuring device by given a unique code")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted measuring device with success"),
        @ApiResponse(code = 404, message = "Not found measuring device with given unique code")
    })
    void deleteByInternalCode(String internalCode) throws MeasuringDeviceNotFoundException;
}
