package com.topcontributors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class PageSizeUpMoreThan100ApiConsumer implements ContributorApiConsumer {

    private static final String REST_URI = "https://api.github.com/search/users";

    private Client client;

    PageSizeUpMoreThan100ApiConsumer(Client client) {
        this.client = client;
    }

    public String getByCity(String cityName, Integer size) throws Exception {

        Integer timesThatItShouldCallTheEndpoint = calculateTimesThatItShouldCallTheEndpoint(size);

        List<CompletableFuture<String>> endpointCalls =
                createEndpointCalls(timesThatItShouldCallTheEndpoint, cityName, size);

        return endpointCalls.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.joining(",", "[", "]"));
    }

    private List<CompletableFuture<String>> createEndpointCalls(Integer quantity, String city, Integer size) {
        LinkedList<CompletableFuture<String>> calls = new LinkedList<>();

        for (int i = 0; i < quantity; i++) {
            final int j = i;
            calls.add(createCompletableCall(city, j, size));
        }

        return calls;
    }

    private CompletableFuture<String> createCompletableCall(
            String city,
            Integer numberOfCallsAlreadyMade,
            Integer askedPerPage
    ) {
        return CompletableFuture.supplyAsync(() -> {
            String response = client
                    .target(REST_URI)
                    .queryParam("q", "location:" + city)
                    .queryParam("page", Integer.max(numberOfCallsAlreadyMade + 1, 1))
                    .queryParam(
                            "per_page",
                            calculateSizeCountingCallsAlreadyMade(numberOfCallsAlreadyMade, askedPerPage)
                    ).queryParam("order", "asc")
                    .request(MediaType.APPLICATION_JSON)
                    .get()
                    .readEntity(String.class);

            client.close();

            String items = (new Gson().fromJson(response, JsonObject.class)).getAsJsonArray("items").toString();

            return items.substring(1, items.length() - 1);
        }).exceptionally(ex -> {
            System.out.println("Error when calling github users endpoint" + ex.getMessage());
            return "Unknown!";
        });
    }

    private Integer calculateTimesThatItShouldCallTheEndpoint(Integer sizeAsked) throws Exception {

        if (sizeAsked <= 0) {
            throw new IllegalArgumentException(String.format("%d is not a valid size", sizeAsked));
        }

        Number chunkSize  = sizeAsked / 100.1f;

        return Double.valueOf(Math.ceil(chunkSize.doubleValue())).intValue();
    }

    private Integer calculateSizeCountingCallsAlreadyMade(Integer numberOfCalls, Integer sizeAsked) {
        return  numberOfCalls < 1 ? 100 : sizeAsked - (Integer.max(numberOfCalls, 1) * 100);
    }
}