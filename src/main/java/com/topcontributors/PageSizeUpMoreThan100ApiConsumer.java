package com.topcontributors;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class PageSizeUpMoreThan100ApiConsumer implements ContributorApiConsumer {

    private static final String REST_URI = "https://api.github.com/search/users";

    private Client client;

    PageSizeUpMoreThan100ApiConsumer(Client client) {
        this.client = client;
    }

    //TODO: create all the endpoint calls and execute all the completable futures combining it's results
    public Response getByCity(String cityName, Integer size) throws Exception {

        Integer timesThatItShouldCallTheEndpoint = calculateTimesThatItShouldCallTheEndpoint(size);

        List<CompletableFuture<String>> endpointCalls = createEndpointCalls(timesThatItShouldCallTheEndpoint, cityName, size);

        CompletableFuture.allOf();
    }

    private List<CompletableFuture<String>> createEndpointCalls(Integer quantity, String city, Integer size) {
        LinkedList<CompletableFuture<String>> calls = new LinkedList<>();

        for (int i = 0; i < quantity; i++) {
            calls.add(CompletableFuture.supplyAsync(() -> client
                    .target(REST_URI)
                    .queryParam("q", "location:" + city)
                    .queryParam("size", calculateSizeCountCallsQuantityIn(calls.size(), size))
                    .queryParam("order", "asc")
                    .request(MediaType.APPLICATION_JSON)
                    .get()
                    .readEntity(String.class)
            ));
        }

        return calls;
    }

    private Integer calculateTimesThatItShouldCallTheEndpoint(Integer sizeAsked) throws Exception {

        if (sizeAsked <= 0) {
            throw new IllegalArgumentException(String.format("%d is not a valid size", sizeAsked));
        }

        Number chunkSize  = sizeAsked / 100 ;

        return Double.valueOf(Math.ceil(chunkSize.doubleValue())).intValue();
    }

    private Integer calculateSizeCountCallsQuantityIn(Integer numberOfCalls, Integer sizeAsked) {
    }
}