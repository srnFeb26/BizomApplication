package com.example.bizomapplication.viewmodel
/**
 * @author Appolla Sreedhar
 * @since (13/03/2022)
 * */
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.bizomapplication.R
import com.example.bizomapplication.model.AddedClaims
import com.example.bizomapplication.model.BizomDataClass
import com.example.bizomapplication.model.IsAdded
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @see BizomViewModel is ViewModel for this application
 * this is responsible to transfer data between pojo class and view
 */
class BizomViewModel(application: Application) : AndroidViewModel(application) {
    var spinnerSelected : MutableLiveData<Int> = MutableLiveData()
    var addedListLiveData : MutableLiveData<ArrayList<AddedClaims>> = MutableLiveData()
    var isDataChanged: MutableLiveData<IsAdded> = MutableLiveData()

    var addedClaimsList : ArrayList<AddedClaims> = arrayListOf()
    var data : BizomDataClass = BizomDataClass()

    /**
     * This method is called from BizomActivity
     * @See BizomActivity.onCreate
     * this method generate the data from json file in raw folder
     * and the data saved in BizomViewModel
     * @see BizomViewModel.data
     * @param context    is required to access raw folder for json file
     */
    fun setupData(context: Context){
        var gson = Gson()
        val text = context.resources.openRawResource(R.raw.claims_json)
            .bufferedReader().use { it.readText() }
        val listPersonType = object : TypeToken<BizomDataClass>() {}.type
        data = gson.fromJson(text, listPersonType)
    }


}