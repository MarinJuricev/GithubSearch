package com.evolutio.domain.feature.user_detail

import com.evolutio.domain.model.user.User
import com.evolutio.domain.model.user.UserItem
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
) : UseCaseWithParams<User, List<UserItem>>() {

    override suspend fun buildUseCase(params: User): List<UserItem> =
        withContext(dispatcherProvider.provideComputationContext()) {
            val result = mutableListOf<UserItem>()

            params.name?.let {
                result.add(UserItem("username", params.name))
            }

            params.company?.let {
                result.add(UserItem("company", params.company))
            }

            if (!params.blog.isBlank())
                result.add(UserItem("blog", params.blog))

            params.location?.let {
                result.add(UserItem("location", params.location))
            }

            params.email?.let {
                result.add(UserItem("email", params.email))
            }

            params.hireable?.let {
                result.add(UserItem("hireable", params.hireable))
            }

            result.add(UserItem("public_repos", params.publicRepos.toString()))
            result.add(UserItem("public_gists", params.publicGists.toString()))
            result.add(UserItem("followers", params.followers.toString()))
            result.add(UserItem("following", params.following.toString()))
            result.add(UserItem("created_at", params.createdAt))
            result.add(UserItem("updated_at", params.updatedAt))

            result
        }
}