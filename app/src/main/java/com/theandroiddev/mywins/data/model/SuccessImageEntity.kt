package com.theandroiddev.mywins.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theandroiddev.mywins.domain.service.success_images.SuccessImagesServiceModel
import com.theandroiddev.mywins.local.model.LocalSuccessImage

data class SuccessImageEntity(
        var id: Long?,
        var successId: Long?,
        var imagePath: String
)

fun SuccessImageEntity.toLocal() = LocalSuccessImage(
        this.id,
        this.successId,
        this.imagePath
)

fun SuccessImageEntity.toServiceModel() = SuccessImagesServiceModel(
        this.id,
        this.successId,
        this.imagePath
)