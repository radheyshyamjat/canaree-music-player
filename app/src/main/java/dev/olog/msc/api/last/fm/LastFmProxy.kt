package dev.olog.msc.api.last.fm

import dev.olog.msc.api.last.fm.album.info.AlbumInfo
import dev.olog.msc.api.last.fm.album.search.AlbumSearch
import dev.olog.msc.api.last.fm.annotation.Impl
import dev.olog.msc.api.last.fm.artist.info.ArtistInfo
import dev.olog.msc.api.last.fm.artist.search.ArtistSearch
import dev.olog.msc.api.last.fm.track.info.TrackInfo
import dev.olog.msc.api.last.fm.track.search.TrackSearch
import io.reactivex.Single
import java.net.URLEncoder
import javax.inject.Inject

class LastFmProxy @Inject constructor(
        @Impl private val impl: RestLastFm

): RestLastFm {

    override fun getTrackInfo(track: String, artist: String, autocorrect: Long): Single<TrackInfo> {
        val normalizedTrack = UTF8NormalizedEntity(track)
        val normalizedArtist = UTF8NormalizedEntity(artist)
        return impl.getTrackInfo(
                normalizedTrack.value,
                normalizedArtist.value
        )
    }

    override fun searchTrack(track: String, artist: String, limit: Long): Single<TrackSearch> {
        val normalizedTrack = UTF8NormalizedEntity(track)
        val normalizedArtist = UTF8NormalizedEntity(artist)
        return impl.searchTrack(
                normalizedTrack.value,
                normalizedArtist.value
        )
    }

    override fun getArtistInfo(artist: String, autocorrect: Long, language: String): Single<ArtistInfo> {
        val normalizedArtist = UTF8NormalizedEntity(artist)
        return impl.getArtistInfo(
                normalizedArtist.value
        )
    }

    override fun searchArtist(artist: String, limit: Long): Single<ArtistSearch> {
        val normalizedArtist = UTF8NormalizedEntity(artist)
        return impl.searchArtist(
                normalizedArtist.value
        )
    }

    override fun getAlbumInfo(album: String, artist: String, autocorrect: Long, language: String): Single<AlbumInfo> {
        val normalizedAlbum = UTF8NormalizedEntity(album)
        val normalizedArtist = UTF8NormalizedEntity(artist)
        return impl.getAlbumInfo(
                normalizedAlbum.value,
                normalizedArtist.value
        )
    }

    override fun searchAlbum(album: String, limit: Long): Single<AlbumSearch> {
        val normalizedAlbum = UTF8NormalizedEntity(album)
        return impl.searchAlbum(
                normalizedAlbum.value
        )
    }

    private class UTF8NormalizedEntity(value: String) {

        val value : String = URLEncoder.encode(value, "UTF-8")

    }

}