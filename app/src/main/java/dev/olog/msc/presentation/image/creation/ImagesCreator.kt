package dev.olog.msc.presentation.image.creation

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import androidx.core.content.systemService
import dev.olog.msc.Permissions
import dev.olog.msc.dagger.qualifier.ApplicationContext
import dev.olog.msc.dagger.qualifier.ProcessLifecycle
import dev.olog.msc.domain.interactor.all.newrequest.GetAllFoldersNewRequestUseCase
import dev.olog.msc.domain.interactor.all.newrequest.GetAllGenresNewRequestUseCase
import dev.olog.msc.domain.interactor.all.newrequest.GetAllPlaylistsNewRequestUseCase
import dev.olog.msc.domain.interactor.prefs.AppPreferencesUseCase
import dev.olog.msc.isLowMemoryDevice
import dev.olog.msc.utils.k.extension.unsubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesCreator @Inject constructor(
        @ApplicationContext private val context: Context,
        @ProcessLifecycle private val lifecycle: Lifecycle,
        private val getAllFoldersUseCase: GetAllFoldersNewRequestUseCase,
        private val getAllPlaylistsUseCase: GetAllPlaylistsNewRequestUseCase,
        private val getAllGenresUseCase: GetAllGenresNewRequestUseCase,

        private val folderImagesCreator: FolderImagesCreator,
        private val playlistImagesCreator: PlaylistImagesCreator,
        private val genreImagesCreator: GenreImagesCreator,
        private val appPreferencesUseCase: AppPreferencesUseCase

) : DefaultLifecycleObserver {

    private val subscriptions = CompositeDisposable()
    private var folderDisposable : Disposable? = null
    private var playlistDisposable : Disposable? = null
    private var genreDisposable : Disposable? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        if (Permissions.canReadStorage(context)){
            execute()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        unsubscribe()
    }

    private fun unsubscribe(){
        folderDisposable.unsubscribe()
        playlistDisposable.unsubscribe()
        genreDisposable.unsubscribe()
        subscriptions.clear()
    }

    fun execute() {
        if (!Permissions.canReadStorage(context)){
            return
        }

        unsubscribe()

        if (isLowMemoryDevice(context)){
            return
        }

        Observables.combineLatest(
                getAllFoldersUseCase.execute().onErrorReturnItem(listOf()),
                appPreferencesUseCase.observeAutoCreateImages(), { folders, create ->
            if (create) folders else listOf()
        }).subscribe({
                    folderDisposable.unsubscribe()
                    folderDisposable = folderImagesCreator.execute()
                            .subscribe({}, Throwable::printStackTrace)
                }, Throwable::printStackTrace)
                .addTo(subscriptions)

        Observables.combineLatest(
                getAllPlaylistsUseCase.execute().onErrorReturnItem(listOf()),
                appPreferencesUseCase.observeAutoCreateImages(), { playlists, create ->
            if (create) playlists else listOf()
        }).subscribe({
                    playlistDisposable.unsubscribe()
                    playlistDisposable = playlistImagesCreator.execute(it)
                            .subscribe({}, Throwable::printStackTrace)
                }, Throwable::printStackTrace)
                .addTo(subscriptions)

        Observables.combineLatest(
                getAllGenresUseCase.execute().onErrorReturnItem(listOf()),
                appPreferencesUseCase.observeAutoCreateImages(), { genres, create ->
            if (create) genres else listOf()
        }).subscribe({
                    genreDisposable.unsubscribe()
                    genreDisposable = genreImagesCreator.execute(it)
                            .subscribe({}, Throwable::printStackTrace)
                }, Throwable::printStackTrace)
                .addTo(subscriptions)
    }

}