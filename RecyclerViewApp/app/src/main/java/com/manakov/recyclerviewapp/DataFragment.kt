package com.manakov.recyclerviewapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class DataFragment : Fragment(R.layout.fragment_data) {

    companion object {
        const val BACK_STACK_TAG = "DataFragmentBackStackTag"
        const val LOG_TAG = "LOG_TAG"

        fun newInstance(dataItem: DataItem, index: Int) = DataFragment().also {
            it.arguments = Bundle().apply {
                putParcelable(ITEM_STRING, dataItem)
                putInt(INDEX_STRING, index)
            }
        }
    }

    private lateinit var fragmentActionListener: FragmentActionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActionListener) {
            context.also {
                fragmentActionListener = it
            }
        } else {
            throw IllegalArgumentException("context must implement FragmentActionListener interface")
        }
    }

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var imageLinkEditText: EditText
    private lateinit var imageView: ImageView

    private lateinit var item: DataItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var args = requireArguments()
        item = args.getParcelable(ITEM_STRING)!!

        nameEditText = view.findViewById(R.id.nameEditTextView)
        nameEditText.text = item.name.toEditable()

        surnameEditText = view.findViewById(R.id.surnameEditTextView)
        surnameEditText.text = item.surname.toEditable()

        numberEditText = view.findViewById(R.id.numberEditTextView)
        numberEditText.text = item.number.toEditable()

        imageLinkEditText = view.findViewById(R.id.imageLinkEditTextView)
        imageLinkEditText.text = item.link.toEditable()

        imageView = view.findViewById(R.id.imageView)

        refreshImage()

        view.findViewById<Button>(R.id.refresh).setOnClickListener {
            refreshImage()
        }

        view.findViewById<Button>(R.id.saveChangesButton).setOnClickListener {
            fragmentActionListener.onSaveStateAction(
                DataItem(
                    nameEditText.text.toString(),
                    surnameEditText.text.toString(),
                    numberEditText.text.toString(),
                    imageLinkEditText.text.toString(),
                    true
                ),
                args.getInt(INDEX_STRING)
            )
        }
    }

    private fun refreshImage() {
        try {
            Picasso.get().load(imageLinkEditText.text.toString())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView)
        } catch (e: Exception) {
            Toast.makeText(fragmentActionListener as Context, "err", Toast.LENGTH_SHORT).show()
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}