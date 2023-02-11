package com.fixess.fourmoney.enums

import androidx.compose.ui.graphics.Color
import com.fixess.fourmoney.R

enum class Type(val id:Int,val tag: String,val color:Color,val icon: Int) {
    PETROL(0,"Бензин",Color(128, 64, 0), R.drawable.petrol),
    FOOD(1,"Продукты",Color(115, 230, 0),R.drawable.food),
    GAMES(2,"Игры",Color(26, 83, 255),R.drawable.games),
    CLOTHES(3,"Одежда",Color(153, 102, 255),R.drawable.clothes),
    SUBSCRIPTIONS(4,"Подписки",Color(209, 71, 163),R.drawable.subs),
    RENT(5,"Аренда",Color(255, 255, 102),R.drawable.rent),
    HEALTH(6,"Здоровье",Color(0, 255, 0),R.drawable.health),
    TRAVEL(7,"Путешествия",Color(51, 204, 204),R.drawable.travel),
    OTHER(-1,"Другое",Color(102, 153, 153),R.drawable.other),
    UNKNOWN(-2,"Неизвестно",Color(0,0,0),R.drawable.unknown);

    companion object {
        fun findByTag(tag :String ): Type {
            var type : Type = UNKNOWN
            values().map {
                if(it.tag == tag){
                    type = it
                }
            }
            return type
        }
        fun getById(id :Int ): Type { // gets type by id value in type object
            var type : Type = UNKNOWN
            values().forEach{ indexedType ->
                if(indexedType.id == id){
                    type = indexedType
                }
            }
            return type
        }
        fun getByIndex(index :Int ): Type {// gets type by index in enum
            var type : Type = UNKNOWN
            values().forEachIndexed{ indexOfType, indexedType ->
                if(indexOfType == index){
                    type = indexedType
                }
            }
            return type
        }
        fun defaultTag():Type{
            return UNKNOWN
        }
        fun getListOfTypes():List<String>{
            val list : MutableList<String> = mutableListOf()
            values().map { if(it != UNKNOWN) list.add(it.tag) }
            return list
        }
    }

}