package org.d3if3049.asessment.myapplication.screenBudnas

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import org.d3if3049.asessment.myapplication.R
import org.d3if3049.asessment.myapplication.database.BudnasDb
import org.d3if3049.asessment.myapplication.ui.theme.MyApplicationTheme
import org.d3if3049.asessment.myapplication.util.ViewModelFactory

const val KEY_JUDUL = "judul"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: String? = null){
    val context = LocalContext.current
    val db = BudnasDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)


    var judul by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var selectedIsland by rememberSaveable { mutableStateOf("") }
    var selectedProvince by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val getImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedImageUri = it }
    }
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBudnas(id) ?: return@LaunchedEffect
        if (data != null) {
            judul = data.judul
        }
        if (data != null) {
            catatan = data.catatan
        }
        if (data != null) {
            selectedIsland = data.lokasi
        }
        if (data != null) {
            selectedProvince = data.lokasi2
        }
        if (data != null) {
            selectedImageUri = data?.gambar?.toUri()
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint =  MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_budaya1))
                    else
                        Text(text = stringResource(id = R.string.edit_budaya))

                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (judul == "" || catatan == "" || selectedIsland == "" || selectedProvince == "" || selectedImageUri == null) { // Memeriksa apakah judul, catatan, dan lokasi sudah diisi
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        val gambarUri = selectedImageUri.toString()
                        if (id == null) {
                            viewModel.insert(judul, catatan, selectedIsland, selectedProvince, gambarUri) // Menyertakan lokasi saat melakukan operasi insert
                        } else {
                            viewModel.update(judul, catatan, selectedIsland, selectedProvince, gambarUri) // Menyertakan lokasi saat melakukan operasi update
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint =  MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = {  val message = "Judul: $judul\nCatatan: $catatan\nLokasi: $selectedIsland\nLokasi: $selectedProvince"
                            shareData(context, message, selectedImageUri)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = stringResource(R.string.bagikan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {showDialog = true}
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormCatatan(
            title = judul,
            onTitleChange = {judul = it},
            desc = catatan,
            onDesChange = {catatan = it},
            lokasi = selectedIsland, // Menggunakan nilai lokasi yang dipilih
            onLokasiChange = { selectedIsland = it },
            lokasi2 = selectedProvince,
            onLokasi2Change = { selectedProvince = it },
            selectedImageUri = selectedImageUri,
            onImageSelected = { getImage.launch("image/*") },
            modifier = Modifier.padding(padding)
        )

    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = {expanded = true}) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                })
        }
    }
}



@Composable
fun FormCatatan(
    title: String, onTitleChange: (String) -> Unit,
    desc: String, onDesChange: (String) -> Unit,
    lokasi: String, onLokasiChange: (String) -> Unit,
    lokasi2: String, onLokasi2Change: (String) -> Unit,
    selectedImageUri: Uri?, onImageSelected: () -> Unit,
    modifier: Modifier
) {
    var selectedIsland by rememberSaveable { mutableStateOf("") }
    val islands = listOf("Bengkulu Selatan")
    var selectedProvince by rememberSaveable { mutableStateOf("") }
    val provinces = remember { mutableStateOf<List<String>>(listOf()) }
    fun selectIsland(island: String) {
        selectedIsland = island
        selectedProvince = ""
        provinces.value = getProvincesByIsland(island)
    }
    fun selectProvince(province: String) {
        selectedProvince = province
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = title,
                onValueChange = { onTitleChange(it) },
                label = { Text(text = stringResource(R.string.judul)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = desc,
                onValueChange = { onDesChange(it) },
                label = { Text(text = stringResource(R.string.isi_budaya)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            DropDownMenu(
                modifier = Modifier,
                list = islands,
                selectedItem = selectedIsland,
                onItemSelected = {
                    selectIsland(it)
                    onLokasiChange(it)
                }
            )
            selectedIsland = lokasi
            DropDownMenu(
                modifier = Modifier,
                list = provinces.value,
                selectedItem = selectedProvince,
                onItemSelected = {
                    selectProvince(it)
                    onLokasi2Change(it)
                }
            )
            selectedProvince = lokasi2
            OutlinedButton(
                onClick = onImageSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = stringResource(R.string.pilih_gambar))
            }
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
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

private fun shareData(context: Context, message: String, imageUri: Uri?) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
        imageUri?.let {
            putExtra(Intent.EXTRA_STREAM, it)
            clipData = ClipData.newUri(context.contentResolver, "Image", it)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
    }
    if (shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(Intent.createChooser(shareIntent, "Sharevia"))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MyApplicationTheme {
        DetailScreen(rememberNavController())
    }
}