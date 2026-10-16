package com.example.oficinatiao01.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "veiculo")
data class Veiculo(
    @PrimaryKey
    val placa: String,
    val marca: String,
    val modelo: String,
    val ano: Int,
    val idTipoCombustivel: Int
)

