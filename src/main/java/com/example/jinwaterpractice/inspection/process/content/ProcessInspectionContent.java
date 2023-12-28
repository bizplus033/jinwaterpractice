package com.example.jinwaterpractice.inspection.process.content;

import com.example.jinwaterpractice.inspection.content.InspectionContent;
import com.example.jinwaterpractice.inspection.process.report.ProcessInspectionReport;
import com.example.jinwaterpractice.product.Product;
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
@Table(name = "process_inspection_content")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProcessInspectionContent { // 제품 공정 검사 항목

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "inspectionContent_id")
    private InspectionContent inspectionContent; // 검사 항목

    @OneToMany(mappedBy = "processInspectionContent")
    private List<ProcessInspectionReport> processInspectionReportList; // 공정 검사 성적서

    @Column(columnDefinition = "int(4) default 1")
    private Integer ranking; // 출력 순위

    @Column(name = "delete_state", columnDefinition = "tinyint(1) default 0")
    private Integer deleteState;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

}

