package com.coderwise.core.domain.utils

import kotlin.test.Test
import kotlin.test.assertEquals


class UriUtilsKtTest {

    @Test
    fun `Valid URI with all components`() {
        val uriString =
            "https://www.example.com/path/to/resource?query1=value1&query2=value2#fragment"
        val parsedUri = parseUri(uriString)

        assertEquals("https", parsedUri.scheme)
        assertEquals("www.example.com", parsedUri.host)
        assertEquals(listOf("path", "to", "resource"), parsedUri.pathSegments)
        assertEquals(mapOf("query1" to "value1", "query2" to "value2"), parsedUri.queryParameters)
        assertEquals("fragment", parsedUri.fragment)

        val parsedUri2 = parseUri("anotherpath")
        val expected = CustomUri(scheme = null, host = "anotherpath", pathSegments = emptyList(), queryParameters = emptyMap(), fragment = null)
        assertEquals(expected, parsedUri2)
    }

    @Test
    fun `URI with only scheme and host`() {
        // Test with a URI that only has a scheme and a host, no path, query, or fragment.
        // TODO implement test
    }

    @Test
    fun `URI with only host`() {
        // Test with a URI that only has a host, no scheme, path, query, or fragment.
        // TODO implement test
    }

    @Test
    fun `URI with only path`() {
        // Test with a URI that only has path, no scheme, host, query, or fragment. Ex. /path/to/resource
        // TODO implement test
    }

    @Test
    fun `URI with only query parameters`() {
        // Test with a URI that only has query parameters, no scheme, host, path, or fragment.  Ex. ?query1=value1&query2=value2
        // TODO implement test
    }

    @Test
    fun `URI with only fragment`() {
        // Test with a URI that only has a fragment, no scheme, host, path, or query. Ex. #fragment
        // TODO implement test
    }

    @Test
    fun `URI with empty query value`() {
        // Test with a URI containing a query parameter with an empty value. Ex. ?query1=
        // TODO implement test
    }

    @Test
    fun `URI with empty query key`() {
        // Test with a URI containing a query parameter with an empty key. Ex. ?=value1
        // TODO implement test
    }

    @Test
    fun `URI with multiple query parameters`() {
        // Test with a URI that has multiple query parameters.
        // TODO implement test
    }

    @Test
    fun `URI with no scheme`() {
        // Test with a URI that does not contain a scheme (e.g., no '://').
        // TODO implement test
    }

    @Test
    fun `URI with no host`() {
        // Test with a URI that does not contain a host.
        // TODO implement test
    }

    @Test
    fun `URI with no path`() {
        // Test with a URI that does not contain any path segments.
        // TODO implement test
    }

    @Test
    fun `URI with no query`() {
        // Test with a URI that does not contain any query parameters.
        // TODO implement test
    }

    @Test
    fun `URI with no fragment`() {
        // Test with a URI that does not contain a fragment.
        // TODO implement test
    }

    @Test
    fun `URI with an empty string`() {
        // Test with an empty URI string.
        // TODO implement test
    }

    @Test
    fun `URI with null input`() {
        // Test with a null URI string to see how it handles null input. (should throw error)
        // TODO implement test
    }

    @Test
    fun `URI with special characters in host`() {
        // Test with a URI containing special characters in the host.
        // TODO implement test
    }

    @Test
    fun `URI with special characters in path`() {
        // Test with a URI containing special characters in the path.
        // TODO implement test
    }

    @Test
    fun `URI with special characters in query`() {
        // Test with a URI containing special characters in the query parameters.
        // TODO implement test
    }

    @Test
    fun `URI with special characters in fragment`() {
        // Test with a URI containing special characters in the fragment.
        // TODO implement test
    }

    @Test
    fun `URI with an invalid scheme format`() {
        // Test with a URI that has an invalid scheme format (e.g., missing '://').
        // TODO implement test
    }

    @Test
    fun `URI with only the scheme separator`() {
        //Test with only the separator of the scheme and nothing else Ex. //
        // TODO implement test
    }

    @Test
    fun `URI with multiple question mark`() {
        //Test with multiple question mark Ex. www.example.com?a=b?c=d.
        // TODO implement test
    }

    @Test
    fun `URI with multiple hash`() {
        //Test with multiple hash marks Ex. www.example.com#a#b.
        // TODO implement test
    }
}