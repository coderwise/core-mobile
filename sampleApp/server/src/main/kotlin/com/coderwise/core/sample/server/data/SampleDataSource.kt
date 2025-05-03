package com.coderwise.core.sample.server.data

import com.coderwise.core.sample.server.api.SampleDto
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document


@Serializable
data class SampleEntity(
    val id: Int,
    val value: String
) {
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): SampleEntity =
            json.decodeFromString(document.toJson())
    }
}

class SampleDataSource(
    database: MongoDatabase
) {
    var collection: MongoCollection<Document>

    init {
        database.createCollection("samples")
        collection = database.getCollection("samples")
    }

    suspend fun create(sample: SampleDto): Unit = withContext(Dispatchers.IO) {
        collection.insertOne(sample.asRecord().toDocument())
    }

    suspend fun read(): List<SampleDto> = withContext(Dispatchers.IO) {
        collection.find()
            .map { SampleEntity.fromDocument(it) }
            .toList()
            .map { it.asResponse() }
    }

    suspend fun read(id: Int): SampleDto? = withContext(Dispatchers.IO) {
        collection.find(
            Filters.eq("id", id)
        ).firstOrNull()?.let { SampleEntity.fromDocument(it).asResponse() }
    }

    suspend fun update(sample: SampleDto): Unit = withContext(Dispatchers.IO) {
        collection.replaceOne(
            Filters.eq("id", sample.id.toString()),
            sample.asRecord().toDocument()
        )
    }

    suspend fun delete(id: Int): Unit = withContext(Dispatchers.IO) {
        collection.deleteOne(Filters.eq("id", id))
    }
}

private fun SampleDto.asRecord() = SampleEntity(
    id = id,
    value = value
)

private fun SampleEntity.asResponse() = SampleDto(
    id = id,
    value = value
)
