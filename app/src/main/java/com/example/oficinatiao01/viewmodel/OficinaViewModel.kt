package com.example.oficinatiao01.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oficinatiao01.data.repository.VeiculoRepository
import com.example.oficinatiao01.model.Pessoa
import com.example.oficinatiao01.model.Veiculo
import kotlinx.coroutines.launch

class OficinaViewModel(
    private val repository: VeiculoRepository
) : ViewModel() {
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

    var modoEdicao by mutableStateOf(false)
        private set

    fun atualizarPlaca(texto: String) {
        placa = texto
            .uppercase()
            .filter { it.isLetterOrDigit() }
            .take(11)
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
        viewModelScope.launch {
            val veiculoEncontrado = repository.buscarPlaca(placa)
            if (veiculoEncontrado == null) {
                placaNaoEncontrada = true
            } else {
                placaNaoEncontrada = false
                marca = veiculoEncontrado.marca
                modelo = veiculoEncontrado.modelo
                ano = veiculoEncontrado.ano.toString()
                idTipoCombustivel = veiculoEncontrado.idTipoCombustivel
                exibirCadastroVeiculo = true
                modoEdicao = true
            }
        }
    }

    fun abrirCadastroVeiculo() {
        exibirCadastroVeiculo = true
    }

    private fun atualizarContador() {
        viewModelScope.launch {
            contaCarro = repository.contar()
        }
    }

    fun incluirVeiculo() {
        val anoInt = ano.toIntOrNull() ?: return
        val veiculo = Veiculo(
            placa = placa,
            marca = marca,
            modelo = modelo,
            ano = anoInt,
            idTipoCombustivel = idTipoCombustivel
        )
        viewModelScope.launch {
            val resultado = repository.inserir(veiculo)

            if (resultado != -1L) {
                contaCarro = repository.contar()
            }
            reiniciarTela()
        }
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
        modoEdicao = false
    }

    fun gravarVeiculo() {
        if(modoEdicao)
        {
            atualizarVeiculo()
        }
        else
        {
            incluirVeiculo()
        }
    }

    fun atualizarVeiculo() {
        val anoInt = ano.toIntOrNull() ?: return
        val veiculo = Veiculo(
            placa = placa,
            marca = marca,
            modelo = modelo,
            ano = anoInt,
            idTipoCombustivel = idTipoCombustivel
        )
        viewModelScope.launch {
            repository.atualizar(
                veiculo
            )
            atualizarContador()
            reiniciarTela()
        }
    }
}