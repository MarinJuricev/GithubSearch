package com.evolutio.data.model.repository


import com.evolutio.domain.model.search.Repository
import com.evolutio.domain.model.search.SearchResponse
import com.google.gson.annotations.SerializedName

data class RemoteSearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<RemoteItem>,
    @SerializedName("total_count")
    val totalCount: Int
)

/**
 * Extension function to map from impure RemoteSearchResponse data class to pure SearchResponse data class.
 * Can be called on SearchResponse objects. Each RemoteSearchResponse object contained in
 * will be mapped to SearchResponse object
 *
 *@return Repository object mapped from RemoteSearchResponse object
 */
fun RemoteSearchResponse.toSearchResponse(): SearchResponse {
    return SearchResponse(
        totalCount = totalCount,
        repositories = items.toRepository()
    )
}

/**
 * Extension function to map from impure RemoteItem data class to pure Repository data class.
 * Can be called on RemoteItem objects. Each RemoteItem object contained in
 * will be mapped to Repository object
 *
 *@return Repository object mapped from RemoteSearchResponse object
 */
fun List<RemoteItem>.toRepository(): List<Repository> {
    return this.map { remoteItem ->
        Repository(
            name = remoteItem.name,
            author = remoteItem.owner.login,
            thumbnail = remoteItem.owner.avatarUrl,
            watcherCount = remoteItem.watchers.toString(),
            forkCount = remoteItem.forksCount.toString(),
            starsCount = remoteItem.stargazersCount.toString(),
            issuesCount = remoteItem.openIssuesCount.toString(),
            programmingLanguage = remoteItem.language
                ?: "Unknown", // For now good, if it was a "serious" app we'd send a error code that could be localized for instance error_10023 or error_noLanguage
            created = remoteItem.createdAt,
            lastUpdated = remoteItem.updatedAt,
            projectHtmlUrl = remoteItem.htmlUrl,
            ownerHtmlUrl = remoteItem.owner.htmlUrl,
            ownerUrl = remoteItem.owner.url
        )
    }
}