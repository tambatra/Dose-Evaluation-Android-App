package com.example.projetrp.model

import androidx.lifecycle.LiveData
import com.example.projetrp.utils.Utils
import kotlin.math.pow

class EvaluationDose (
    private val typeExamen: LiveData<String>,
    private val hauteTension: LiveData<String>,
    private val charge: LiveData<String>,
    private val distanceFoyer: LiveData<String>,
    private val largeurChamp: LiveData<String>,
    private val longueurChamp: LiveData<String>
) {

    fun isInputNotNull(): Boolean {
        return (!hauteTension.value.isNullOrEmpty() && !charge.value.isNullOrEmpty()
                && !distanceFoyer.value.isNullOrEmpty() && !largeurChamp.value.isNullOrEmpty()
                && !longueurChamp.value.isNullOrEmpty() && !typeExamen.value.isNullOrEmpty())
    }

    fun calculFiltration(ht: Double): Double {
        return 0.0008 * ht.pow(2) - (0.0806 * ht) + 4.0071
    }

    fun convChamp(long:Double, larg:Double):Double{
        return (2*(long*larg))/(long+larg)
    }

    fun calculRetrodiffusion(c: Double, cda: Double): Double {
        return 0.0031 * c + 0.04537 * cda + 1.1380
    }


    fun calculerDE(): Double {
        //coefficient (polynome d'ordre 2)de la courbe donnant exposition=f(kVp)
        //coefficient=[10**-7, 4*(10**-5), -0.001]
        val coefficient = arrayOf(Constants.a.pow(-7.0), 4.0* Constants.b.pow(-5.0), -0.001)
        //#calcul de Dair avec conversion en mGy (le facteur 8.16 no mamadika azy ho mGy)
        //Dair=((coefficient[0]*((ht)**2))+(coefficient[1]*(ht))+(coefficient[2]))*8.16
        val dAir = (coefficient[0]* toDouble(hauteTension).pow(2.0)
                + coefficient[1] * toDouble(hauteTension) + coefficient[2])*8.16
        //calcul de la dose a l'entree tenant compte de la variation de la distance et la retrodiffusion
        //De=Dair*mAs*((100/DFP)**2)*(1.3)
        // calcul RFD
        val champ_carre = convChamp(toDouble(longueurChamp), toDouble(largeurChamp))
        val filtration = calculFiltration(toDouble(hauteTension))
        val RFD = calculRetrodiffusion(champ_carre, filtration)
        val de = dAir*toDouble(charge)* (100 / toDouble(distanceFoyer)).pow(2.0) * RFD
        return Utils.round(de, 4)
    }

    fun comparerDEAvecReference(de: Double): String{
        val i = Constants.typeExamens.indexOf(typeExamen.value)
        return if(de<= Constants.typeExamensValue[i])
            "L'exposition du patient est optimisee"
        else "Les paramètres utilisées ne sont pas encore optimisées"
    }

    fun isTypeExamenOK():Boolean{
        return Constants.typeExamens.indexOf(typeExamen.value) != -1
    }

    private fun toDouble(input: LiveData<String>): Double {
        return input.value!!.toDouble()
    }
}