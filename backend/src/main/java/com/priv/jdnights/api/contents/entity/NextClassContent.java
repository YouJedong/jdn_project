package com.priv.jdnights.api.contents.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NC")
public class NextClassContent {

    private Integer orderCount;

    private Integer rating;

    private Integer price;

    private String externalCreatedAt;

    private String externalUpdateAt;
}
