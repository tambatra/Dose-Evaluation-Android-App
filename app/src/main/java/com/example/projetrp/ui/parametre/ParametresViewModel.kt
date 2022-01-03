package com.example.projetrp.ui.parametre

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetrp.model.EvaluationDose
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ParametresViewModel : ViewModel(), Observable {

    @Bindable
    val hauteTension = MutableLiveData<String>()

    @Bindable
    val charge = MutableLiveData<String>()

    @Bindable
    val distanceFoyerPatient = MutableLiveData<String>()

    @Bindable
    val largeurChamp = MutableLiveData<String>()

    @Bindable
    val longueurChamp = MutableLiveData<String>()

    @Bindable
    val filtrationTotal = MutableLiveData<String>()

    @Bindable
    val typeExamen = MutableLiveData<String>()


    var progressBarVisible = MutableLiveData(false)

    var good = false
    var result = MutableLiveData<String>()

    fun calculerDE() {
        val evaluationDose = EvaluationDose(typeExamen, hauteTension, charge,
        distanceFoyerPatient, largeurChamp, longueurChamp)
        if (evaluationDose.isInputNotNull()) {
            viewModelScope.launch {
                if(!evaluationDose.isTypeExamenOK()) {
                    progressBarVisible.value = false
                    good = false
                    result.value = "None"
                    return@launch
                }
                progressBarVisible.value = true
                delay(1000)
                val de = evaluationDose.calculerDE()
                val comparaison = evaluationDose.comparerDEAvecReference(de)
                good = true
                result.value = "La valeur de DE = $de mGy\n$comparaison"
                progressBarVisible.value = false
            }
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

}