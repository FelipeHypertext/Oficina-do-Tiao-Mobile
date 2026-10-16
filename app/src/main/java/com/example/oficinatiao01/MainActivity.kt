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
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.oficinatiao01.data.database.OficinaDatabase
import com.example.oficinatiao01.data.repository.PessoaRepository
import com.example.oficinatiao01.data.repository.VeiculoRepository
import com.example.oficinatiao01.viewmodel.OficinaViewModelFactory
import com.example.oficinatiao01.viewmodel.PessoaViewModelFactory

val AmareloClaro = Color(0xFFE6D690)
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val MIGRATION_1_2 =
            object : Migration(1, 2) {
                override fun migrate(
                    database: SupportSQLiteDatabase
                ) {
                    database.execSQL(
                        """
                CREATE TABLE IF NOT EXISTS veiculo(
                    placa TEXT NOT NULL,
                    marca TEXT NOT NULL,
                    modelo TEXT NOT NULL,
                    ano INTEGER NOT NULL,
                    idTipoCombustivel INTEGER NOT NULL,
                    PRIMARY KEY(placa)
                )
                """
                    )
                }
            }

        val database = Room.databaseBuilder(
            applicationContext,
            OficinaDatabase::class.java,
            "oficina.db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()

        val repository = PessoaRepository(
            database.pessoaDao())

        val veiculoRepository = VeiculoRepository(
                database.veiculoDao())
        enableEdgeToEdge()

        setContent {

            OficinaTiao01Theme {
                TelaPrincipal(repository, veiculoRepository)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(repository: PessoaRepository
                , veiculoRepository: VeiculoRepository) {
    //val viewModel: OficinaViewModel = viewModel()
    val viewModel: OficinaViewModel = viewModel(
            factory = OficinaViewModelFactory(
                veiculoRepository
            )
        )

    //val pessoaViewModel: PessoaViewModel = viewModel()
    val pessoaViewModel: PessoaViewModel = viewModel(
        factory = PessoaViewModelFactory(repository)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {Text(text = "Oficina do Tião",
                             fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Column() {
                            Text(text = "Carros..: ${viewModel.contaCarro}",
                                fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Pessoas: ${pessoaViewModel.contaPessoa}",
                                 fontSize = 14.sp,fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults
                    .topAppBarColors(containerColor = AmareloClaro)
            )
        }
    ) { paddingValues ->
        OficinaTiaoAbertura(
            modifier = Modifier.padding(paddingValues)
            ,viewModel
            ,pessoaViewModel
        )
    }
}

@Composable
fun OficinaTiaoAbertura(
    modifier: Modifier = Modifier,
    viewModel: OficinaViewModel,
    pessoaViewModel: PessoaViewModel
) {

    if (viewModel.exibirCadastroVeiculo) {
        CadastroVeiculo(
            viewModel = viewModel,
            modifier = modifier
        )
        return
    }

    if (pessoaViewModel.exibirCadastroPessoa) {
        CadastroPessoa(pessoaViewModel,
            modifier = modifier
        )
        return
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(
                id = R.drawable.carro_do_tiao),
            contentDescription = "Oficina do Tião",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize()
                .padding(start = 24.dp,
                    end = 24.dp,
                    bottom = 30.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            OutlinedTextField(value = viewModel.placa,
                onValueChange = { texto ->
                    viewModel.atualizarPlaca(texto) },
                label = {
                    Text(text = "Digite a Placa ou CPF",
                        fontSize = 20.sp)  },
                textStyle = TextStyle(fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black  ),
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
            Button(onClick = { viewModel.buscarPlaca() },
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmareloClaro
                )
            ) {
                Text(
                    text = "Buscar",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (viewModel.placaNaoEncontrada) {
                Text(text = "Cadastrar placa?",
                    fontSize = 40.sp,
                    color = Color.White )
                Row(modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement =Arrangement.SpaceEvenly,
                    verticalAlignment =Alignment.CenterVertically
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

            if (pessoaViewModel.cpfNaoEncontrado) {
                Text(text = "Cadastrar pessoa?",
                    fontSize = 40.sp,
                    color = Color.White)
                Row(modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Sim",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.clickable {
                            pessoaViewModel.abrirCadastroPessoa()
                            viewModel.reiniciarTela()
                        }
                    )
                    Text(text = "Não",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.clickable {
                            pessoaViewModel.reiniciarTela()
                            viewModel.reiniciarTela()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                pessoaViewModel.atualizarCpf(viewModel.placa)
                pessoaViewModel.buscarCpf()
                viewModel.reiniciarTela()
                },
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmareloClaro)
            ) {
                Text(text = "Pessoas",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { },
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmareloClaro
                )
            ) {Text(text = "Outros",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}