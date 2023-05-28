package com.knitehias.musify.provider.wikidata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.knitehias.musify.provider.wikidata.entity.WikiDataResponse;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class WikiDataService {
    private static final String FORMATTER = "https://www.wikidata.org/wiki/Special:EntityData/%s.json";
    private final MeterRegistry registry;

    @Autowired
    public WikiDataService(MeterRegistry registry) {
        this.registry = registry;
    }

    public String getTitleByQx(String qX) {
        AtomicReference<String> result = new AtomicReference<>();
        registry.timer("service.wikidata.byqx").record(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            RestTemplate restTemplate = new RestTemplate();
            module.addDeserializer(WikiDataResponse.class, new CustomerWikiDataResponseDeserializer(qX));
            objectMapper.registerModule(module);
            restTemplate.getMessageConverters().add(0, new MappingJackson2HttpMessageConverter(objectMapper));
            result.set(Objects.requireNonNull(restTemplate.getForEntity(String.format(FORMATTER, qX), WikiDataResponse.class).getBody()).entities().q().sitelinks().enwiki().title());
        });
        return result.get();
    }
}
