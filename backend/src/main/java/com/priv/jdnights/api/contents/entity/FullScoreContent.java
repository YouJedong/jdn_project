package com.priv.jdnights.api.contents.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FS")
public class FullScoreContent extends Content {

    private Integer orderCount;

    private Integer price;

    private Long viewCount;
}
