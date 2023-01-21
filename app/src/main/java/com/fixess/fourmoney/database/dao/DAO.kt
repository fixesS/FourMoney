package com.fixess.fourmoney.database.dao

import androidx.room.*
import com.fixess.fourmoney.database.entities.PurchaseEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface DAO {
    @Query("SELECT * FROM ${PurchaseEntity.TABLE_NAME}")
    fun getAllPurchases(): Single<List<PurchaseEntity>>

    @Query("SELECT * FROM ${PurchaseEntity.TABLE_NAME} LIMIT :numberOfPurchases")
    fun getFirstPurchases(numberOfPurchases: Int): Single<List<PurchaseEntity>>

    @Insert(entity = PurchaseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addPurchase(purchaseEntity: PurchaseEntity) : Completable

    @Delete( entity = PurchaseEntity::class)
    fun deletePurchases(purchaseEntity: PurchaseEntity) : Completable

}