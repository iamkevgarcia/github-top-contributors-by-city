package com.topcontributors;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public final class PageSizeUpTo100ApiConsumer implements ContributorApiConsumer {

    private static final String REST_URI = "https://api.github.com/search/users";

    private Client client;

    PageSizeUpTo100ApiConsumer(Client client) {
        this.client = client;
    }

    public Response getByCity(String cityName, Integer size) {
        return client
                .target(REST_URI)
                .queryParam("q", "location:" + cityName)
                .queryParam("size", size)
                .queryParam("order", "asc")
                .request(MediaType.APPLICATION_JSON)
                .get();
    }
}