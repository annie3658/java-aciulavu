package com.library.application.service;

import ch.qos.logback.classic.Logger;
import com.library.application.config.CoversConfig;
import com.library.application.dto.CoverDTO;
import com.library.application.dto.ResponseDTO;
import com.library.application.exception.CoverNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class CoverService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CoverService.class);

    private WebClient.Builder webClientBuilder;
    @Autowired
    private CoversConfig config;

    @Autowired
    public CoverService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public CoverDTO findCoverById(String id) {
        CoverDTO cover = webClientBuilder.build()
                .get()
                .uri(config.getUrl() + id)
                .retrieve()
                .bodyToMono(CoverDTO.class)
                .switchIfEmpty(Mono.empty())
                .block();
        if (cover == null) {
            LOGGER.error("The Cover with id {} was not found", id);
            throw new CoverNotFoundException(id);
        }
        return cover;
    }

    public CoverDTO findCoverByBookTitle(String title) {
        CoverDTO cover = webClientBuilder.build()
                .get()
                .uri(config.getCover() + title)
                .retrieve()
                .bodyToMono(CoverDTO.class)
                .switchIfEmpty(Mono.empty())
                .block();
        if (cover == null) {
            LOGGER.error("The Cover for book {} was not found", title);
            throw new CoverNotFoundException(title);
        }
        return cover;
    }

    public List<CoverDTO> getAllCovers() {
        Flux<CoverDTO> flux = webClientBuilder.build()
                .get()
                .uri(config.getUrl())
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(CoverDTO.class);

        List<CoverDTO> covers = flux.collectList().block();
        return covers;
    }

    public CoverDTO insert(CoverDTO cover) {

        WebClient.RequestBodySpec uri = webClientBuilder.build()
                .put()
                .uri(config.getUrl())
                .accept(APPLICATION_JSON);

        return prepareCoverForInsertOrUpdate(cover, uri);

    }

    public CoverDTO update(CoverDTO cover) {

        WebClient.RequestBodySpec uri = webClientBuilder.build()
                .post()
                .uri(config.getUrl())
                .accept(APPLICATION_JSON);

        return prepareCoverForInsertOrUpdate(cover, uri);

    }

    public void delete(String id) {
         webClientBuilder.build()
                .delete()
                .uri(config.getUrl() + id)
                .exchange().doOnSuccess(clientResponse -> new ResponseDTO(true)).block();


    }

    private CoverDTO prepareCoverForInsertOrUpdate(CoverDTO cover, WebClient.RequestBodySpec uri) {
        BodyInserter<CoverDTO, ReactiveHttpOutputMessage> inserter
                = BodyInserters.fromObject(cover);

        WebClient.ResponseSpec response = uri
                .body(inserter)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();

        return response.bodyToMono(CoverDTO.class).block();
    }
}
