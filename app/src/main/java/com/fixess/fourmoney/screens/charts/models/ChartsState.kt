package com.fixess.fourmoney.screens.charts.models

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.enums.Type

enum class ChartsSubState{
    CircleChart,Purchase,Category
}
enum class ChartsViewState(val id: Int,val tag: String){
    Slices(0,"Покупки"),
    Categories(1,"Категории");

    companion object{
        fun getListOfViewStates():List<String>{
            val list : MutableList<String> = mutableListOf()
            ChartsViewState.values().map { list.add(it.tag) }
            return list
        }
        fun getById(id :Int ): ChartsViewState {
            var state : ChartsViewState = ChartsViewState.Categories
            ChartsViewState.values().map {
                if(it.id == id){
                    state = it
                }
            }
            return state
        }
    }


}

data class ChartsState(
    var selectedSlice : PieChartSlice = PieChartSlice(),
    var selectedCategory: Category = Category(),
    val chartsViewState: ChartsViewState = ChartsViewState.Slices,
    val chartsSubState : ChartsSubState = ChartsSubState.CircleChart,
    var listOfSlices : MutableList<PieChartSlice> = ArrayList(),
    var listOfCategories : MutableList<Category> = ArrayList()
)