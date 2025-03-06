package com.priv.jdnights.api.batch.entity;

import com.priv.jdnights.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BatchHst extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contentType;

    private Integer totalCnt;

    private Integer insertCnt;

    private Integer updateCnt;


    public static BatchHst createBatchHst(String contentType, Integer totalCnt, Integer insertCnt, Integer updateCnt) {
        BatchHst batchHst = new BatchHst();
        batchHst.setContentType(contentType);
        batchHst.setTotalCnt(totalCnt);
        batchHst.setInsertCnt(insertCnt);
        batchHst.setUpdateCnt(updateCnt);

        return batchHst;
    }
}
