package com.example.projetrp.ui.parametre

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projetrp.R
import com.example.projetrp.databinding.FragmentParametresBinding
import com.example.projetrp.model.Constants
import com.example.projetrp.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_parametres.*

class ParametresFragment : Fragment() {

    private lateinit var parametresViewModel: ParametresViewModel
    private lateinit var binding: FragmentParametresBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parametresViewModel = ViewModelProvider(this).get(ParametresViewModel::class.java)
        binding = FragmentParametresBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = parametresViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parametresViewModel.result.observe(viewLifecycleOwner, Observer {
            val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
            if(parametresViewModel.good) {
                dialogBuilder.apply {
                    setTitle("Resultat d'évaluation")
                    setMessage(parametresViewModel.typeExamen.value + "\n" + it.toString())
                    setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
                        findNavController().navigate(R.id.nav_apropos)
                    })
                    setNegativeButton(
                        "Retour", DialogInterface.OnClickListener { dialog, which -> })
                    show()
                }
            }else{
                dialogBuilder.apply {
                    setTitle("Erreur")
                    setMessage("Type d'examen inconnue!")
                    setIcon(R.drawable.ic_baseline_warning_24)
                    setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->  })
                    show()
                }
            }
        })
        parametresViewModel.typeExamen.observe(viewLifecycleOwner, Observer {
            autoCompleteTextView.error = null
        })
        btCalculer.setOnClickListener {
            Utils.closeKeyboard(requireActivity())
            Utils.checkInput(binding.etHauteTension)
            Utils.checkInput(binding.etCharge)
            Utils.checkInput(binding.etDistanceFoyerPatient)
            Utils.checkInput(binding.etLongueurChamp)
            Utils.checkInput(binding.etLargeurChamp)
            Utils.checkInput(binding.autoCompleteTextView)
            val mPref = Utils.getPreference(requireContext())
            Constants.a = mPref.getString(
                Constants.COEFF_A, "10.0")!!.toDouble()
            Constants.b = mPref.getString(
                Constants.COEFF_B, "10.0")!!.toDouble()
            Constants.c = mPref.getString(
                Constants.COEFF_C, "10.0")!!.toDouble()
            parametresViewModel.calculerDE()
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.type_examen_item,
            Constants.typeExamens)
        autoCompleteTextView.setAdapter(adapter)
    }


}