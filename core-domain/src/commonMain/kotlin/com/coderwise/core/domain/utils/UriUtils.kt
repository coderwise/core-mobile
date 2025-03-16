package com.coderwise.core.domain.utils


data class CustomUri(
    val scheme: String? = null,
    val host: String? = null,
    val pathSegments: List<String> = emptyList(),
    val queryParameters: Map<String, String> = emptyMap(),
    val fragment: String? = null
)

fun parseUri(uriString: String): CustomUri {
    val schemeRegex = Regex("^(.*?)://")
    val schemeMatch = schemeRegex.find(uriString)
    val scheme = schemeMatch?.groups?.get(1)?.value

    val withoutScheme = schemeMatch?.let {
        uriString.substring(it.range.last + 1)
    } ?: uriString

    val fragmentRegex = Regex("#(.*)")
    val fragmentMatch = fragmentRegex.find(withoutScheme)
    val fragment = fragmentMatch?.groups?.get(1)?.value

    val withoutFragment = fragmentMatch?.let {
        withoutScheme.substring(0, it.range.first)
    } ?: withoutScheme

    val queryRegex = Regex("\\?(.*)")
    val queryMatch = queryRegex.find(withoutFragment)
    val query = queryMatch?.groups?.get(1)?.value
    val queryParameters = mutableMapOf<String, String>()
    query?.split("&")?.forEach { pair ->
        val (key, value) = pair.split("=")
        queryParameters[key] = value
    }

    val withoutQuery = queryMatch?.let {
        withoutFragment.substring(0, it.range.first)
    } ?: withoutFragment

    val hostAndPath = withoutQuery.split("/")
    val host = hostAndPath.firstOrNull()

    val pathSegments = if (hostAndPath.size > 1) {
        hostAndPath.subList(1, hostAndPath.size)
    } else {
        emptyList()
    }

    return CustomUri(scheme, host, pathSegments, queryParameters, fragment)
}
