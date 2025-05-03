package com.coderwise.core.sample.data.sample.remote

import com.coderwise.core.data.repository.ManyRemoteSource
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome
import com.coderwise.core.sample.data.sample.Sample
import com.coderwise.core.sample.server.api.SampleDto

class SampleRemoteSource(
    private val api: SampleApi
) : ManyRemoteSource<Int, Sample> {

    override suspend fun readAll(): Outcome<List<Sample>> = tryOutcome {
        api.readAll().map { it.asDomainModel() }
    }

    override suspend fun create(entity: Sample): Outcome<Int> = tryOutcome {
        api.create(entity.asDto())
    }


    override suspend fun update(entity: Sample): Outcome<Unit> = tryOutcome {
        api.update(entity.asDto())
    }

    override suspend fun delete(id: Int): Outcome<Unit> = tryOutcome {
        api.delete(id)
    }
}

private fun Sample.asDto() = SampleDto(id, value)

private fun SampleDto.asDomainModel() = Sample(id, value)
