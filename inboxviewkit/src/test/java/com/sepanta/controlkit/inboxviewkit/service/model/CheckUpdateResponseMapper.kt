package com.sepanta.controlkit.inboxviewkit.service.model
/*
 *  File: CheckUpdateResponseMapper.kt
 *
 *  Created by morteza on 9/1/25.
 */



import org.junit.Assert.*
import org.junit.Test
import java.util.Locale

class CheckUpdateResponseMapper {

    @Test
    fun getContentBySystemLang_returnsContentForSystemLanguage() {
        val texts = listOf(
            LocalizedText(language = "en", content = "English text"),
            LocalizedText(language = "fa", content = "متن فارسی")
        )

        Locale.setDefault(Locale("fa"))
        val result = texts.getContentBySystemLang()

        assertEquals("متن فارسی", result)
    }

    @Test
    fun getContentBySystemLang_fallsBackToEnglish() {
        val texts = listOf(
            LocalizedText(language = "en", content = "English text")
        )

        Locale.setDefault(Locale("de"))
        val result = texts.getContentBySystemLang()

        assertEquals("English text", result)
    }

    @Test
    fun getContentBySystemLang_returnsNullWhenNoMatch() {
        val texts = listOf(
            LocalizedText(language = "fr", content = "Texte français")
        )

        Locale.setDefault(Locale("de"))
        val result = texts.getContentBySystemLang()

        assertNull(result)
    }

    @Test
    fun toDomain_mapsApiDataCorrectly() {
        val apiData = ApiData(
            id = "123",
            title = listOf(LocalizedText("en", "Title")),
            description = listOf(LocalizedText("en", "Description")),
            force = true,
            icon = "http://icon.png",
            link = "http://link.com",
            version = listOf(LocalizedText("en", "1.0.0")),
            sdk_version = 42,
            minimum_version = "1.0",
            maximum_version = "2.0",
            created_at = "2023-09-01T12:34:56Z"
        )

        val result = apiData.toDomain()

        assertEquals("123", result.id)
        assertEquals("1.0.0", result.version)
        assertEquals("Title", result.title)
        assertEquals("Description", result.description)
        assertEquals("http://icon.png", result.iconUrl)
        assertEquals("http://link.com", result.linkUrl)
        assertEquals("42", result.sdkVersion)
        assertEquals("1.0", result.minimumVersion)
        assertEquals("2.0", result.maximumVersion)
        assertEquals("2023-09-01T12:34:56Z", result.created_at)
        assertNotNull(result.date)
        assertNotNull(result.time)
    }

    @Test
    fun toDomainList_mapsListOfApiData() {
        val response = ApiCheckUpdateResponse(
            data = listOf(
                ApiData(
                    id = "1",
                    title = listOf(LocalizedText("en", "Title1")),
                    description = null,
                    force = null,
                    icon = null,
                    link = null,
                    version = null,
                    sdk_version = null,
                    minimum_version = null,
                    maximum_version = null,
                    created_at = null
                ),
                ApiData(
                    id = "2",
                    title = listOf(LocalizedText("en", "Title2")),
                    description = null,
                    force = null,
                    icon = null,
                    link = null,
                    version = null,
                    sdk_version = null,
                    minimum_version = null,
                    maximum_version = null,
                    created_at = null
                )
            )
        )

        val result = response.toDomainList()

        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("2", result[1].id)
    }

    @Test
    fun toDisplayDate_returnsFormattedDate() {
        val input = "2023-09-01T12:34:56Z"
        Locale.setDefault(Locale.US)

        val result = input.toDisplayDate()

        assertEquals("01 Sep, 2023", result)
    }

    @Test
    fun toDisplayDate_returnsSameStringIfInvalid() {
        val input = "invalid-date"
        val result = input.toDisplayDate()

        assertEquals("invalid-date", result)
    }

    @Test
    fun toDisplayTime_returnsFormattedTime() {
        val input = "2023-09-01T12:34:56Z"
        Locale.setDefault(Locale.US)

        val result = input.toDisplayTime()

        assertEquals("12:34 PM", result)
    }

    @Test
    fun toDisplayTime_returnsSameStringIfInvalid() {
        val input = "invalid-time"
        val result = input.toDisplayTime()

        assertEquals("invalid-time", result)
    }
}
