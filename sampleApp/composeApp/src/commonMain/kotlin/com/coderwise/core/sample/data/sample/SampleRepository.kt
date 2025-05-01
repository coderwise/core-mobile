package com.coderwise.core.sample.data.sample

import com.coderwise.core.domain.repository.Identifiable
import com.coderwise.core.domain.repository.ManyRepository

data class Sample(
    override val id: Int,
    val value: String
) : Identifiable<Int>

interface SampleRepository : ManyRepository<Int, Sample>