package com.example.oficinatiao01.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pessoa")
data class Pessoa(
    @PrimaryKey
    val cpf: String,
    val nome: String,
    val ddd: String,
    val telefone: String
)