package com.topcontributors;

import javax.ws.rs.core.Response;

interface ContributorApiConsumer {
    Response getByCity(String cityName, Integer size) throws Exception;
}
