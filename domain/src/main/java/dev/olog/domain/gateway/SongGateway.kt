package dev.olog.domain.gateway

import dev.olog.domain.entity.Song
import io.reactivex.Completable
import io.reactivex.Flowable

interface SongGateway : BaseGateway<Song, Long> {

    fun deleteSingle(songId: Long): Completable

    fun deleteGroup(songList: List<Song>): Completable

    fun getAllNotDistinct(): Flowable<List<Song>>

}