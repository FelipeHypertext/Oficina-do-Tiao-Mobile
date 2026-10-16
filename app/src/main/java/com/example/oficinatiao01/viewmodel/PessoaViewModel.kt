package com.example.oficinatiao01.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oficinatiao01.model.Pessoa
import com.example.oficinatiao01.data.repository.PessoaRepository
import kotlinx.coroutines.launch

class PessoaViewModel(
    private val repository: PessoaRepository
) : ViewModel() {

    var contaPessoa by mutableStateOf(0)
        private set

    var cpfNaoEncontrado by mutableStateOf(false)
        private set

    var exibirCadastroPessoa by mutableStateOf(false)
        private set

    var cpf by mutableStateOf("")
        private set

    var nome by mutableStateOf("")
        private set

    var ddd by mutableStateOf("")
        private set

    var telefone by mutableStateOf("")
        private set

    var modoEdicao by mutableStateOf(false)
        private set

    init {
        atualizarContador()
    }

    fun atualizarCpf(texto: String) {
        cpf = texto
            .filter { it.isDigit() }
            .take(11)
    }

    fun atualizarNome(texto: String) {
        nome = texto
    }

    fun atualizarDdd(texto: String) {
        ddd = texto
            .filter { it.isDigit() }
            .take(2)
    }

    fun atualizarTelefone(texto: String) {
        telefone = texto
            .filter { it.isDigit() }
            .take(9)
    }

    fun buscarCpf() {

        if (cpf.isBlank()) {
            return
        }

        viewModelScope.launch {

            val pessoaEncontrada =
                repository.buscarCpf(cpf)

            if (pessoaEncontrada == null) {
                cpfNaoEncontrado = true
            } else {
                cpfNaoEncontrado = false
                nome = pessoaEncontrada.nome
                ddd = pessoaEncontrada.ddd
                telefone = pessoaEncontrada.telefone
                exibirCadastroPessoa = true

                modoEdicao = true
            }
        }
    }

    fun abrirCadastroPessoa() {
        exibirCadastroPessoa = true
    }

    fun incluirPessoa() {

        val pessoa = Pessoa(
            cpf = cpf,
            nome = nome,
            ddd = ddd,
            telefone = telefone
        )

        viewModelScope.launch {

            val resultado = repository.inserir(pessoa)

            if (resultado != -1L) {
                contaPessoa = repository.contar()
            }

            reiniciarTela()
        }
    }

    private fun atualizarContador() {

        viewModelScope.launch {
            contaPessoa = repository.contar()
        }
    }

    fun reiniciarTela() {
        cpf = ""
        nome = ""
        ddd = ""
        telefone = ""

        cpfNaoEncontrado = false
        exibirCadastroPessoa = false
        modoEdicao = false
    }

    fun gravarPessoa() {
        if(modoEdicao)
        {
            atualizarPessoa()
        }
        else
        {
            incluirPessoa()
        }
    }

    fun atualizarPessoa() {

        val pessoa = Pessoa(
            cpf = cpf,
            nome = nome,
            ddd = ddd,
            telefone = telefone
        )
        viewModelScope.launch {
            repository.atualizar(
                pessoa
            )
            reiniciarTela()
        }
    }
}