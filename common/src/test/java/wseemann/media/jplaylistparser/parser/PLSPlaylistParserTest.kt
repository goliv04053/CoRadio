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

package wseemann.media.jplaylistparser.parser

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import wseemann.media.jplaylistparser.parser.pls.PLSPlaylistParser
import wseemann.media.jplaylistparser.playlist.Playlist
import wseemann.media.jplaylistparser.playlist.PlaylistEntry

/**
 * Entries pointing at ".m3u8" streams are added as-is, so these tests stay off the network
 * (other URLs make the parser fetch and parse them recursively).
 */
class PLSPlaylistParserTest {

    private fun parse(text: String): Playlist {
        val playlist = Playlist()
        PLSPlaylistParser(0).parse("http://host/list.pls", text.byteInputStream(), playlist)
        return playlist
    }

    @Test
    fun parsesEntriesWithTitles() {
        val playlist = parse(
            """
            [playlist]
            NumberOfEntries=2
            File1=http://host/one.m3u8
            Title1=First
            Length1=-1
            File2=http://host/two.m3u8
            Title2=Second
            Length2=-1
            """.trimIndent()
        )

        val entries = playlist.playlistEntries
        assertEquals(2, entries.size)
        assertEquals("http://host/one.m3u8", entries[0][PlaylistEntry.URI])
        assertEquals("First", entries[0][PlaylistEntry.PLAYLIST_METADATA])
        assertEquals("http://host/two.m3u8", entries[1][PlaylistEntry.URI])
        assertEquals("Second", entries[1][PlaylistEntry.PLAYLIST_METADATA])
    }

    @Test
    fun keysAreCaseInsensitiveAndValuesTrimmed() {
        val playlist = parse("[playlist]\nfile1 = http://host/one.m3u8 \nlength1=-1\n")

        assertEquals(1, playlist.playlistEntries.size)
        assertEquals("http://host/one.m3u8", playlist.playlistEntries[0][PlaylistEntry.URI])
    }

    @Test
    fun entryWithoutTrailingLengthIsStillSaved() {
        val playlist = parse("[playlist]\nFile1=http://host/one.m3u8\nTitle1=Only")

        assertEquals(1, playlist.playlistEntries.size)
        assertEquals("Only", playlist.playlistEntries[0][PlaylistEntry.PLAYLIST_METADATA])
    }

    @Test
    fun emptyInputProducesNoEntries() {
        assertTrue(parse("").playlistEntries.isEmpty())
        assertTrue(parse("[playlist]\nNumberOfEntries=0\n").playlistEntries.isEmpty())
    }
}
