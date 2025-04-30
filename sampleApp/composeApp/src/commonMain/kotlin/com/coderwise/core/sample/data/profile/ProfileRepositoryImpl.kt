package com.coderwise.core.sample.data.profile

import com.coderwise.core.data.repository.OneRepositoryImpl
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.sample.data.profile.local.ProfileLocalSource
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    localSource: ProfileLocalSource
) : ProfileRepository, OneRepositoryImpl<Profile>(
    localSource = localSource
)