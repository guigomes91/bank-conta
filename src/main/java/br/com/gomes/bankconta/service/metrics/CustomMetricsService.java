package br.com.gomes.bankconta.service.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetricsService {
    private final Counter customMetricCounter;
    public CustomMetricsService(MeterRegistry meterRegistry) {
        customMetricCounter = Counter.builder("search_client")
                .description("Consulta de cliente - GET")
                .tags("environment", "development")
                .register(meterRegistry);
    }
    public void incrementCustomMetric() {
        customMetricCounter.increment();
    }
}
