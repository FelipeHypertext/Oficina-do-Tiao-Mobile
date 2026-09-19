package com.example.oficinatiao01

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CadastroPessoa (
    modifier: Modifier = Modifier
) {
    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var ddd by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cadastro de Pessoa",
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    OutlinedTextField(
        value = cpf,
        onValueChange = {
            cpf = it
        },
        label = {
            Text("CPF")
        },
        modifier = Modifier.fillMaxSize()
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = nome,
        onValueChange = {
            nome = it
        },
        label = {
            Text("Nome")
        },
        modifier = Modifier.fillMaxSize()
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = ddd,
        onValueChange = {
            ddd = it
        },
        label = {
            Text("DDD")
        },
        modifier = Modifier.fillMaxSize()
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = telefone,
        onValueChange = {
            telefone = it
        },
        label = {
            Text("Telefone")
        },
        modifier = Modifier.fillMaxSize()
    )

    Spacer(modifier = Modifier.height((24.dp)))

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulClaro
            )
        ) {
            Text (
                text = "Incluir",
                color = Color.Black
            )
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulClaro
            )
        ) {
            Text (
                text = "Cancelar",
                color = Color.Black
            )
        }
    }
}