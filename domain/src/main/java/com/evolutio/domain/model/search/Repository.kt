package com.evolutio.domain.model.search

import java.io.Serializable

data class Repository(
    val name: String,
    val author: String,
    val thumbnail: String,
    val watcherCount: String,
    val forkCount: String,
    val issuesCount: String,
    val starsCount: String,
    val programmingLanguage: String,
    val created: String,
    val lastUpdated: String,
    val projectHtmlUrl: String,
    val ownerHtmlUrl: String,
    val ownerUrl: String
) : Serializable, AdapterItem {
    val repositoryInfo = "$author/$name"
    val transitionName = "${thumbnail}-${repositoryInfo}"
}
