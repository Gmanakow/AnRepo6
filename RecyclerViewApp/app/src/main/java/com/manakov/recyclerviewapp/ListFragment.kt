package com.manakov.recyclerviewapp

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ListFragment : Fragment(R.layout.fragment_list) {

    companion object {
        const val LOG_TAG = "ListFragment"

        fun newInstance() = ListFragment()
    }

    var list = mutableListOf<DataItem>()

    private lateinit var fragmentActionListener: FragmentActionListener

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataItemAdapter: DataItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        dataItemAdapter = DataItemAdapter().apply {
            onClick = { dataItem: DataItem, position: Int ->
                fragmentActionListener.onSelectItemAction(dataItem, position)
            }
            onLongClick = { position: Int ->
                AlertDialog.Builder(fragmentActionListener as Context).apply {
                    setMessage(context.getString(R.string.delete_item_confirmation))
                    setNegativeButton("no") { _: DialogInterface, _: Int -> }
                    setPositiveButton("yes") { _: DialogInterface, _: Int ->
                        list.removeAt(position)
                        refresh()
                    }
                }.create().show()
            }
            dataItemList = list
        }

        recyclerView.apply {
            adapter = dataItemAdapter
            addItemDecoration(
                DataItemDecoration()
            )
            isNestedScrollingEnabled = false
        }

        view.findViewById<Button>(R.id.searchButton).setOnClickListener {
            var text = view.findViewById<EditText>(R.id.searchEditTextView).text.toString()

            list.forEachIndexed { _, dataItem ->
                dataItem.visibility = true
                if (!dataItem.name.contains(text, true) && !dataItem.surname.contains(text, true)) {
                    dataItem.visibility = false
                }
            }

            refresh()
            fragmentActionListener.onChangeSearchStatus(true)
        }
    }

    fun refresh() {
        val diffUtilCallBack = DataItemDiffUtilCallBack(dataItemAdapter.dataItemList, list)
        var productDiffResult = DiffUtil.calculateDiff(diffUtilCallBack)

        dataItemAdapter.dataItemList = list
        productDiffResult.dispatchUpdatesTo(dataItemAdapter)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentActionListener) {
            context.also { fragmentActionListener = it }
        } else {
            throw IllegalArgumentException("context must implement FragmentActionListener interface")
        }

        repeat(50) {
            list.add(
                DataItem(
                    "dog",
                    (it * 2).toString(),
                    UUID.randomUUID().toString(),
                    getString(R.string.loremPicsumLinkPart1) + it + getString(R.string.loremPicsumLinkPart2),
                    true
                )
            )
            list.add(
                DataItem(
                    "cat",
                    (it * 2 + 1).toString(),
                    UUID.randomUUID().toString(),
                    getString(R.string.loremPicsumLinkPart1) + it + getString(R.string.loremPicsumLinkPart2),
                    true
                )
            )
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}