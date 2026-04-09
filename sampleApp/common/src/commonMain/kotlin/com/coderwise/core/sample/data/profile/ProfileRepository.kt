package com.coderwise.core.sample.data.profile

import com.coderwise.core.domain.repository.OneRepository

data class Profile(
    val userName: String
)

interface ProfileRepository : OneRepository<Profile>