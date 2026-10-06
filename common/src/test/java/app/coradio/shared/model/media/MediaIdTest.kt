/*
 * Copyright 2026 The "CoRadio" Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coradio.shared.model.media

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MediaIdTest {

    private val default = "US"

    // CoRadioService calls getId() with an empty default country; with a non-empty default,
    // any prefixed ID would be reported as "stations in a country".
    private val noCountry = ""

    @Test
    fun getIdReturnsEmptyForEmptyValue() {
        assertEquals("", MediaId.getId("", noCountry))
    }

    @Test
    fun getIdReturnsEmptyForUnknownValue() {
        assertEquals("", MediaId.getId("not-a-media-id", noCountry))
    }

    @Test
    fun getIdMatchesExactId() {
        assertEquals(MediaId.MEDIA_ID_ROOT, MediaId.getId(MediaId.MEDIA_ID_ROOT, noCountry))
        assertEquals(MediaId.MEDIA_ID_FAVORITES_LIST, MediaId.getId(MediaId.MEDIA_ID_FAVORITES_LIST, noCountry))
    }

    @Test
    fun getIdMatchesIdWithSuffix() {
        assertEquals(
            MediaId.MEDIA_ID_CHILD_CATEGORIES,
            MediaId.getId(MediaId.MEDIA_ID_CHILD_CATEGORIES + "11", noCountry)
        )
    }

    @Test
    fun getIdResolvesCountryStationsFromCountriesListWithCode() {
        assertEquals(
            MediaId.MEDIA_ID_COUNTRY_STATIONS,
            MediaId.getId(MediaId.MEDIA_ID_COUNTRIES_LIST + "BR", noCountry)
        )
    }

    @Test
    fun getCountryCodeExtractsUpperCasedSuffix() {
        assertEquals("BR", MediaId.getCountryCode(MediaId.MEDIA_ID_COUNTRIES_LIST + "br", default))
    }

    @Test
    fun getCountryCodeFallsBackToDefault() {
        assertEquals(default, MediaId.getCountryCode(null, default))
        assertEquals(default, MediaId.getCountryCode("", default))
        assertEquals(default, MediaId.getCountryCode(MediaId.MEDIA_ID_COUNTRIES_LIST, default))
        assertEquals(default, MediaId.getCountryCode(MediaId.MEDIA_ID_SEARCH_FROM_APP, default))
    }

    @Test
    fun searchIdRoundTrip() {
        val searchId = MediaId.makeSearchId("jazz")
        assertEquals("search:jazz", searchId)
        assertTrue(MediaId.isFromSearch(searchId))
        assertEquals("jazz", MediaId.normalizeFromSearchId(searchId))
    }

    @Test
    fun isFromSearchIsFalseForPlainIds() {
        assertFalse(MediaId.isFromSearch("jazz"))
        assertFalse(MediaId.isFromSearch(MediaId.MEDIA_ID_ROOT))
    }

    @Test
    fun containsIdDetectsKnownIds() {
        assertTrue(MediaId.containsId("prefix" + MediaId.MEDIA_ID_POPULAR_STATIONS))
        assertFalse(MediaId.containsId("some-station-id"))
    }
}
