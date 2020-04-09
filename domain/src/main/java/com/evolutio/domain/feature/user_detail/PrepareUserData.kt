package com.evolutio.domain.feature.user_detail

import com.evolutio.domain.ext.isNotNullOrNotBlankLet
import com.evolutio.domain.model.user.IBaseUser
import com.evolutio.domain.model.user.UserItem
import com.evolutio.domain.model.user.private_user.PrivateUser
import com.evolutio.domain.shared.DispatcherProvider
import com.evolutio.domain.shared.UseCaseWithParams
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Usecase used to build UserItems depending on the available User data,
 * the field itemTitle represents ( in Androids case res.string value that can be localized )
 * if this was a Kotlin Multiplatform project this would map to the respected
 * platform R file
 */
class PrepareUserData @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<IBaseUser, List<UserItem>>() {

    override suspend fun buildUseCase(params: IBaseUser): List<UserItem> =
        withContext(dispatcherProvider.provideComputationContext()) {
            val result = mutableListOf<UserItem>()

            if (params.name.isNotNullOrNotBlankLet()) {
                result.add(UserItem("username", params.name!!))
            }

            if (params.company.isNotNullOrNotBlankLet()) {
                result.add(UserItem("company", params.company!!))
            }

            if (params.blog.isNotNullOrNotBlankLet()) {
                result.add(UserItem("blog", params.blog))
            }

            if (params.location.isNotNullOrNotBlankLet()) {
                result.add(UserItem("location", params.location!!))
            }

            if (params.email.isNotNullOrNotBlankLet()) {
                result.add(UserItem("email", params.email!!))
            }

            if (params.hireable.isNotNullOrNotBlankLet()) {
                result.add(UserItem("hireable", params.hireable!!))
            }

            result.add(UserItem("public_repos", params.publicRepos.toString()))
            result.add(UserItem("public_gists", params.publicGists.toString()))
            result.add(UserItem("followers", params.followers.toString()))
            result.add(UserItem("following", params.following.toString()))
            result.add(UserItem("created_at", params.createdAt))
            result.add(UserItem("updated_at", params.updatedAt))

            //PrivateUserSpecific fields
            if (params is PrivateUser) {
                result.add(UserItem("avatar_url", params.avatarUrl))
                result.add(UserItem("events_url", params.eventsUrl))
                result.add(UserItem("followers_url", params.followersUrl))
                result.add(UserItem("following_url", params.followingUrl))
                result.add(UserItem("gists_url", params.gistsUrl))
                result.add(UserItem("html_url", params.htmlUrl))
                result.add(UserItem("id", params.id.toString()))
                result.add(UserItem("login", params.login))
                result.add(UserItem("node_id", params.nodeId))
                result.add(UserItem("organizations_url", params.organizationsUrl))
                result.add(UserItem("repos_url", params.reposUrl))
                result.add(UserItem("starred_url", params.starredUrl))

                if (params.gravatarId.isNotNullOrNotBlankLet()) {
                    result.add(UserItem("gravatar_id", params.gravatarId))
                }
            }

            result
        }
}