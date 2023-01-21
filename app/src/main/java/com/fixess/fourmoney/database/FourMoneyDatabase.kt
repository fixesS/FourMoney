package com.fixess.fourmoney.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fixess.fourmoney.database.dao.DAO
import com.fixess.fourmoney.database.entities.PurchaseEntity

@Database(entities = [PurchaseEntity::class], version = 1, exportSchema = true)
abstract class FourMoneyDatabase : RoomDatabase() {

    abstract fun DAO(): DAO
}