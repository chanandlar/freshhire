package com.freshhire.ingestion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdzunaJobResponse {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Job {

        private String id;
        private String title;
        private String description;
        private String created;

        @JsonProperty("redirect_url")
        private String redirectUrl;

        @JsonProperty("contract_time")
        private String contractTime;

        @JsonProperty("salary_min")
        private Double salaryMin;

        @JsonProperty("salary_max")
        private Double salaryMax;

        @JsonProperty("salary_is_predicted")
        private String salaryIsPredicted;

        private Company company;
        private Location location;
        private Category category;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {

        @JsonProperty("display_name")
        private String displayName;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {

        @JsonProperty("display_name")
        private String displayName;
        private List<String> area;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Category {

        private String label;
        private String tag;
    }

    private List<Job> results;
    private Long count;
}