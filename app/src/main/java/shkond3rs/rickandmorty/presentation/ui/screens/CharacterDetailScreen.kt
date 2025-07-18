package shkond3rs.rickandmorty.presentation.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import shkond3rs.rickandmorty.R
import shkond3rs.rickandmorty.domain.model.Character
import shkond3rs.rickandmorty.domain.model.Episode
import shkond3rs.rickandmorty.domain.model.Location
import shkond3rs.rickandmorty.presentation.ui.theme.RickAndMortyTheme
import shkond3rs.rickandmorty.presentation.viewmodels.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    characterId: Int,
    vm: DetailViewModel,
    onBack: () -> Unit
) {
    val person = vm.character.collectAsState()
    val origin = vm.origin.collectAsState()
    val location = vm.location.collectAsState()
    val episodes = vm.episodes.collectAsState()

    LaunchedEffect(characterId) {
        vm.fetchCharacter(characterId)
    }

    Scaffold(
        modifier = Modifier,
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
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
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
            LazyColumn(
                contentPadding = PaddingValues(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 8.dp,
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 8.dp,
                    bottom = innerPadding.calculateBottomPadding()
                ),
            ) {
                item { PersonCard(person = person.value) }
                item { Text(
                    text = "Information",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.onPrimary,
                            offset = Offset(0f, 0f),
                            blurRadius = 4f
                        )
                    )
                ) }
                item {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(8.dp))
                }
                item {
                    InformationCard(
                        person = person.value,
                        origin = origin.value,
                        location = location.value
                    )
                    Spacer(Modifier.height(16.dp))
                }
                item { Text(
                    text = "Episodes",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.onPrimary,
                            offset = Offset(0f, 0f),
                            blurRadius = 4f
                        )
                    )
                ) }
                item {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(8.dp))
                }
                items(episodes.value) { episode ->
                    EpisodeCard(episode = episode)
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    )
}

@Composable
fun PersonCard(
    modifier: Modifier = Modifier,
    person: Character?,
) {
    Card(
        modifier.fillMaxWidth(),
        shape = CutCornerShape(
            topStart = 32.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 32.dp
        ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black
        ),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(
                model = person?.image,
                contentDescription = "person img",
                modifier = modifier
                    .fillMaxWidth(),
//                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${person?.name}",
                    color = if (person?.gender?.lowercase() == "male") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                )
                Box(
                    modifier = Modifier
                        .clip(CutCornerShape(8.dp))
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.outline,
                            CutCornerShape(8.dp)
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (person?.status?.lowercase() == "alive") {
                        Text(
                            text = "${person?.status}",
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(8.dp)
                        )
                    } else {
                        Text(
                            text = "${person?.status}",
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(text = "GENDER",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 20.sp
                    )
                    Text(text = "${person?.gender}",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 22.sp,
                        lineHeight = 22.sp
                    )
                }
                Column {
                    Text(text = "SPECIES",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 20.sp
                    )
                    Text(text = "${person?.species}",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 22.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }

    Spacer(Modifier.height(16.dp))
}

@Composable
fun InformationCard(
    modifier: Modifier = Modifier,
    person: Character?,
    origin: Location?,
    location: Location?
) {
    val personType: String? = if (person?.type == "") "Creature" else person?.type
    val locationName: String = location?.name ?: "Unknown"
    Card(
        modifier.fillMaxWidth(),
        shape = CutCornerShape(
            topStart = 16.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 16.dp
        ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black
        ),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline)
    ) {
        val originInfo = origin?.name ?: "Unknown"
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Text(
                    text = "TYPE",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
                Text(
                    text = "$personType",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Column {
                Text(
                    text = "ORIGIN",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
                Text(
                    text = originInfo,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Column {
                Text(
                    text = "LOCATION",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
                Text(
                    text = locationName,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun EpisodeCard(modifier: Modifier = Modifier, episode: Episode) {
    Card(
        modifier.fillMaxWidth(),
        shape = CutCornerShape(
            topStart = 8.dp,
            topEnd = 4.dp,
            bottomStart = 4.dp,
            bottomEnd = 8.dp
        ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black
        ),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = episode.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 16.sp
            )
            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = episode.episode,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
                Text(
                    text = episode.airDate,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

val mockupPerson = Character(
    1, "Rick Sanchez",
    "Alive",
    species = "Human",
    gender = "Male",
    type = "Creature",
    image = "",
    episodeIds = listOf(1, 2, 3),
    originId = 1,
    locationId = 3
)

val mockupOrigin = Location(
    id = 1,
    name = "Earth (C-137)",
    type = "Planet",
    dimension = "Dimension C-137"
)


val mockupLocation = Location(
    id = 3,
    name = "Citadel of Ricks",
    type = "Space station",
    dimension = "unknown"
)

val mockupEpisodes = listOf<Episode>(
    Episode(
        id = 1,
        name = "Pilot",
        airDate = "December 2, 2013",
        episode = "S01E01"
    ),
    Episode(
        id = 2,
        name = "Lawnower Dog",
        airDate = "December 9, 2013",
        episode = "S01E02"
    ),
    Episode(
        id = 3,
        name = "Anatomy Park",
        airDate = "December 16, 2013",
        episode = "S01E03"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DetailScreenPrev(modifier: Modifier = Modifier) {
    RickAndMortyTheme {
        Scaffold(
            modifier = Modifier,
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
                    navigationIcon = {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
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
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 8.dp,
                        top = innerPadding.calculateTopPadding(),
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 8.dp,
                        bottom = innerPadding.calculateBottomPadding()
                    ),
                ) {
                    item {
                        Card(
                            modifier.fillMaxWidth(),
                            shape = CutCornerShape(
                                topStart = 32.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 32.dp
                            ),
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.Black
                            ),
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Image(
                                    painter = painterResource(R.drawable.rick),
                                    contentDescription = "person img",
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = mockupPerson.name,
                                        color = if (mockupPerson.gender.lowercase() == "male") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp,
                                    )
//                                   Button(onClick = { }) { Text(text = mockupPerson.status) }
                                    Box(
                                        modifier = Modifier
                                            .clip(CutCornerShape(8.dp))
                                            .border(
                                                2.dp,
                                                MaterialTheme.colorScheme.outline,
                                                CutCornerShape(8.dp)
                                            )
                                            .size(height = 32.dp, width = 64.dp)
                                            .background(MaterialTheme.colorScheme.background),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (mockupPerson.status.lowercase() == "alive") {
                                            Text(
                                                text = mockupPerson.status,
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                        } else {
                                            Text(
                                                text = mockupPerson.status,
                                                color = MaterialTheme.colorScheme.onTertiary
                                            )
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Column {
                                        Text(
                                            text = "GENDER",
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            lineHeight = 14.sp
                                        )
                                        Text(
                                            text = mockupPerson.gender,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = "SPECIES",
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            lineHeight = 14.sp
                                        )
                                        Text(
                                            text = mockupPerson.species,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    item {
                        Text(
                            text = "Information",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    offset = Offset(0f, 0f),
                                    blurRadius = 4f
                                )
                            )
                        )
                    }
                    item {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                        Spacer(Modifier.height(8.dp))
                    }
                    item {
                        InformationCard(
                            person = mockupPerson,
                            origin = mockupOrigin,
                            location = mockupLocation
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                    item {
                        Text(
                            text = "Episodes",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    offset = Offset(0f, 0f),
                                    blurRadius = 4f
                                )
                            )
                        )
                    }
                    item {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                        Spacer(Modifier.height(8.dp))
                    }
                    items(mockupEpisodes) { episode ->
                        EpisodeCard(episode = episode)
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        )
    }
}