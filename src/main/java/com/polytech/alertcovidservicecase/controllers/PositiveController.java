package com.polytech.alertcovidservicecase.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.polytech.alertcovidservicecase.models.Positive;
import com.polytech.alertcovidservicecase.models.User;
import com.polytech.alertcovidservicecase.repositories.PositiveRepository;
import com.polytech.alertcovidservicecase.services.JsonBodyHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/positive")
public class PositiveController {

    @Autowired
    private PositiveRepository positiveRepository;

    @GetMapping
    public List<Positive> getAll(@RequestHeader("Authorization") String authorization) {
        System.out.println(getIdFromAuthorization(authorization));
        return positiveRepository.findAll();
    }

    @GetMapping @RequestMapping("{id_user}")
    public List<Positive> getAllForUser(@PathVariable Long id_user) {
        return positiveRepository.findAllByIdUser(id_user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Positive create(@RequestHeader("Authorization") String authorization, @RequestBody final Positive positive) {
        Long id_user = this.getIdFromAuthorization(authorization);
        if (id_user.equals(positive.getId_user())){
            try {
                this.postStreamLocationService_thenCorrect(positive.toJson());
            } catch (IOException e) {
                e.printStackTrace();
                throw new ResponseStatusException( HttpStatus .INTERNAL_SERVER_ERROR, "Something went wrong with the stream location service" ) ;
            }
            return positiveRepository.saveAndFlush(positive);
        } else {
            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Your not authorized to do this action" ) ;
        }
    }

    @DeleteMapping
    public void delete(@RequestBody final Positive positive) {
        positiveRepository.delete(positive);
    }


    private void postStreamLocationService_thenCorrect(String json)
            throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:9000/stream/location/positive");
        System.out.println();

        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        assert(response.getStatusLine().getStatusCode() == 200);
        client.close();
    }

    private long getIdFromAuthorization(String authorization){
        String token = authorization.split(" ")[1];
        DecodedJWT jwt = JWT.decode(token);
        String email = jwt.getClaim("email").asString();

        var client = HttpClient.newHttpClient();
        String url = "http://146.59.234.45:8081/users/" + email;
        var request = HttpRequest.newBuilder(
                URI.create(url))
                .header("Authorization", authorization)
                .build();

        HttpResponse<Supplier<User>> response = null;
        try {
            response = client.send(request, new JsonBodyHandler<>(User.class));
            return response.body().get().id_user;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ResponseStatusException( HttpStatus .INTERNAL_SERVER_ERROR, "can't get your id from your token" ) ;
        }

    }
}
