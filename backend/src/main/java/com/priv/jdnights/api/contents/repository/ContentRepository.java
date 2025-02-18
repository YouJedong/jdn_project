package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
