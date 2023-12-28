package com.example.jinwaterpractice.monitoring.plcdata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plc_data")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class PlcData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipment_id")
    private Integer equipmentId;

    @Column(name = "production_date")
    private LocalDate productionDate;

    private Integer count;

    private Integer operate;
}
