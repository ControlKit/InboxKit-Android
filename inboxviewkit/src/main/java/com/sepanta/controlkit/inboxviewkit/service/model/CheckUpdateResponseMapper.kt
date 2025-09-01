package com.sepanta.controlkit.inboxviewkit.service.model

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class ApiCheckUpdateResponse(
    val data: List<ApiData>
)

data class ApiData(
    val id: String?,
    val title: List<LocalizedText>?,
    val description: List<LocalizedText>?,
    val force: Boolean?,
    val icon: String?,
    val link: String?,
    val version: List<LocalizedText>?,
    val sdk_version: Int?,
    val minimum_version: String?,
    val maximum_version: String?,
    val created_at: String?
)

data class LocalizedText(
    val language: String?,
    val content: String?
)

fun List<LocalizedText>?.getContentBySystemLang(): String? {
    val lang = Locale.getDefault().language
    return this?.firstOrNull { it.language == lang }?.content
        ?: this?.firstOrNull { it.language == "en" }?.content
}

fun ApiData.toDomain(): InboxViewResponse {
    return InboxViewResponse(
        id = id,
        version = version.getContentBySystemLang(),
        title = title.getContentBySystemLang(),
        description = description.getContentBySystemLang(),
        iconUrl = icon,
        linkUrl = link,
        sdkVersion = sdk_version?.toString(),
        minimumVersion = minimum_version,
        maximumVersion = maximum_version,
        created_at = created_at,
        date = created_at?.toDisplayDate(),
        time = created_at?.toDisplayTime(),
    )
}


fun ApiCheckUpdateResponse.toDomainList(): List<InboxViewResponse> {
    return data.map { it.toDomain() }
}

fun String.toDisplayDate(): String {
    return try {
        val odt = OffsetDateTime.parse(this)
        val currentLocale = Locale.getDefault() //
        odt.format(DateTimeFormatter.ofPattern("dd MMM, yyyy", currentLocale))
    } catch (e: Exception) {
        this
    }
}

fun String.toDisplayTime(): String {
    return try {
        val odt = OffsetDateTime.parse(this)
        val currentLocale = Locale.getDefault()
        odt.format(DateTimeFormatter.ofPattern("hh:mm a", currentLocale))
    } catch (e: Exception) {
        this
    }
}