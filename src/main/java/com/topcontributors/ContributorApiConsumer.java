package com.topcontributors;

interface ContributorApiConsumer {
    Object getByCity(String cityName, Integer size) throws Exception;
}
