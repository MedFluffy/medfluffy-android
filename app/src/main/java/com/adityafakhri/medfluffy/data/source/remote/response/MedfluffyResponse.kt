package com.adityafakhri.medfluffy.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MedfluffyResponse(

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("accuration")
	val accuration: String? = null
)
