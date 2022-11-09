package com.anthonychaufrias.people.ui.person

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.anthonychaufrias.people.R
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthonychaufrias.people.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.activity_people_list.*

class PeopleListActivity : AppCompatActivity() {
    private lateinit var viewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)
        setToolbar()
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        initUI()
    }

    private fun setToolbar() {
        this.supportActionBar?.title = getString(R.string.tlt_people_list)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initUI(){
        rvPeople.layoutManager = LinearLayoutManager(this)
        rvPeople.adapter = PersonListAdapter()
        loadPeople("")
        viewModel.liveDataPeopleList.observe(this, Observer { list ->
            (rvPeople.adapter as PersonListAdapter).setData(list)
            setMessageNoRecords(list.size)
        })
    }

    private fun loadPeople(filter: String) {
        try {
            viewModel.loadPeopleList(filter)
        } catch (e: Exception) {
            print(e.message)
        }
    }

    private fun setMessageNoRecords(size: Int) {
        lblNoRecords.visibility = if (size == 0) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}