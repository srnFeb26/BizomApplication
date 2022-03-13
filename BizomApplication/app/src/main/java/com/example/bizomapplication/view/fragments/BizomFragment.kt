package com.example.bizomapplication.view.fragments
/**
 * @author Appolla Sreedhar
 * @since (13/03/2022)
 * */
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizomapplication.adapters.ClaimsListAdapter
import com.example.bizomapplication.databinding.FragmentBizomBinding
import com.example.bizomapplication.globals.GlobalUtils
import com.example.bizomapplication.model.*
import com.example.bizomapplication.view.activities.BizomActivity
import com.example.bizomapplication.viewmodel.BizomViewModel
/**
 * This fragment is added to the layout of activity
 * @see BizomActivity
 * This one getting the main data from
 * @See BizomViewModel.data
* */
class BizomFragment : Fragment() {
    private lateinit var binding : FragmentBizomBinding
    private lateinit var viewModel: BizomViewModel
    private var spinnerList : ArrayList<String> = arrayListOf()
    private var data: BizomDataClass = BizomDataClass()
    private var claims : ArrayList<Claims> = arrayListOf()
    private var claimTypeDetails : ArrayList<Claimtypedetail> = arrayListOf()
    private var addClaimList: ArrayList<AddedClaims> = arrayListOf()
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: ClaimsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBizomBinding.inflate(layoutInflater)
        viewModel = (activity as BizomActivity).viewModel
        binding.viewmodel = viewModel
        binding.spinnerList = spinnerList
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = ClaimsListAdapter(this.requireContext(), claimTypeDetails, addClaimList,viewModel)
        recyclerView.adapter = adapter
        binding.claimDate.text = GlobalUtils.getCurrentDate()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        data = viewModel.data
        claims = data.Claims
        for ((index, value) in claims.withIndex()){
            if (index == 0){
                claimTypeDetails = value.Claimtypedetail
                adapter.updateList(claimTypeDetails)
                adapter.claimType(value.Claimtype?.name!!)
            }
            var claimType : Claimtype? = value.Claimtype
            spinnerList.add(claimType?.name ?: "No Name")
        }
        binding.spinnerList = spinnerList

        viewModel.spinnerSelected.observe(this){
            setList(spinnerList[it])
        }


    }

    private fun setList(value : String){
        for (i in claims){
            if (i.Claimtype?.name.equals(value)){
                claimTypeDetails = i.Claimtypedetail
                adapter.updateList(claimTypeDetails)
                adapter.claimType(i.Claimtype?.name!!)
            }
        }
    }

}