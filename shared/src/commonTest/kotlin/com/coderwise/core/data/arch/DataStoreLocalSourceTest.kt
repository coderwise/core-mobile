package com.coderwise.core.data.arch

import androidx.datastore.core.DataStore
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

@Serializable
internal data class Sample(
    val id: String,
    val value: String
)

@Serializable
internal data class SampleRecord(
    val id: String,
    val value: String
)

class DataStoreLocalSourceTest {
//    private val mockDataStore = mockk<DataStore<DataStoreRecord<SampleRecord>>>(relaxed = true)
//    private val localSource = DataStoreLocalSource<Sample, String, SampleRecord>(
//        identify = { it.id },
//        recordToEntity = { it.asDomainModel() },
//        entityToRecord = { it.asRecord() },
//        dataStore = mockDataStore
//    )

    @Test
    fun `DataStoreRecord extensions`() = runTest {
//        val list = List(5) {
//            SampleRecord(it.toString(), "existing $it")
//        }
//
//        val record = list.findById("2", Sample::id, SampleRecord::asDomainModel)
//        assertEquals("existing 2", record?.value)
//
//        val newEntity = Sample("100", "added")
//        val alteredList =
//            list.upsert(newEntity, Sample::id, SampleRecord::asDomainModel, Sample::asRecord)
//        assertEquals(6, alteredList.size)
//
//        val updatedRecord = record!!.copy(value = "updated")
//        val updatedList =
//            list.upsert(updatedRecord, Sample::id, SampleRecord::asDomainModel, Sample::asRecord)
//        assertEquals(
//            "updated",
//            updatedList.findById("2", Sample::id, SampleRecord::asDomainModel)?.value
//        )
//
//        val newList = list.delete("2", Sample::id, SampleRecord::asDomainModel)
//        assertEquals(4, newList.size)
    }

    @Test
    fun `Update existing entity`() = runTest {
        // Test updating an existing entity in the DataStore.
        // Verify that the entity with the given ID is updated correctly.
    }

    @Test
    fun `Add new entity`() {
        // Test adding a new entity to the DataStore.
        // Verify that the new entity is added correctly and the returned ID is correct.
    }

    @Test
    fun `Multiple entity updates`() {
        // Test updating multiple entities in a sequence.
        // Verify that each update is reflected correctly in the DataStore.
        // TODO implement test
    }

    @Test
    fun `Update entity with same data`() {
        // Test updating an entity with the same data it already has.
        // Verify that the DataStore state remains consistent and no duplicates are created.
        // TODO implement test
    }

    @Test
    fun `Empty data store update`() {
        // Test adding a new entity to an initially empty DataStore.
        // Verify that the entity is correctly added.
        // TODO implement test
    }

    @Test
    fun `Verify ID returned from update`() {
        // Test if the method correctly returns the ID of the entity that was updated or added.
        // TODO implement test
    }

    @Test
    fun `Verify record mapping during update`() {
        // Test if the `mapToRecord` function is called and
        // applied correctly during the update process.
        // TODO implement test
    }

    @Test
    fun `Verify entity mapping on read after update`() {
        // Test if the `mapToEntity` is called and applied correctly when we
        // read data from the datastore after an update.
        // TODO implement test
    }

    @Test
    fun `Non existing entity update`() {
        // Test updating an entity that does not exist yet in the data store
        // to make sure it is added correctly.
        // TODO implement test
    }

    @Test
    fun `Concurrent updates to same entity`() {
        // Test what happens when multiple concurrent update calls are made
        // for the same entity. Verify the final state of the data store is correct.
        // TODO implement test
    }

    @Test
    fun `Concurrent updates to different entities`() {
        // Test multiple concurrent updates to different entities.
        // Ensure that no data corruption occurs.
        // TODO implement test
    }

    @Test
    fun `DataStore error during update`() {
        // Simulate an error within DataStore during the updateData call.
        // Verify how the `update` function handles such an exception.
        // Does it rethrow it or handle it internally?
        // TODO implement test
    }

    @Test
    fun `Null entity provided`() {
        // Test the behavior when a null entity is passed to the update method.
        // Check for potential NullPointerExceptions or if null is handled gracefully.
        // TODO implement test
    }

    @Test
    fun `Null ID during update`() {
        // Test the scenario where identify function returns null.
        // Check if it handles correctly.
        // TODO implement test
    }

    @Test
    fun `Empty List for mapToRecord`() {
        // Test the behavior when list is empty for `mapToRecord` function
        // TODO implement test
    }

    @Test
    fun `Empty List for mapToEntity`() {
        // Test the behavior when list is empty for `mapToEntity` function
        // TODO implement test
    }

}