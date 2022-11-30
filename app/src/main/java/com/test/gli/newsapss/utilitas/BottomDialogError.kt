package com.test.gli.newsapss.utilitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.gli.newsapss.R
import com.test.gli.newsapss.databinding.ActivityBottomDialogErrorBinding

class BottomDialogError (val errCode:String?, val message:String?, val bodyResponse:String?):
    BottomSheetDialogFragment() {

    companion object{

        fun init(errCode: String?,message: String?,bodyResponse: String?,fm: FragmentManager?):BottomDialogError{
            BottomDialogError(errCode, message, bodyResponse).let {
                it.showNow(fm!!, "err_msg")
                it.setOnClickListener(object : BottomDialogErrorListener{
                    override fun onCloseDialog() {
                        it.dismiss()
                    }
                })
                return it
            }
        }
    }

    private lateinit var binding:ActivityBottomDialogErrorBinding
    private var listener:BottomDialogErrorListener?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_bottom_dialog_error, container, false)
        return binding.root
    }

    fun setOnClickListener(listener: BottomDialogErrorListener){
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler         = this
        binding.errCode         = errCode
        binding.errMessage      = message
        binding.bodyResponse    = bodyResponse
    }

    fun closeDialog(view: View){
        listener?.onCloseDialog()
    }

    interface BottomDialogErrorListener{
        fun onCloseDialog()
    }
}