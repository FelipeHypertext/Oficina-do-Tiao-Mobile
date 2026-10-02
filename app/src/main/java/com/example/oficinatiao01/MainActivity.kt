package com.example.oficinatiao01

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oficinatiao01.ui.theme.OficinaTiao01Theme
import com.example.oficinatiao01.view.CadastroPessoa
import com.example.oficinatiao01.view.CadastroVeiculo
import com.example.oficinatiao01.viewmodel.OficinaViewModel
import com.example.oficinatiao01.viewmodel.PessoaViewModel

val AzulClaro = Color(0xFF536DFE)
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            OficinaTiao01Theme {
                TelaPrincipal()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal() {
    val viewModel: OficinaViewModel = viewModel()
    val viewModelPessoa: PessoaViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Oficina do Tião",
                             fontSize = 28.sp,
                             fontWeight = FontWeight.Bold
                        )
                        Text(text = "Carros: ${viewModel.contaCarro}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Text(text = "Carros: ${viewModelPessoa.contaPessoa}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults
                    .topAppBarColors(containerColor = AzulClaro)
            )
        }
    ) { paddingValues ->
        OficinaTiaoAbertura(
            modifier = Modifier.padding(paddingValues)
            ,viewModel
        )
    }
}

@Composable
fun OficinaTiaoAbertura(
    modifier: Modifier = Modifier,
    viewModel: OficinaViewModel,

) {
      val viewModel: OficinaViewModel = viewModel()
      val viewModelPessoa: PessoaViewModel = viewModel()

    if (viewModel.exibirCadastroVeiculo) {
        CadastroVeiculo(
            viewModel = viewModel,
            modifier = modifier
        )
        return
    }

    if (viewModelPessoa.exibirCadastroPessoa) {
        CadastroPessoa(
            viewModelPessoa = viewModelPessoa,
            modifier = modifier
        )
        return
    }

    Box(modifier = modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(
                        id = R.drawable.carro_do_tiao),
            contentDescription = "Oficina do Tião",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 30.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        )
        {
            OutlinedTextField(value =viewModel.placa,
                onValueChange = { texto ->
                    viewModel.atualizarPlaca(texto);
                    viewModelPessoa.atualizarCpf(texto)
                },

                label = {
                    Text(text = "Digite a Placa ou CPF",
                        fontSize = 20.sp)},

                textStyle = TextStyle(fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black),

                singleLine = true,
                modifier = Modifier.fillMaxWidth(),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor =
                        Color.White.copy(alpha = 0.95f),
                    unfocusedContainerColor =
                        Color.White.copy(alpha = 0.95f),

                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Yellow,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { viewModel.buscarPlaca(); viewModelPessoa.buscarCpf()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulClaro)
            ) {Text(text = "Buscar",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold)
            }
            if (viewModel.placaNaoEncontrada) {
                Text(text = "Cadastrar placa?",
                    fontSize = 40.sp,
                    color = Color.White)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Sim",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.clickable {
                            viewModel.abrirCadastroVeiculo()
                        }
                    )

                    Text(text = "Não",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.clickable {
                            viewModel.reiniciarTela()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { viewModelPessoa.buscarCpf() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulClaro
                )
            ) {
                Text(text = "Pessoas",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (viewModelPessoa.cpfNaoEncontrado) {
                Text(
                    text = "Cadastrar Pessoa?",
                    fontSize = 40.sp,
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sim",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.clickable {
                            viewModelPessoa.abrirCadastroPessoa()
                        }
                    )

                    Text(
                        text = "Não",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.clickable {
                            viewModelPessoa.reiniciarTela()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {/* Outros */},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulClaro)
            ) {
                Text(text = "Outros",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}