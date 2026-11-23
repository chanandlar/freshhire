package com.freshhire.model;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class JobDocument {

    private String id;
    private String title;
    private String company;
    private String location;
    private String country;
    private String description;
    private String jobType;
    private Double salaryMin;
    private Double salaryMax;
    private String currency;
    private String applyUrl;
    private List<String> tags;
    private Instant postedAt;
    private Instant indexedAt;
}
