package com.fixess.fourmoney.tools

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.enums.Type

class ListOfSlicesToListOfCategoriesConverter {
    private var listOfCategories : MutableList<Category> = ArrayList()
    fun convert(list: MutableList<PieChartSlice>): MutableList<Category>{
        for(type in Type.values()){
            if(isTypeInList(type.id,list)){
                listOfCategories.add(
                    Category(
                        type = type,
                        color = type.color,
                        sumOfWeights = getSumOfWeightsOfSlicesOfType(type.id,list)
                    )
                )
            }
        }
        return listOfCategories
    }
    private fun isTypeInList(typeId: Int,list:List<PieChartSlice>): Boolean{
        var result: Boolean = false
        for(slice in list){
            if(slice.type.id == typeId){
                result = true
            }
        }
        return result
    }
    private fun getSumOfWeightsOfSlicesOfType(typeId: Int,list:List<PieChartSlice>):Float{
        var result : Float = 0f
        for(slice in list){
            if(slice.type.id== typeId){
                result+=slice.weight
            }
        }
        return result
    }
}