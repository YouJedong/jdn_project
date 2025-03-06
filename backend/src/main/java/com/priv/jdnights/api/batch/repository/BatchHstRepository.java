package com.priv.jdnights.api.batch.repository;

import com.priv.jdnights.api.batch.entity.BatchHst;
import com.priv.jdnights.api.batch.entity.BatchSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchHstRepository extends JpaRepository<BatchHst, Long> {

}
