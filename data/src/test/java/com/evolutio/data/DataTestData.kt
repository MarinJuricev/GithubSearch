package com.evolutio.data

import com.evolutio.data.model.user.RemoteUser

const val TEST_USER_NAME = "USER_NAME"

// The object is "relatively" small and it doesn't have alot
// of nested objects, an alternative to this naive typing out
// could be to use Gson ( or any other preferred parser tool )
// and call Gson().fromJson and provide a static test JSON
// that can serialize / deserialize for our tests needs.
val TEST_REMOTE_USER = RemoteUser(
    name = "name",
    company = "company",
    blog = "blog",
    location = "location",
    email = "email",
    hireable = "hireable",
    bio = "bio",
    publicRepos = 10,
    publicGists = 25,
    followers = 5,
    following = 5,
    createdAt = "createdAt",
    updatedAt = "updatedAt",
    avatarUrl = "avatarUrl",
    eventsUrl = "eventsUrl",
    followersUrl = "followersUrl",
    followingUrl = "followingUrl",
    gistsUrl = "gistsUrl",
    gravatarId = "gravatarId",
    htmlUrl = "htmlUrl",
    id = 1,
    login = "login",
    nodeId = "nodeId",
    organizationsUrl = "organizationsUrl",
    receivedEventsUrl = "receivedEventsUrl",
    reposUrl = "reposUrl",
    siteAdmin = false,
    starredUrl = "starredUrl",
    subscriptionsUrl = "subUrl",
    type = "type",
    url = "url"
)