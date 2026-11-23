package com.freshhire.ingestion;

import com.freshhire.ingestion.dto.AdzunaJobResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdzunaPollingScheduler {

    private static final Logger log = LoggerFactory.getLogger(AdzunaPollingScheduler.class);

    private final AdzunaApiClient adzunaApiClient;

    @Value("${adzuna.countries}")
    private String[] countries;

    public AdzunaPollingScheduler(AdzunaApiClient adzunaApiClient) {
        this.adzunaApiClient = adzunaApiClient;
    }

    @Scheduled(cron = "${adzuna.poll-cron}")
    public void poll() {
        log.info("Starting Adzuna poll");

        for (String country : countries) {
            log.info("Polling country={}", country);
            List<AdzunaJobResponse.Job> jobs = adzunaApiClient.fetchJobs(country);
            log.info("Fetched {} jobs for country={}", jobs.size(), country);
            // jobs will be passed to JobRepository once it's built
        }

        log.info("Adzuna poll complete");
    }
}
