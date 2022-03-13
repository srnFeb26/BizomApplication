package com.example.bizomapplication.adapters
/**
 * @author Appolla Sreedhar
 * @since (13/03/2022)
 * */
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizomapplication.R
import com.example.bizomapplication.globals.GlobalUtils
import com.example.bizomapplication.model.AddedClaims
import com.example.bizomapplication.model.Claimtypedetail
import com.example.bizomapplication.model.IsAdded
import com.example.bizomapplication.view.activities.BizomActivity
import com.example.bizomapplication.viewmodel.BizomViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.Exception
/**
 * This adapter is calling from
 * @See BizomFragment
 * This Adapter is responsible to show all form types coming from server
 * It contains the sub recycler view with adapter
 * @see ButtonViewHolder.recyclerView
 * @See AddedClaimsAdapter
 * User can add the claims by using the button Add Claim Item contains ViewHolder type
 * @see ButtonViewHolder.button
 * */
class ClaimsListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var viewModel: BizomViewModel

    private var list: ArrayList<Claimtypedetail> = arrayListOf()
    private var addedList: ArrayList<AddedClaims> = arrayListOf()
    private var dataFilled: ArrayList<Boolean> = arrayListOf()

    private var addedClaims: AddedClaims = AddedClaims()
    private  var addedClaimsAdapter : AddedClaimsAdapter? = null
    private var claimType: String = ""

    constructor(
        context: Context,
        list: ArrayList<Claimtypedetail>,
        addedList: ArrayList<AddedClaims>,
        viewModel: BizomViewModel
    ) : this() {
        this.context = context
        this.list = list
        this.viewModel = viewModel
        this.addedList = addedList
        addedClaims.claimDate = GlobalUtils.getCurrentDate()
        addedClaims.claimType = claimType

        viewModel.isDataChanged.observe((context as BizomActivity)) {
            if (it.addedItemPosition != -1) {
                Log.e("Here :: ", it.text)
                Log.e("Here :: ", "" + it.addedItemPosition)
                dataFilled[it.addedItemPosition] = it.isAdded
                if (it.isAdded) {
                    if (it.hint.equals("Remark")) {
                        addedClaims.remark = it.text
                    }
                }
            }
        }

        viewModel.addedListLiveData.observe((context as BizomActivity)){
            this.addedList = it
            if (addedClaimsAdapter!=null){
                addedClaimsAdapter?.updateList(addedList)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        return if (viewType == 1) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_list_item_select, parent, false)
            ListEditViewHolder(view)
        } else if (viewType == 0) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.cell_edit_text, parent, false)
            EditViewHolder(view, viewModel)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.cell_button_layout, parent, false)
            ButtonViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < list.size) {
            if (list[position].Claimfield?.type.equals("DropDown")) {
                dataFilled[position] = true
                var arrayList: ArrayList<String> = arrayListOf()
                (holder as ListEditViewHolder).title.text = list[position].Claimfield?.label
                for (value in list[position].Claimfield?.Claimfieldoption!!) {
                    arrayList.add(value.name!!)
                }

                val adapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item, arrayList
                )
                var spinner: Spinner = (holder as ListEditViewHolder).spinner
                spinner.adapter = adapter

            } else {
                (holder as EditViewHolder).layout.hint = list[position].Claimfield?.label
                var editText: TextInputEditText = holder.editText
                if (list[position].Claimfield?.type?.contains("Numeric") == true) {
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                }
                if (position == list.size-1){
                    editText.imeOptions = EditorInfo.IME_ACTION_DONE
                }
                editText.setText("")
            }
        } else {
            (holder as ButtonViewHolder).button.setOnClickListener {
                addedClaims.claimType = claimType
                addedClaims.claimDate = GlobalUtils.getCurrentDate()
                viewModel.addedClaimsList.add(addedClaims)
                Log.e("Claim Data ::",""+addedClaims)
                viewModel.addedListLiveData.postValue(viewModel.addedClaimsList)
            }

            var addedClaimsRecycler: RecyclerView = (holder as ButtonViewHolder).recyclerView
            addedClaimsRecycler.layoutManager = LinearLayoutManager(context)
            addedClaimsAdapter = AddedClaimsAdapter(addedList)
            addedClaimsRecycler.adapter = addedClaimsAdapter
        }

    }

    /**
     * This ViewHolder class is user while {itemId = 0}
     * This contains the layout with editText
     * */
    class ListEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var spinner: Spinner = itemView.findViewById(R.id.spinner)
    }

    /**
     * This ViewHolder class is user while {itemId = 1}
     * This contains the layout with spinner and textview
     * */
    class EditViewHolder(itemView: View, viewModel: BizomViewModel) :
        RecyclerView.ViewHolder(itemView) {
        var layout: TextInputLayout = itemView.findViewById(R.id.text_input_layout)
        var editText: TextInputEditText = itemView.findViewById(R.id.edit_text)
        var viewModel: BizomViewModel = viewModel

        init {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    var hint: String = editText.hint.toString()
                    var position: Int = adapterPosition
                    var status: Boolean = false
                    var text: String = try {
                        status = true
                        p0.toString()
                    } catch (e: Exception) {
                        status = false
                        ""
                    }
                    var isAdded = IsAdded(position, status, hint, text)
                    viewModel.isDataChanged.postValue(isAdded)
                }

            })
        }


    }

    /**
     * This ViewHolder class is user while {itemId = 2}
     * This contains the button and recyclerview
     * button is responsible to submit the claims
     * recyclerview is responsible to show the all claims added list
     * */
    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var button: AppCompatButton = itemView.findViewById(R.id.add_claim)
        var recyclerView : RecyclerView = itemView.findViewById(R.id.added_claims_recycler)
    }


    /**
     * This method based on the conditions pass the values 0 or 1 or 2 to OnCreateViewHolder
     * This itemViewType return value is used for decide the layout
     * */
    override fun getItemViewType(position: Int): Int {
        try {
            if (list[position].Claimfield?.type.equals("DropDown")) {
                return 1
            }
        } catch (e: Exception) {
            Log.e("Exception :: ", "Exception")
            return 2
        }

        return 0
    }


    override fun getItemCount(): Int {
        for (index in list.withIndex()) {
            dataFilled.add(false)
        }
        return list.size + 1
    }

    /**
     * This method is responsible to update recycler view with new list
     * This method is called while user changed the data with main spinner
     * @param newList is Based on main spinner this will change
     * this method refresh the entire adapter with new updated list
     * */
    fun updateList(newList: ArrayList<Claimtypedetail>) {
        this.list = newList
        addedClaims = AddedClaims("", "", "")
        notifyDataSetChanged()
    }

    /**
    * This method is responsible to give claim type to adapter
    * @param claimType is Based on main spinner this will change
    * After button click @param claimType will send to added data
    * */
    fun claimType(claimType: String) {
        this.claimType = claimType
    }

}