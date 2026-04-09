package com.coderwise.core.sample.data.profile.local

import com.coderwise.core.sample.data.profile.Profile

fun ProfileRecord.asEntity() = Profile(
    userName = userName
)

fun Profile.asRecord(): ProfileRecord = ProfileRecord(
    userName = userName
)