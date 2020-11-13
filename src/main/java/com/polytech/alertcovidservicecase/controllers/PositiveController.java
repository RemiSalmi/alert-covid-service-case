package com.polytech.alertcovidservicecase.controllers;

import com.polytech.alertcovidservicecase.models.Positive;
import com.polytech.alertcovidservicecase.repositories.PositiveRepository;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/positive")
public class PositiveController {

    @Autowired
    private PositiveRepository positiveRepository;

    @GetMapping
    public List<Positive> list() { return positiveRepository.findAll(); }

    @GetMapping @RequestMapping("{id_user}")
    public List<Positive> get(@PathVariable Long id_user) {
        return positiveRepository.findAllByIdUser(id_user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Positive create(@RequestBody final Positive positive) throws IOException {
        //make credential test
        this.postJsonUsingHttpClient_thenCorrect(positive.toJson());
        return positiveRepository.saveAndFlush(positive);
    }

    @DeleteMapping
    public void delete(@RequestBody final Positive positive) {
        positiveRepository.delete(positive);
    }


    public void postJsonUsingHttpClient_thenCorrect(String json)
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:9000/stream/location/positive");

        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        assert(response.getStatusLine().getStatusCode() == 200);
        client.close();
    }
}
