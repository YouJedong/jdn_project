package com.priv.jdnights.api.contents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestDto {

    private List<HomeContentDto> homeContentDtoList;
}
