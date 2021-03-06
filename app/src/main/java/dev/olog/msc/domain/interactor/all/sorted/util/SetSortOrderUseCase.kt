package dev.olog.msc.domain.interactor.all.sorted.util

import dev.olog.msc.domain.entity.SortType
import dev.olog.msc.domain.executors.IoScheduler
import dev.olog.msc.domain.gateway.prefs.AppPreferencesGateway
import dev.olog.msc.domain.interactor.base.CompletableUseCaseWithParam
import dev.olog.msc.utils.MediaId
import dev.olog.msc.utils.MediaIdCategory
import io.reactivex.Completable
import javax.inject.Inject

class SetSortOrderUseCase @Inject constructor(
        schedulers: IoScheduler,
        private val gateway: AppPreferencesGateway

) : CompletableUseCaseWithParam<SetSortOrderRequestModel>(schedulers){

    override fun buildUseCaseObservable(param: SetSortOrderRequestModel): Completable {
        val category = param.mediaId.category
        return when (category){
            MediaIdCategory.FOLDERS -> gateway.setFolderSortOrder(param.sortType)
            MediaIdCategory.PLAYLISTS,
            MediaIdCategory.PODCASTS_PLAYLIST -> gateway.setPlaylistSortOrder(param.sortType)
            MediaIdCategory.ALBUMS,
            MediaIdCategory.PODCASTS_ALBUMS -> gateway.setAlbumSortOrder(param.sortType)
            MediaIdCategory.ARTISTS,
            MediaIdCategory.PODCASTS_ARTISTS -> gateway.setArtistSortOrder(param.sortType)
            MediaIdCategory.GENRES -> gateway.setGenreSortOrder(param.sortType)
            else -> throw IllegalArgumentException("invalid param $param")
        }
    }
}

class SetSortOrderRequestModel(
        val mediaId: MediaId,
        val sortType: SortType
)