package com.fixess.fourmoney.database

import com.fixess.fourmoney.database.dao.DAO
import com.fixess.fourmoney.database.entities.PurchaseEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PurchaseRepository @Inject constructor(private val dao: DAO){

    fun getAllPurchases(): Single<List<PurchaseEntity>> {
        return dao.getAllPurchases()
    }

    fun addPurchase(purchaseEntity: PurchaseEntity) : Completable{
        return dao.addPurchase(purchaseEntity)
    }
}