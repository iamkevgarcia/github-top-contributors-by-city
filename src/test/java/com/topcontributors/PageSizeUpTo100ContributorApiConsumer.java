package com.topcontributors;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;

import static com.topcontributors.JSONStringValidator.isValid;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class PageSizeUpTo100ContributorApiConsumer {

    @Test
    public void return_users_top_contributors_from_given_city() {
        PageSizeUpTo100ApiConsumer apiConsumer = new PageSizeUpTo100ApiConsumer(ClientBuilder.newClient());

        String topContributorsResponse = apiConsumer.getByCity("barcelona", 1).readEntity(String.class);

        assertThat("Api consumer returns json response", topContributorsResponse, is(not(emptyString())));
        Assert.assertThat("response is valid json", isValid(topContributorsResponse), is(true));
    }
}