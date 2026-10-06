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

import app.coradio.shared.model.eq.EqualizerState
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class EqualizerSerializationTest {

    @Test
    fun roundTripKeepsPresetsRangeAndLevels() {
        val state = EqualizerState()
        state.presets = arrayListOf("One", "Two", "Three")
        state.bandLevelRange = shortArrayOf(-1500, 1500)
        state.bandLevels = shortArrayOf(100, 200, 300, 400, 500)

        val restored = EqualizerStateJsonDeserializer().deserialize(EqualizerJsonStateSerializer().serialize(state))

        assertEquals(listOf("One", "Two", "Three"), restored.presets)
        assertArrayEquals(shortArrayOf(-1500, 1500), restored.bandLevelRange)
        assertArrayEquals(shortArrayOf(100, 200, 300, 400, 500), restored.bandLevels)
    }
}
