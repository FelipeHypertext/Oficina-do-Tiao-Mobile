package com.example.oficinatiao01.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oficinatiao01.AmareloClaro
import com.example.oficinatiao01.viewmodel.PessoaViewModel

@Composable
fun CadastroPessoa(pessoaViewModel: PessoaViewModel,
                   modifier: Modifier = Modifier
) {
    Column( modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Cadastro de Pessoa",
            fontSize = 28.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = pessoaViewModel.cpf,
            onValueChange = { },
            readOnly = true,
            label = {
                Text("CPF")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = pessoaViewModel.nome,
            onValueChange = {
                pessoaViewModel.atualizarNome(it)
            },
            label = {
                Text("Nome")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = pessoaViewModel.ddd,
            onValueChange = {
                pessoaViewModel.atualizarDdd(it)
            },
            label = {
                Text("DDD")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = pessoaViewModel.telefone,
            onValueChange = {
                pessoaViewModel.atualizarTelefone( it)
            },
            label = {
                Text("Telefone")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceEvenly
            // distribui espaços iguais entre os elementos e nas duas extremidades
        ) {
            Button(
                onClick = { pessoaViewModel.gravarPessoa() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmareloClaro )
            ) {
                Text(text = "Gravar",
                    color = Color.Black
                )
            }
            Button(
                onClick = { pessoaViewModel.reiniciarTela() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text(text = "Cancelar",
                    color = Color.Black
                )

            }

        }
    }
}
