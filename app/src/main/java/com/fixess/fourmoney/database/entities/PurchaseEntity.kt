package com.fixess.fourmoney.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.database.entities.PurchaseEntity.Companion.TABLE_NAME
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity(tableName = TABLE_NAME)
data class PurchaseEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("purchaseId")
    val purchaseId: Long,
    @ColumnInfo("type")
    val typeId: Int?,
    @ColumnInfo("money")
    val money: Float?,
    @ColumnInfo("timestamp")
    val timestamp: String?

    ){
    companion object{
        const val TABLE_NAME = "purchases_table"
    }
}