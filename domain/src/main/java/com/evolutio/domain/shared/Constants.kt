package com.evolutio.domain.shared

const val PER_PAGE_SIZE = 30
const val INITIAL_PAGE = 1
const val STAR_SORT = "stars"
const val FORK_SORT = "forks"
const val UPDATED_SORT = "updated"
const val CODE_QUERY_PARAMETER = "code"
const val ERROR_PARAMETER = "error"
const val GITHUB_FILE_NAME = "PREFERENCE_GITHUB_FILE_NAME"
const val ACCESS_TOKEN_KEY = "access_token"


const val SORT_KEY = "sort_key"

// In a real world application you'd NEVER keep these files in the open like this
// we could for instance store them in the NDK, or create a private file under
// the apps scope and fetch it, and so on...
// But for this simple use-case as this test app this is "fine".
const val REDIRECT_URI = "githubsearch://login"
const val CLIENT_ID = "6323f66ad95a64c91bbc"
const val CLIENT_SECRET = "ada70b1bb86ab972d0ed06930730586db280fa23"
const val SCOPE = "repo"