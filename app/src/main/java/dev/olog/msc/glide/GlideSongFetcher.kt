package dev.olog.msc.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.data.DataFetcher
import dev.olog.msc.domain.gateway.LastFmGateway
import dev.olog.msc.presentation.model.DisplayableItem
import io.reactivex.Single
import java.io.InputStream

class GlideSongFetcher(
        model: DisplayableItem,
        private val lastFmGateway: LastFmGateway

) : BaseRxDataFetcher() {

    private val id = model.mediaId.resolveId

    override fun execute(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>): Single<String> {
        return lastFmGateway
                .getTrackImage(id)
                .map { it.get() }
    }

    override fun shouldFetch(): Single<Boolean> {
        return lastFmGateway.shouldFetchTrackImage(id)
    }


}