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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String batchName; // 배치 이름
    private String cronExpression; // 실행 스케줄 (크론)
    private LocalDateTime lastRunDate; // 마지막 실행 시간

    @Enumerated(EnumType.STRING)
    private BatchStatus status;

}
