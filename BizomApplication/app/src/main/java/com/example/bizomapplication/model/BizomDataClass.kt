package com.example.bizomapplication.model
/**
 * @author Appolla Sreedhar
 * @since (13/03/2022)
 * */
import com.google.gson.annotations.SerializedName

data class BizomDataClass(

    @SerializedName("Result") var Result: Boolean? = null,
    @SerializedName("Reason") var Reason: String? = null,
    @SerializedName("Claims") var Claims: ArrayList<Claims> = arrayListOf()

)


data class Claims(

    @SerializedName("Claimtype") var Claimtype: Claimtype? = Claimtype(),
    @SerializedName("Claimtypedetail") var Claimtypedetail: ArrayList<Claimtypedetail> = arrayListOf()

)

data class Claimtype(

    @SerializedName("name") var name: String? = null,
    @SerializedName("id") var id: String? = null

)

data class Claimfieldoption(

    @SerializedName("name") var name: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("belongsto") var belongsto: String? = null,
    @SerializedName("hasmany") var hasmany: String? = null,
    @SerializedName("claimfield_id") var claimfieldId: String? = null,
    @SerializedName("id") var id: String? = null

)

data class Claimfield(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("required") var required: String? = null,
    @SerializedName("isdependant") var isdependant: String? = null,
    @SerializedName("created") var created: String? = null,
    @SerializedName("modified") var modified: String? = null,
    @SerializedName("Claimfieldoption") var Claimfieldoption: ArrayList<Claimfieldoption> = arrayListOf()

)

data class Claimtypedetail(

    @SerializedName("claimfield_id") var claimfieldId: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("claimtype_id") var claimtypeId: String? = null,
    @SerializedName("Claimfield") var Claimfield: Claimfield? = Claimfield()

)

data class AddedClaims(
    var claimType: String = "",
    var claimDate: String = "",
    var expenseAmount: String = "",
    var remark: String ="",
    var kms : String = "",
    var rate : String = ""
)

data class IsAdded(
    var addedItemPosition: Int = -1,
    var isAdded: Boolean = false,
    var hint: String = "",
    var text : String = ""
)
