# github-top-contributors-by-city


### Motivation
Just having fun using CompletableFuture from Java 8 for asynchronous programming.

Get top contributors in given city, at this moment a top contributor is based on quantity of repositories this should be
improved early.

As you can read in [GitHub users search endpoint](https://developer.github.com/v3/search/#search-users) the maximum of per
page query param is 100, so that i wanted to apply concurrency in order to return asked number of items, so if you ask
101, source code will create two completable calls and then execute them merging returned items by the api.