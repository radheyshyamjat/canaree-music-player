package dev.olog.msc.api.last.fm

import android.support.annotation.IntRange
import dev.olog.msc.api.last.fm.album.info.AlbumInfo
import dev.olog.msc.api.last.fm.album.search.AlbumSearch
import dev.olog.msc.api.last.fm.artist.info.ArtistInfo
import dev.olog.msc.api.last.fm.artist.search.ArtistSearch
import dev.olog.msc.api.last.fm.track.search.TrackSearch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = "56553f687cba2aa671c99caff536def1"
private const val MIN_SEARCH_PAGES = 1L
private const val MAX_SEARCH_PAGES = 5L
private const val DEFAULT_SEARCH_PAGES = MAX_SEARCH_PAGES

private const val DEFAULT_AUTO_CORRECT = 1L

interface LastFmRest {

//    https://en.wikipedia.org/wiki/List_of_ISO_639-2_codes
    // 639-1 works, 639-2 not works

    @GET("" +
            "?method=track.getInfo" +
            "&api_key=$API_KEY" +
            "&artist={artist}" +
            "&track={track}" +
            "&format=json" +
            "&autocorrect={autoCorrect}"
    )
    fun getTrackInfo(
            @Path("track") track: String,
            @Path("artist") artist: String,
            @IntRange(from = 0, to = 1) @Path("autoCorrect") autoCorrect: Long = DEFAULT_AUTO_CORRECT
    ) : Single<TrackInfo>

    @GET("" +
            "?method=track.search" +
            "&track={track}" +
            "&artist={artist}" +
            "&api_key=$API_KEY" +
            "&format=json" +
            "&limit={limit}"
    )
    fun searchTrack(
            @Path("track") track: String,
            @Path("artist") artist: String = "",
            @IntRange(from = MIN_SEARCH_PAGES, to = MAX_SEARCH_PAGES)
            @Path("limit") limit: Long = DEFAULT_SEARCH_PAGES
    ): Single<TrackSearch>

    @GET("" +
            "?method=artist.getinfo" +
            "&artist={artist}" +
            "&api_key=$API_KEY" +
            "&format=json" +
            "&autocorrect={autoCorrect}" +
            "&lang={lang}"
    )
    fun getArtistInfo(
            @Path("artist") artist: String,
            @IntRange(from = 0, to = 1)
            @Path("autoCorrect") autoCorrect: Long = DEFAULT_AUTO_CORRECT,
            @Path("lang") language: String = "en"
    ): Single<ArtistInfo>

    @GET("" +
            "?method=artist.search" +
            "&artist={artist}" +
            "&api_key=$API_KEY" +
            "&format=json" +
            "&limit={limit}"
    )
    fun searchArtist(
            @Path("artist") artist: String,
            @IntRange(from = MIN_SEARCH_PAGES, to = MAX_SEARCH_PAGES)
            @Path("limit") limit: Long = DEFAULT_SEARCH_PAGES
    ): Single<ArtistSearch>

    @GET("" +
            "?method=album.getinfo" +
            "&api_key=$API_KEY" +
            "&artist={artist}" +
            "&album={album}" +
            "&format=json" +
            "&autocorrect={autoCorrect}" +
            "&lang={lang}"
    )
    fun getAlbumInfo(
            @Path("album") album: String,
            @Path("artist") artist: String,
            @IntRange(from = 0, to = 1)
            @Path("autoCorrect") autoCorrect: Long= DEFAULT_AUTO_CORRECT,
            @Path("lang") language: String = "en"
    ): Single<AlbumInfo>

    @GET("" +
            "?method=album.search" +
            "&album={album}" +
            "&api_key=$API_KEY" +
            "&format=json" +
            "&limit={limit}"
    )
    fun searchAlbum(
            @Path("album") album: String,
            @IntRange(from = MIN_SEARCH_PAGES, to = MAX_SEARCH_PAGES)
            @Path("limit") limit: Long = DEFAULT_SEARCH_PAGES
    ): Single<AlbumSearch>

}