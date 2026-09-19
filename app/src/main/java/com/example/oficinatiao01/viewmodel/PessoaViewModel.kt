package com.example.oficinatiao01.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.oficinatiao01.model.Pessoa
import kotlin.text.filter

class PessoaViewModel {
    var exibirCadastroPessoa by mutableStateOf(false)
        private set

    var cpfNaoEncontrado by mutableStateOf(false)
        private set

    var contaPessoa by mutableStateOf(0)
        private set

    private val listaPessoas = mutableListOf<Pessoa>()


    var nome by mutableStateOf("")
        private set

    var cpf by mutableStateOf("")
        private set

    var ddd by mutableStateOf("")
        private set

    var telefone by mutableStateOf("")
        private set

    fun atualizarNome(texto: String) {
        nome = texto
    }

    fun atualizarCpf(texto: String) {
        cpf = texto
            .filter { it.isDigit() }
            .take(2)
    }

    fun atualizarDdd(texto: String) {
        ddd = texto
            .filter { it.isDigit() }
            .take(2)
    }

    fun atualizarTelefone(texto: String) {
        telefone = texto
            .filter { it.isDigit() }
            .take(2)
    }

    fun buscarCpf() {
        if (cpf.isBlank()) return

        val pessoaEncontrada = listaPessoas.find {
            it.cpf == cpf
        }

        if (pessoaEncontrada == null) {
            cpfNaoEncontrado = true
        } else {
            cpfNaoEncontrado = false
            nome = pessoaEncontrada.nome
            ddd = pessoaEncontrada.ddd
            telefone = pessoaEncontrada.telefone
            exibirCadastroPessoa = true
        }

        if (pessoaEncontrada != null) {
            reiniciarTela()
            return
        }

        listaPessoas.add(
            Pessoa(
                cpf = cpf,
                nome = nome,
                ddd = ddd,
                telefone = telefone
            )
        )
        contaPessoa = listaPessoas.count()
        reiniciarTela()
    }

    fun reiniciarTela() {
        cpf = ""
        nome = ""
        ddd = ""
        telefone = ""

        cpfNaoEncontrado = false
        exibirCadastroPessoa = false
    }
}