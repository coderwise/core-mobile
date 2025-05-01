package com.coderwise.core.sample.data.sample

import com.coderwise.core.data.repository.ManyRepositoryImpl
import com.coderwise.core.sample.data.sample.remote.SampleRemoteSource

class SampleRepositoryImpl(
    network: SampleRemoteSource
) : SampleRepository, ManyRepositoryImpl<Int, Sample>(
    remoteSource = network
)