package shkond3rs.rickandmorty.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import shkond3rs.rickandmorty.domain.model.Character
import shkond3rs.rickandmorty.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainVM: MainViewModel,
    onCharacterClick: (Int) -> Unit,
) {
    val characters by mainVM.characters.collectAsState()
    val errorMessage by mainVM.errorMessage.collectAsState()

    var isExpanded by rememberSaveable() { mutableStateOf(false) }
    var queryText by rememberSaveable() { mutableStateOf("") }

    val filteredCharacters = remember(characters, queryText) {
        if (queryText.isBlank()) characters
        else characters.filter { character ->
            character.name.contains(queryText, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RICK & MORTY",
                        fontSize = 48.sp,
                        color = MaterialTheme.colorScheme.primary,
                        style = TextStyle(
                            shadow = Shadow(
                                color = MaterialTheme.colorScheme.onPrimary,
                                offset = Offset(0f, 0f),
                                blurRadius = 8f
                            )
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                DockedSearchBar (
                    inputField = {
                        InputField(
                            query = queryText,
                            onQueryChange = { queryText = it },
                            onSearch = {},
                            expanded = false,
                            onExpandedChange = {},
                            placeholder = { Text("Input character name...") },
                            colors = TextFieldDefaults.colors().copy(
                                cursorColor = MaterialTheme.colorScheme.onPrimary,
                                focusedIndicatorColor = Color.Red,
                                unfocusedIndicatorColor = Color.White
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search icon",
                                    modifier = Modifier
                                        .size(18.dp),
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CutCornerShape(
                                topStart = 8.dp,
                                topEnd = 4.dp,
                                bottomStart = 4.dp,
                                bottomEnd = 8.dp
                            )
                        ),
                    expanded = false,
                    onExpandedChange = {},
                    colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.background),
                    shape = CutCornerShape(12.dp)
                ) { }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    content = {
                        items(filteredCharacters) { character ->
                            CharacterCard(
                                character = character,
                                onClick = { onCharacterClick(character.id) }
                            )
                        }
                    }
                )


                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { mainVM.deleteAll() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("clear", color = Color.Black)
                    }
                    Button(
                        onClick = { mainVM.fetchCharacters() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("load", color = Color.Black)
                    }
                }
            }
        }
    )
}


@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: (Int) -> Unit,
) {
    val rows = listOf(
        "SPECIES" to character.species,
        "GENDER" to character.gender
    )

    val characterFirstAppear = character.episodeIds.first()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(character.id) },
        shape = CutCornerShape(
            topStart = 16.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 16.dp
        ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = Color(0xFF1A2035),
            disabledContentColor = Color(0xFF1FD3F0),
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        content = {
            AsyncImage(
                model = character.image,
                contentDescription = "person img",
                modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = character.name.uppercase(),
                    color = if (character.gender.lowercase() == "male") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rows.forEach { (title, value) ->
                        Column(
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = title,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 12.sp
                            )
                            Text(
                                text = value,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 14.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = "Appeared in $characterFirstAppear episode",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    )
}
