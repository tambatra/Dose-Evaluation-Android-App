package com.example.projetrp.ui.loi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetrp.R
import com.example.projetrp.model.Loi
import com.example.projetrp.model.LoiTexts
import kotlinx.android.synthetic.main.fragment_loidecrets.*

class LoiFragment : Fragment() {
    private lateinit var loiAdapter: LoiRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loidecrets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            loiAdapter = LoiRecyclerAdapter(object : CellClickListener{
                override fun onCellClicked(loi: Loi) {
                    val bundle = bundleOf("title" to loi.title, "subtitle" to loi.subtitle, "body" to loi.body)
                    findNavController().navigate(R.id.action_nav_loidecrets_to_nav_loi_detail, bundle)
                }
            })
            val lois = LoiTexts.lois
            loiAdapter.submitList(lois)
            adapter = loiAdapter
        }
    }
}