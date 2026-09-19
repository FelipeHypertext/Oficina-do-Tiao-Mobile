package com.example.oficinatiao01.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oficinatiao01.model.Veiculo

class OficinaViewModel : ViewModel() {
    private val listaVeiculos = mutableListOf<Veiculo>()

    var contaCarro by mutableStateOf(0)
        private set
    var placaNaoEncontrada by mutableStateOf(false)
        private set

    var exibirCadastroVeiculo by mutableStateOf(false)
        private set

    var placa by mutableStateOf("")
        private set

    var marca by mutableStateOf("")
        private set

    var modelo by mutableStateOf("")
        private set

    var ano by mutableStateOf("")
        private set

    var idTipoCombustivel by mutableStateOf(1)
        private set

    fun atualizarPlaca(texto: String) {
        placa = texto
            .uppercase()
            .filter { it.isLetterOrDigit() }
            .take(7)
    }

    fun atualizarMarca(texto: String) {
        marca = texto
    }

    fun atualizarModelo(texto: String) {
        modelo = texto
    }

    fun atualizarAno(texto: String) {
        ano = texto.filter { it.isDigit() }.take(4)
    }

    fun atualizarTipoCombustivel(id: Int) {
        idTipoCombustivel = id
    }

    fun buscarPlaca() {
        if (placa.isBlank()) {
            return
        }

        val veiculoEncontrado = listaVeiculos.find {
            it.placa.uppercase() == placa.uppercase()
        }

        if (veiculoEncontrado == null) {
            placaNaoEncontrada = true
        } else {
            placaNaoEncontrada = false
            marca = veiculoEncontrado.marca
            modelo = veiculoEncontrado.modelo
            ano = veiculoEncontrado.ano.toString()
            idTipoCombustivel =
                veiculoEncontrado.idTipoCombustivel
            exibirCadastroVeiculo = true
        }
    }

    fun abrirCadastroVeiculo() {
        exibirCadastroVeiculo = true
    }

    fun incluirVeiculo() {
        val anoInt = ano.toIntOrNull() ?: return
        val veiculoAchado = listaVeiculos.find {
            it.placa.uppercase() == placa.uppercase()
        }

        if (veiculoAchado != null) {
            reiniciarTela()
            return
        }

         listaVeiculos.add(
            Veiculo(
                placa = placa,
                marca = marca,
                modelo = modelo,
                ano = anoInt,
                idTipoCombustivel = idTipoCombustivel
            )
        )

//        contaCarro++
        contaCarro = listaVeiculos.count()
        reiniciarTela()
    }

    fun reiniciarTela() {
        placa = ""
        marca = ""
        modelo = ""
        ano = ""

        placaNaoEncontrada = false
        exibirCadastroVeiculo = false

        idTipoCombustivel = 1
    }
}