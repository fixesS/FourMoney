package com.fixess.fourmoney.enums

import androidx.compose.ui.graphics.Color

enum class Type(val id:Int,val tag: String,val color:Color) {
    PETROL(0,"Бензин",Color(1,1,1)),
    FOOD(1,"Продукты",Color(0,0,0)),
    GAMES(2,"Игры",Color(0,0,0)),
    CLOTHES(3,"Одежда",Color(0,0,0)),
    SUBSCRIPTIONS(4,"Подписки",Color(0,0,0)),
    RENT(5,"Аренда",Color(0,0,0)),
    HEALTH(6,"Здоровье",Color(0,0,0)),
    TRAVEL(7,"Путешествия",Color(0,0,0)),
    OTHER(-1,"Другое",Color(0,0,0)),
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