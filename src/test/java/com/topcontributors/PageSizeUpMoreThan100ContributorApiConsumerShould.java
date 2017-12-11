package com.topcontributors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;

import static com.topcontributors.JSONStringValidator.isValid;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class PageSizeUpMoreThan100ContributorApiConsumerShould {

    @Test
    public void return_asked_contributors_from_given_city() throws Exception {
        PageSizeUpMoreThan100ApiConsumer apiConsumer = new PageSizeUpMoreThan100ApiConsumer(ClientBuilder.newClient());

        String topContributorsResponse = apiConsumer.getByCity("barcelona", 110);

        assertThat("Api consumer returns json response", topContributorsResponse, is(not(emptyString())));
        assertThat("response is valid json", isValid(topContributorsResponse), is(true));
        assertThat("There " + 100 + " results", (new Gson().fromJson(topContributorsResponse, JsonArray.class)).size(), is(110));
    }
}