package org.d3if3049.mopro1.budnas.ui.screen

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3049.mopro1.budnas.R
import org.d3if3049.mopro1.budnas.navigation.Screen
import org.d3if3049.mopro1.budnas.ui.theme.BudNasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },

                        actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.About.route)
                        }

                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }

    ) { padding ->
        ScreenContent(context, Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(context: Context, modifier: Modifier) {
    var judul by rememberSaveable { mutableStateOf("") }
    var selectedIsland by rememberSaveable { mutableStateOf("") }
    val islands = listOf("Bengkulu Selatan")
    var selectedProvince by rememberSaveable { mutableStateOf("") }
    val provinces = remember { mutableStateOf<List<String>>(listOf()) }
    val dataList = remember { mutableStateOf<List<String>>(listOf()) }
    val currentContext = LocalContext.current



    fun selectIsland(island: String) {
        selectedIsland = island
        selectedProvince = ""
        provinces.value = getProvincesByIsland(island)
    }
    fun selectProvince(province: String) {
        selectedProvince = province
    }

    fun addData() {
        if (judul.isNotEmpty() && selectedIsland.isNotEmpty() && selectedProvince.isNotEmpty()) {
            dataList.value = dataList.value + "$judul - $selectedIsland - $selectedProvince"
        } else {
            Toast.makeText(context, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
        }
    }

    fun resetData() {
        judul = ""
        selectedIsland = ""
        selectedProvince = ""
        dataList.value = emptyList()
    }


    fun shareData(context: Context, dataList: List<String>) {
        if (dataList.isNotEmpty()) {
            val shareMessage = buildString {
                append("Data yang dibagikan:\n")
                dataList.forEachIndexed { index, data ->
                    append("${index + 1}. $data\n")
                    val (judul, pulau, provinsi) = data.split(" - ")
                    append("Judul: $judul\nPulau: $pulau\nProvinsi: $provinsi\n\n")
                }
            }
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareMessage)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(sendIntent, null))
        } else {
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(15.dp))
            Image(
                painter = painterResource(id = R.drawable.bungarafflesia),
                contentDescription = "Pulau Sumatra",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            )
            OutlinedTextField(
                value = judul,
                onValueChange = { judul = it },
                label = { Text(text = stringResource(R.string.judul)) },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )
            DropDownMenu(
                modifier = Modifier,
                list = islands,
                selectedItem = selectedIsland,
                onItemSelected = { selectIsland(it) }
            )
            DropDownMenu(
                modifier = Modifier,
                list = provinces.value,
                selectedItem = selectedProvince,
                onItemSelected = { selectProvince(it) }
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { addData() },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .weight(1f)
                ) {
                    Text(text = stringResource(R.string.tambah_data))
                }
                Button(
                    onClick = { resetData() },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .weight(1f)
                ) {
                    Text(text = stringResource(R.string.reset))
                }
            }
        }
        items(dataList.value) { data ->
            Text(
                text = data,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp)
            )
        }
        item {
            Button(
                onClick = { shareData(currentContext, dataList.value) },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}


@Composable
fun DropDownMenu(
    modifier: Modifier,
    list: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(0.dp) }
    val icon: ImageVector = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.ArrowDropDown
    }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.width.dp
                },
            label = { Text(text = stringResource(R.string.pilih)) },
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None)
        )
        if (expanded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                list.forEach { label ->
                    DropdownItem(label = label, onClick = {
                        onItemSelected(label)
                        expanded = false
                    })
                }
            }
        }
    }
}

@Composable
fun DropdownItem(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}
fun getProvincesByIsland(island: String): List<String> {
    return when (island) {
        "Bengkulu Selatan" -> listOf("Kedurang", "Kedurang Ilir", "Seginim", "Pino", "Manna", "Kota Manna", "Pino Raya", "Air Nipis", "Ulu Manna", "Bunga Mas", "Pasar Manna")
        else -> emptyList()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BudNasTheme {
        AboutScreen(rememberNavController())
    }
}
