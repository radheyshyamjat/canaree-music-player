package dev.olog.presentation.fragment_tab

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Lazy
import dev.olog.presentation.R
import dev.olog.presentation._base.BaseFragment
import dev.olog.presentation.activity_main.TabViewPagerAdapter
import dev.olog.presentation.fragment_tab.di.TabViewModelModule
import dev.olog.presentation.utils.extension.subscribe
import dev.olog.presentation.utils.extension.withArguments
import kotlinx.android.synthetic.main.fragment_tab.view.*
import javax.inject.Inject

class TabFragment : BaseFragment() {

    companion object {

        private const val TAG = "TabFragment"
        const val ARGUMENTS_SOURCE = "$TAG.argument.dataSource"

        fun newInstance(source: Int): TabFragment {
            return TabFragment().withArguments(
                    ARGUMENTS_SOURCE to source)
        }
    }

    @Inject lateinit var adapter: TabFragmentAdapter
    @Inject lateinit var viewModel: TabFragmentViewModel
    @Inject @JvmField var source: Int = 0
    @Inject lateinit var spanSizeLookup: TabFragmentSpanSizeLookup
    private lateinit var layoutManager: GridLayoutManager

    @Inject lateinit var lastAlbumsAdapter : Lazy<TabLastPlayedAlbumsAdapter>
    @Inject lateinit var lastArtistsAdapter : Lazy<TabLastPlayedArtistsAdapter>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.observeData(source)
                .subscribe(this, adapter::updateDataSet)

        when (source){
            TabViewPagerAdapter.ALBUM -> {
                viewModel.observeData(TabViewModelModule.LAST_PLAYED_ALBUM)
                        .subscribe(this, {
                            lastAlbumsAdapter.get().updateDataSet(it)
                        })
            }
            TabViewPagerAdapter.ARTIST -> {
                viewModel.observeData(TabViewModelModule.LAST_PLAYED_ARTIST)
                        .subscribe(this, {
                            lastArtistsAdapter.get().updateDataSet(it)
                        })
            }
        }
    }

    @CallSuper
    override fun onViewBound(view: View, savedInstanceState: Bundle?) {
        layoutManager = GridLayoutManager(context, TabFragmentSpanSizeLookup.SPAN_COUNT)
        layoutManager.spanSizeLookup = spanSizeLookup
        view.list.layoutManager = layoutManager
        view.list.adapter = adapter
        view.list.setHasFixedSize(true)
    }

    override fun provideView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }
}