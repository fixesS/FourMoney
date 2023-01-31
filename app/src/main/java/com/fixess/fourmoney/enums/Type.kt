package com.fixess.fourmoney.enums

import androidx.compose.ui.graphics.Color

enum class Type(val id:Int,val tag: String,val color:Color) {
    PETROL(0,"Бензин",Color(128, 64, 0)),
    FOOD(1,"Продукты",Color(115, 230, 0)),
    GAMES(2,"Игры",Color(26, 83, 255)),
    CLOTHES(3,"Одежда",Color(153, 102, 255)),
    SUBSCRIPTIONS(4,"Подписки",Color(209, 71, 163)),
    RENT(5,"Аренда",Color(255, 255, 102)),
    HEALTH(6,"Здоровье",Color(0, 255, 0)),
    TRAVEL(7,"Путешествия",Color(51, 204, 204)),
    OTHER(-1,"Другое",Color(102, 153, 153)),
    UNKNOWN(-2,"Неизвестно",Color(0,0,0));

    companion object {
        fun findByTag(tag :String ): Type {
            var type : Type = OTHER
            values().map {
                if(it.tag == tag){
                    type = it
                }
            }
            return type
        }
        fun findById(id :Int ): Type {
            var type : Type = OTHER
            values().map {
                if(it.id == id){
                    type = it
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