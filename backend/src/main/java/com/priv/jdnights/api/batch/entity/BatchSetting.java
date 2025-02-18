package com.priv.jdnights.api.batch.entity;

import com.priv.jdnights.api.batch.enums.BatchStatus;
import com.priv.jdnights.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BatchSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(4) COMMENT '배치 이름 타입'")
    private String batchNameType; // 배치 이름 타입

    @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT '실행 스케줄 (크론)'")
    private String cronExpression; // 실행 스케줄 (크론)

    private LocalDateTime lastRunDate; // 마지막 실행 시간

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '배치 활성 상태'")
    private BatchStatus status;

}
