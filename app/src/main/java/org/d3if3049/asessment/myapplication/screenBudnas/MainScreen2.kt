package org.d3if3049.asessment.myapplication.screenBudnas

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3049.asessment.myapplication.R
import org.d3if3049.asessment.myapplication.database.BudnasDb
import org.d3if3049.asessment.myapplication.model.Budnas
import org.d3if3049.asessment.myapplication.navigation.Screen
import org.d3if3049.asessment.myapplication.ui.theme.MyApplicationTheme
import org.d3if3049.asessment.myapplication.util.SettingsDataStore
import org.d3if3049.asessment.myapplication.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen2(navController: NavHostController) {
    val currentContext = LocalContext.current
    val dataList = remember { mutableStateOf<List<Budnas>>(listOf()) }
    val context = LocalContext.current
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)
    val data by remember { mutableStateOf<List<Budnas>>(emptyList()) }
    var judul by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var selectedIsland by rememberSaveable { mutableStateOf("") }
    var selectedProvince by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                },actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    dataStore.saveLayout(!showList)
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (showList) R.drawable.baseline_grid_view_24
                                    else R.drawable.baseline_view_list_24
                                ),
                                contentDescription = stringResource(
                                    if (showList) R.string.grid
                                    else R.string.list
                                ),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
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
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = stringResource(R.string.tentang_aplikasi),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.AccountCircle,
                                        contentDescription = stringResource(R.string.assesment),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                text = {
                                    Text(stringResource(R.string.assesment))
                                },
                                onClick = {
                                    expanded = false
                                    navController.navigate(Screen.Home2.route)
                                }
                            )

                        }
                    }

                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription =  stringResource(id = R.string.tambah_budaya1),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    ) { padding ->
        ScreenContent(showList, Modifier.padding(padding), navController)
    }
}

@Composable
fun ScreenContent(showList:Boolean, modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = BudnasDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel2 = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    Column(modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.budaya_tak_benda),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Ukuran gambar ditingkatkan agar lebih mudah terlihat
                .padding(top = 40.dp) // Padding atas sebesar 50dp untuk menurunkan gambar
        ) {
            Image(
                painter = painterResource(id = R.drawable.bungarafflesia),
                contentDescription = "Pulau Sumatra",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            )
        }

        // Check if data is empty
        if (data.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.list_kosong))
            }
        } else {
            if (showList) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 84.dp)
                ) {
                    items(data) {
                        ListItem(catatan = it) {
                            navController.navigate(Screen.FormUbah.withJudul(it.judul))
                        }
                        Divider()
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    modifier = modifier.fillMaxSize(),
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
                ) {
                    items(data) {
                        GridItem(catatan = it) {
                            navController.navigate(Screen.FormUbah.withJudul(it.judul))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun GridItem(catatan: Budnas, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = catatan.judul,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = catatan.catatan,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = catatan.lokasi,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = catatan.lokasi2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            catatan.gambar?.let { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp) // Sesuaikan ukuran gambar sesuai kebutuhan
                        .padding(top = 8.dp) // Beri jarak dari teks di atasnya
                )
            }
        }
    }
}

@Composable
fun ListItem(catatan: Budnas, onClick: (Budnas) -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(catatan) }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = catatan.judul,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = catatan.catatan,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = catatan.lokasi,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = catatan.lokasi2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        catatan.gambar?.let { uri ->
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp) // Sesuaikan ukuran gambar sesuai kebutuhan
                    .padding(top = 8.dp) // Beri jarak dari teks di atasnya
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    MyApplicationTheme {
        MainScreen2(rememberNavController())
    }
}
