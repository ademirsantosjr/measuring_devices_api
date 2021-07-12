package one.dio.measuringdevicesmgmt.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.dio.measuringdevicesmgmt.enums.UnitOfMeasurement;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasuringDevice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitOfMeasurement unitOfMeasurement;

    @Column(nullable = false, unique = true)
    private String internalCode;

    private LocalDate createdAt;

    @PrePersist
    private void addTimestamp() {
        createdAt = LocalDate.now();
    }
}
