package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.entity.ContentLang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentLangRepository extends JpaRepository<ContentLang, Long> {

    Optional<ContentLang> findByContentAndLangCode(Content content, String langCode);
}
