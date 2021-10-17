package com.manakov.recyclerviewapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity(), FragmentActionListener{
    companion object {
        const val LOG_TAG = "MainActivity"
    }

    private lateinit var listFragment : ListFragment
    private lateinit var dataFragment: DataFragment
    private var searchStatus = false
    private var inDataFragment = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().run {
            listFragment = ListFragment.newInstance()
            replace(R.id.listFragmentFrameLayout, listFragment)
            commit()
        }
    }

    override fun onSelectItemAction(dataItem: DataItem, index: Int) {
        supportFragmentManager.beginTransaction().run {
            dataFragment = DataFragment.newInstance(dataItem, index)
            add(dataFragment, DataFragment.BACK_STACK_TAG)
            addToBackStack(DataFragment.BACK_STACK_TAG)
            replace(R.id.listFragmentFrameLayout, dataFragment)
            inDataFragment = true
            commit()
        }
    }

    override fun onSaveStateAction(dataItem: DataItem, index: Int) {
        supportFragmentManager.beginTransaction().run {
            listFragment.list[index] = dataItem
            listFragment.refresh()
            commit()
        }
        supportFragmentManager.popBackStack()
        inDataFragment = false;
    }

    override fun onChangeSearchStatus(boolean: Boolean) {
        this.searchStatus = boolean
    }

    override fun onBackPressed() {
        if (searchStatus && !inDataFragment){
            listFragment.list.forEach{
                it.visibility = true
            }
            listFragment.refresh()
            searchStatus = false
        } else {
            super.onBackPressed()
            inDataFragment = false
        }
    }
}