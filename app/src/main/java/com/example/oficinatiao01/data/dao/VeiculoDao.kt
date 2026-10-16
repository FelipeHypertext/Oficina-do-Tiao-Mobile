package com.example.oficinatiao01.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.oficinatiao01.model.Pessoa
import com.example.oficinatiao01.model.Veiculo

@Dao
interface VeiculoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserir(veiculo: Veiculo): Long

    @Query("SELECT * FROM veiculo WHERE placa = :placa LIMIT 1")
    suspend fun buscarPlaca(placa: String): Veiculo?

    @Query("SELECT COUNT(*) FROM veiculo")
    suspend fun contar(): Int

    @Update
    suspend fun atualizar(veiculo: Veiculo)
}