package dev.olog.presentation.dialog_entry

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.olog.presentation.R
import dev.olog.presentation._base.BaseBottomSheetDialogFragment
import dev.olog.presentation.utils.extension.removeLightStatusBar
import dev.olog.presentation.utils.extension.setLightStatusBar
import dev.olog.presentation.utils.extension.subscribe
import dev.olog.presentation.utils.extension.withArguments
import kotlinx.android.synthetic.main.dialog_item.view.*
import javax.inject.Inject

class DialogItemFragment : BaseBottomSheetDialogFragment(), DialogItemView {

    companion object {
        const val TAG = "DialogItemFragment"
        const val ARGUMENTS_MEDIA_ID = "$TAG.arguments.media_id"
        const val ARGUMENTS_LIST_POSITION = "$TAG.arguments.list_position"

        fun newInstance(mediaId: String, position: Int): DialogItemFragment {
            return DialogItemFragment().withArguments(
                    ARGUMENTS_MEDIA_ID to mediaId,
                    ARGUMENTS_LIST_POSITION to position)
        }
    }

    @Inject lateinit var viewModel: DialogItemViewModel
    @Inject lateinit var adapter: DialogItemAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.window.removeLightStatusBar()
    }

    override fun onViewBound(view: View, savedInstanceState: Bundle?) {
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = adapter

        viewModel.data.subscribe(this, adapter::updateDataSet)
    }

    override fun onDestroyView() {
        activity!!.window.setLightStatusBar()
        super.onDestroyView()
    }

    override fun provideView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_item, container, false)
    }

}