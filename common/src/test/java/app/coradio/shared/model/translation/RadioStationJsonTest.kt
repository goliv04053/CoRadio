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

package app.coradio.shared.model.translation

import app.coradio.shared.model.media.RadioStation
import app.coradio.shared.model.media.getStreamBitrate
import app.coradio.shared.model.media.getStreamUrlFixed
import app.coradio.shared.model.media.isInvalid
import app.coradio.shared.model.media.setVariant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RadioStationJsonTest {

    private val serializer = RadioStationJsonSerializer()
    private val deserializer = RadioStationJsonDeserializer()

    private fun station(): RadioStation {
        val rs = RadioStation.makeDefaultInstance("id-1")
        rs.name = "Radio One"
        rs.setVariant(128, "http://stream.example.net/live")
        rs.imageUrl = "http://img.example.net/logo.png"
        rs.countryCode = "DE"
        rs.genre = "Jazz"
        rs.homePage = "http://example.net"
        rs.sortId = 7
        rs.isLocal = true
        return rs
    }

    @Test
    fun roundTripKeepsFields() {
        val original = station()
        val restored = deserializer.deserialize(serializer.serialize(original))

        assertEquals(original.id, restored.id)
        assertEquals(original.name, restored.name)
        assertEquals(original.getStreamUrlFixed(), restored.getStreamUrlFixed())
        assertEquals(128, restored.getStreamBitrate())
        assertEquals("DE", restored.countryCode)
        assertEquals("Jazz", restored.genre)
        assertEquals("http://example.net", restored.homePage)
        assertEquals("http://img.example.net/logo.png", restored.imageUrl)
        assertEquals(7, restored.sortId)
        assertTrue(restored.isLocal)
        assertFalse(restored.isInvalid())
    }

    @Test
    fun stationWithoutStreamSerializesToEmptyObject() {
        val rs = RadioStation.makeDefaultInstance("id-2")
        assertEquals("{}", serializer.serialize(rs))
    }

    @Test
    fun invalidInputsDeserializeToInvalidInstance() {
        assertTrue(deserializer.deserialize("").isInvalid())
        assertTrue(deserializer.deserialize("true").isInvalid())
        assertTrue(deserializer.deserialize("FALSE").isInvalid())
        assertTrue(deserializer.deserialize("{not json").isInvalid())
    }
}
