package com.manufacturing.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manufacturing.backend.common.MachineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MachineStatus status;

    @Column()
    private LocalDateTime lastMaintenanceDate;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ManufacturingOrder> manufacturingOrders;

    @OneToMany(mappedBy = "employeeMachine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Employee> employees;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
