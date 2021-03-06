package dev.olog.msc.floating.window.service

import android.content.Context
import androidx.lifecycle.Lifecycle
import dev.olog.msc.R

class VideoContent(
        lifecycle: Lifecycle,
        context: Context

) : WebViewContent(lifecycle, context, R.layout.content_web_view) {

    override fun getUrl(item: String): String {
        return "https://www.youtube.com/search?q=$item"
    }

}