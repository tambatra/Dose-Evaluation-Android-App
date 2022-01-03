package com.example.projetrp.ui.loi

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projetrp.R
import com.example.projetrp.utils.Utils.fromHTML
import kotlinx.android.synthetic.main.fragment_loi_detail.view.*


class LoiDetailFragment : Fragment() {
    private lateinit var title: String
    private lateinit var subtitle: String
    private lateinit var body: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = requireArguments().getString("title").toString()
        subtitle = requireArguments().getString("subtitle").toString()
        body = requireArguments().getString("body").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loi_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.tv_loi_title.text = title
        view.tv_loi_body.text = fromHTML(body)
    }

}