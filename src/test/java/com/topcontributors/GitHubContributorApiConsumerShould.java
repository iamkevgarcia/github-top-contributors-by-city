package com.topcontributors;

import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GitHubContributorApiConsumerShould {

    @Test
    public void return_users_top_contributors_from_given_city() {
        GitHubContributorApiConsumer apiConsumer = new GitHubContributorApiConsumer(ClientBuilder.newClient());

        Response topContributorsResponse = apiConsumer.getByCity("barcelona", 1);

        assertThat("Api consumer returns json response", topContributorsResponse.toString(), is(not(emptyString())));
    }
}