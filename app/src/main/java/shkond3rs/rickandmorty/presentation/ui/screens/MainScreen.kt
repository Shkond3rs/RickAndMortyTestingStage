package shkond3rs.rickandmorty.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import shkond3rs.rickandmorty.domain.model.Character
import shkond3rs.rickandmorty.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainVM: MainViewModel,
    onCharacterClick: (Int) -> Unit,
) {
    val characters by mainVM.characters.collectAsState()
    val errorMessage by mainVM.errorMessage.collectAsState()
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
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background
                )
            )
        },
        contentWindowInsets = WindowInsets.safeContent,
        content = { innerPadding ->
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.weight(1f)) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
                            top = innerPadding.calculateTopPadding(),
                            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 16.dp,
                            bottom = innerPadding.calculateBottomPadding()
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        content = {
                            items(characters) { character ->
                                CharacterCard(
                                    modifier = modifier,
                                    character = character,
                                    onClick = { onCharacterClick(character.id) }
                                )
                            }
                        }
                    )
                }
                Button(onClick = { mainVM.deleteAll() }) { Text(text = "clear") }
                Button(onClick = { mainVM.fetchCharacters() }) { Text(text = "load") }
            }

        }
    )
}

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: (Int) -> Unit
) {
    val rows = listOf(
        "SPECIES" to character.species,
        "GENDER" to character.gender
    )

    val characterFirstAppear = character.episodeIds.first()
    Card(
        modifier = modifier
            .widthIn(max = 220.dp)
            .clickable { onClick(character.id) },
        shape = CutCornerShape(
            topStart = 16.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 16.dp
        ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer                                                                                  ,
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
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = character.name.uppercase(),
                    color = if (character.gender.lowercase() == "male") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
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
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    )
}
