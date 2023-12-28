package com.example.jinwaterpractice.inspection.content;

import com.example.jinwaterpractice.inspection.code.InspectionCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "inspection_contents")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class InspectionContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inspection_code_id")
    private InspectionCode inspectionCode;

    private String content; // 검사 항목

    private String checkContent; // 판정 결과: O, X, 그 외 결과 값 들어올 수 있음

    private String etc;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "delete_state", columnDefinition = "tinyint(1) default 0")
    private Integer deleteState;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

}
