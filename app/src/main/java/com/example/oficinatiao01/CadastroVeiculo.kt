package com.example.oficinatiao01

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oficinatiao01.model.listaTiposCombustivel
import com.example.oficinatiao01.viewmodel.OficinaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroVeiculo(
    viewModel: OficinaViewModel,
    modifier: Modifier = Modifier
) {

    var expandido by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Cadastro de Veículo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            OutlinedTextField(
                value = viewModel.placa,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Placa")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.marca,
                onValueChange = {
                    viewModel.atualizarMarca(it)
                },
                label = {
                    Text("Marca")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.modelo,
                onValueChange = {
                    viewModel.atualizarModelo(it)
                },
                label = {
                    Text("Modelo")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.ano,
                onValueChange = {
                    viewModel.atualizarAno(it)
                },
                label = {
                    Text("Ano")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = {
                    expandido = !expandido
                }
            ) {

                val combustivelSelecionado =
                    listaTiposCombustivel
                        .find {
                            it.idTipoCombustivel ==
                                    viewModel.idTipoCombustivel
                        }
                        ?.tipoCombustivel ?: ""

                OutlinedTextField(
                    value = combustivelSelecionado,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Combustível")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandido
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = {
                        expandido = false
                    }
                ) {

                    listaTiposCombustivel.forEach { tipo ->

                        DropdownMenuItem(
                            text = {
                                Text(tipo.tipoCombustivel)
                            },
                            onClick = {
                                viewModel.atualizarTipoCombustivel(
                                    tipo.idTipoCombustivel
                                )

                                expandido = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        viewModel.incluirVeiculo()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulClaro
                    )
                ) {
                    Text(
                        text = "Incluir",
                        color = Color.Black
                    )
                }

                Button(
                    onClick = {
                        viewModel.reiniciarTela()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "Cancelar",
                        color = Color.Black
                    )
                }
            }
        }
    }
}