package com.topcontributors;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;

import static com.topcontributors.JSONStringValidator.isValid;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GitHubContributorApiConsumerShould {

    @Test
    public void return_users_top_contributors_from_given_city() {
        GitHubContributorApiConsumer apiConsumer = new GitHubContributorApiConsumer(ClientBuilder.newClient());

        String topContributorsResponse = apiConsumer.getByCity("barcelona", 1).readEntity(String.class);

        assertThat("Api consumer returns json response", topContributorsResponse, is(not(emptyString())));
        Assert.assertThat("response is valid json", isValid(topContributorsResponse), is(true));
    }
}