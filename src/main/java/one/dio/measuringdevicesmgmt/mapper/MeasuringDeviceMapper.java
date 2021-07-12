package one.dio.measuringdevicesmgmt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import one.dio.measuringdevicesmgmt.dto.MeasuringDeviceDTO;
import one.dio.measuringdevicesmgmt.entity.MeasuringDevice;

@Mapper
public interface MeasuringDeviceMapper {
    
    MeasuringDeviceMapper INSTANCE = Mappers.getMapper(MeasuringDeviceMapper.class);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd-MM-yyyy")
    MeasuringDevice toModel(MeasuringDeviceDTO measuringDeviceDTO);

    MeasuringDeviceDTO toDTO(MeasuringDevice measuringDevice);
}
