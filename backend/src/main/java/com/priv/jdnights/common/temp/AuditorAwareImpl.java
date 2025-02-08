package com.priv.jdnights.common.temp;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    // TODO Spring security? or 로그인 정보 받아서 등록
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("admin");
    }
}
