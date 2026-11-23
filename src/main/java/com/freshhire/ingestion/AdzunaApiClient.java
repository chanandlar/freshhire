package com.freshhire.ingestion;

import com.freshhire.ingestion.dto.AdzunaJobResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdzunaApiClient {

    private static final Logger log = LoggerFactory.getLogger(AdzunaApiClient.class);

    private final WebClient webClient;

    @Value("${adzuna.app-id}")
    private String appId;

    @Value("${adzuna.app-key}")
    private String appKey;

    @Value("${adzuna.base-url}")
    private String baseUrl;

    @Value("${adzuna.page-size}")
    private int pageSize;

    @Value("${adzuna.max-calls-per-poll}")
    private int maxCallsPerPoll;

    @Value("${job.ttl-days}")
    private int maxDaysOld;

    public AdzunaApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<AdzunaJobResponse.Job> fetchJobs(String country) {
        List<AdzunaJobResponse.Job> allJobs = new ArrayList<>();

        for (int page = 1; page <= maxCallsPerPoll; page++) {
            AdzunaJobResponse response = fetchPage(country, page);

            if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
                log.info("No more results for country={} at page={}", country, page);
                break;
            }

            allJobs.addAll(response.getResults());
            log.info("Fetched page={} for country={}, jobs={}", page, country, response.getResults().size());
        }

        return allJobs;
    }

    private AdzunaJobResponse fetchPage(String country, int page) {
        String url = String.format("%s/%s/search/%d?app_id=%s&app_key=%s&results_per_page=%d&sort_by=date&max_days_old=%d",
                baseUrl, country, page, appId, appKey, pageSize, maxDaysOld);
        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(AdzunaJobResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("Failed to fetch page={} for country={}: {}", page, country, e.getMessage());
            return null;
        }
    }
}
