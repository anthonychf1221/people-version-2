package com.anthonychaufrias.people.ui.view.person

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anthonychaufrias.people.R
import com.anthonychaufrias.people.core.Values
import com.anthonychaufrias.people.data.model.Person
import com.anthonychaufrias.people.data.model.PersonSaveResult
import com.anthonychaufrias.people.data.model.ValidationResult
import com.anthonychaufrias.people.ui.viewmodel.CountryViewModel
import com.anthonychaufrias.people.ui.viewmodel.PersonViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_person_save.*

class PersonSaveActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var personViewModel: PersonViewModel
    private lateinit var objPerson: Person
    private var action: Int = 0
    private var countryPosition: Int = 0
    companion object {
        @JvmStatic val ARG_ITEM: String = "objPerson"
        @JvmStatic val ARG_ACTION: String = "action"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_save)

        objPerson = intent.getSerializableExtra(ARG_ITEM) as Person
        action = intent.getIntExtra(ARG_ACTION, Values.INSERT) as Int
        setToolbar()

        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        personViewModel  = ViewModelProvider(this).get(PersonViewModel::class.java)
        initUI()
    }

    private fun initUI(){
        setFields()
        loadCountries(objPerson.countryID)
        btnSave.setOnClickListener { view ->
            saveData()
        }

        personViewModel.liveDataPeopleSave.observe(this, Observer { result ->
            showResult(result)
        })
    }

    private fun setToolbar(){
        val title:String = if( action == Values.INSERT )
            getString(R.string.tlt_person_new)
        else
            getString(R.string.tlt_person_edit)

        this.supportActionBar!!.setTitle(title)
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setFields(){
        if( action == Values.UPDATE ){
            txtFullName.setText(objPerson.fullName)
            txtDocumentID.setText(objPerson.documentID)
        }
    }

    private fun saveData(){
        val name: String = txtFullName.text.toString().trim()
        val documentId: String = txtDocumentID.text.toString().trim()
        val countryID: Int = countryViewModel.countryList[countryPosition].countryID
        val countryName: String = countryViewModel.countryList[countryPosition].countryName

        objPerson.fullName = name
        objPerson.documentID = documentId
        objPerson.countryID = countryID
        objPerson.countryName = countryName
        personViewModel.savePerson(objPerson, action)
    }

    private fun showResult(result: PersonSaveResult) {
        txtFullName.setError(null)
        txtDocumentID.setError(null)
        when(result){
            is PersonSaveResult.OK -> {
                finishWithSuccess(result.person)
            }
            is PersonSaveResult.OperationFailed -> {
                showFailedMessage(result.message, result.type)
            }
            is PersonSaveResult.InvalidInputs -> {
                showInputErrors(result.errors)
            }
        }
    }
    private fun finishWithSuccess(person: Person?){
        Snackbar.make(btnSave, getString(R.string.msgSuccess), Snackbar.LENGTH_LONG ).setAction("Action", null).show()

        val data = Intent()
        data.putExtra(ARG_ITEM, person)
        data.putExtra(ARG_ACTION, action)
        setResult(Activity.RESULT_OK, data)

        finish()
    }
    private fun showInputErrors(errors: List<ValidationResult> ){
        for (error in errors) {
            if(error == ValidationResult.INVALID_NAME){
                txtFullName.setError(getString(R.string.requiredField))
            }
            if(error == ValidationResult.INVALID_DOCUMENT_ID){
                txtDocumentID.setError(getString(R.string.docIDLen, Values.PERSON_DOCUMENT_LENGTH.toString()))
            }
        }
    }
    private fun showFailedMessage(message: String, error: ValidationResult){
        if(error == ValidationResult.INVALID_DOCUMENT_ID){
            txtDocumentID.setError(message)
        }
        if(error == ValidationResult.FAILURE){
            Snackbar.make(btnSave, getString(R.string.msgFailure), Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }

    private fun loadCountries(selectedId:Int){
        try{
            countryViewModel.loadCountryList(selectedId)
            countryViewModel.liveDataCountryList.observe(this, Observer { list ->
                val countryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryViewModel.countryNamesList)
                countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                with(spCountry){
                    adapter = countryAdapter
                    setSelection(countryViewModel.selectedIndex, false)
                    onItemSelectedListener = this@PersonSaveActivity
                    prompt = getString(R.string.lblSelectCountry)
                    gravity = Gravity.CENTER
                }
            })
        }
        catch (e: Exception) {
            print(e.message)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        countryPosition = position
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}