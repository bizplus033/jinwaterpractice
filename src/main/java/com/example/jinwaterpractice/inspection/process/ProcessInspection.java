package com.example.jinwaterpractice.inspection.process;

import com.example.jinwaterpractice.inspection.process.report.ProcessInspectionReport;
import com.example.jinwaterpractice.production.Production;
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
import java.util.List;

@Entity
@Table(name = "process_inspection")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProcessInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "production_id")
    private Production production;

    @OneToMany(mappedBy = "processInspection")
    private List<ProcessInspectionReport> processInspectionReportList;

    @Column(name = "inspection_date")
    private LocalDate inspectionDate; // 검사일자

    @Column(name = "completed_status", columnDefinition = "tinyint(1) default 0")
    private Integer completedStatus; // 0: 공정검사 전, 1: 공정검사 완료

    private String etc;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;


}
