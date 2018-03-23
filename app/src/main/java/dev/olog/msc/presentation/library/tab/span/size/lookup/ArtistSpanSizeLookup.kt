package dev.olog.msc.presentation.library.tab.span.size.lookup

import android.content.Context
import dev.olog.msc.R
import dev.olog.msc.presentation.base.adapter.AbsAdapter
import dev.olog.msc.presentation.model.DisplayableItem
import dev.olog.msc.utils.k.extension.configuration

class ArtistSpanSizeLookup(
        context: Context,
        private val isPortrait: Boolean,
        private val adapter: AbsAdapter<DisplayableItem>

) : AbsSpanSizeLookup() {

    private val smallestWidthDip = context.configuration.smallestScreenWidthDp
    private val isBigTablet = smallestWidthDip >= 720

    override fun getSpanSize(position: Int): Int {
        val itemType = adapter.elementAt(position).type
        when (itemType){
            R.layout.item_tab_header,
            R.layout.item_tab_last_played_artist_horizontal_list,
            R.layout.item_tab_last_played_album_horizontal_list -> return spanCount
        }

        val span = if (isPortrait) spanCount / 3 else spanCount / 4

        if (isBigTablet) {
            return span + 1
        }

        return span
    }
}