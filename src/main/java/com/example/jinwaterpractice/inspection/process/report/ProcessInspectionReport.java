package com.example.jinwaterpractice.inspection.process.report;

import com.example.jinwaterpractice.inspection.process.ProcessInspection;
import com.example.jinwaterpractice.inspection.process.content.ProcessInspectionContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "process_inspection_report")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProcessInspectionReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "processInspection_id")
    private ProcessInspection processInspection;

    @ManyToOne
    @JoinColumn(name = "processInspectionContent_id")
    private ProcessInspectionContent processInspectionContent;

    private String checkedResult; // 판정 선택 값

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

}
