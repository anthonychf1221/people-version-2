package com.anthonychaufrias.people.ui.person

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.anthonychaufrias.people.R
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthonychaufrias.people.core.Values
import com.anthonychaufrias.people.model.Person
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
        fabNewPerson.setOnClickListener { view ->
            val intent = Intent(this, PersonSaveActivity::class.java)
            intent.putExtra(PersonSaveActivity.ARG_ITEM, Person(0, "", "", 0, ""))
            intent.putExtra(PersonSaveActivity.ARG_ACTION, Values.INSERT)
            startActivityForResult(intent, 1)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val person: Person = data.getSerializableExtra(PersonSaveActivity.ARG_ITEM) as Person
            val action: Int = data.getIntExtra(PersonSaveActivity.ARG_ACTION, Values.INSERT) as Int
            viewModel.refreshList(person, action)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}