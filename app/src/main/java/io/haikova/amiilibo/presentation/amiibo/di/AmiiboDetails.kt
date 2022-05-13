package io.haikova.amiilibo.presentation.amiibo.di

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.haikova.amiilibo.R
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.presentation.amiibo.AmiiboDetailsViewModel
import io.haikova.amiilibo.theme.*
import kotlinx.coroutines.launch

@Composable
fun AmiiboDetailsScreen(
  amiiboDetailsViewModel: AmiiboDetailsViewModel = viewModel()
) {

  Scaffold(
    topBar = {
      TopAppBar(
        backgroundColor = MaterialTheme.colors.onBackground
      ) {
        IconButton(onClick = {
          // ToDo add back navigation
        })
        {
          Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = MaterialTheme.colors.secondary
          )
        }
      }
    }
  ) {
    val amiiboData = amiiboDetailsViewModel.amiiboData.observeAsState()
    amiiboData.value?.let { amiibo ->
      AmiiboDetails(amiibo)
      ButtonsContainer(
        amiibo = amiibo,
        ownButtonClicked = amiiboDetailsViewModel::changeOwnedState,
        favouriteButtonClicked = amiiboDetailsViewModel::changeFavouriteState
      )
    }
  }
}

@Composable
fun AmiiboDetails(
  amiibo: AmiiboModel
) {
  BoxWithConstraints(
    modifier = Modifier
      .fillMaxSize()
  ) {
    var colorFrom by remember { mutableStateOf(Color.White) }
    var colorTo by remember { mutableStateOf(Color.White) }
    var visible by remember { mutableStateOf(false) }

    GradientBackground(visible, colorTo, colorFrom)

    Box(
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
    ) {
      Box(
        modifier = Modifier
          .padding(top = 200.dp)
          .fillMaxWidth()
          .height(this@BoxWithConstraints.maxHeight)
          .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
          .background(MaterialTheme.colors.background)
      )
      Column {
        AmiiboImage(amiibo.image) { palette ->
          colorFrom = Color(palette.getVibrantColor(Color.White.toArgb()))
          colorTo = Color(palette.getMutedColor(Color.White.toArgb()))
          visible = true
        }
        AmiiboDescription(amiibo)
        Spacer(modifier = Modifier.height(108.dp))
      }
    }
  }
}

@Composable
fun GradientBackground(
  isVisible: Boolean,
  colorFrom: Color,
  colorTo: Color
) {
  AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn()
  ) {
    Box(
      modifier = Modifier
        .height(216.dp)
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              colorFrom,
              colorTo
            )
          )
        )
    )
  }
}

@Composable
fun AmiiboImage(
  image: String,
  onImageLoaded: (Palette) -> Unit
) {
  val painter = rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalContext.current)
      .data(image)
      .allowHardware(false)
      .build()
  )
  Image(
    painter = painter,
    contentDescription = null,
    modifier = Modifier
      .height(243.dp)
      .fillMaxWidth()
      .padding(top = 32.dp),
  )
  if (painter.state is AsyncImagePainter.State.Success) {
    LaunchedEffect(key1 = painter) {
      launch {
        painter.imageLoader.execute(painter.request).drawable?.toBitmap()
          ?.let { bitmap ->
            Palette.Builder(bitmap).generate {
              it?.let { palette ->
                onImageLoaded.invoke(palette)
              }
            }
          }
      }
    }
  }
}

@Composable
fun AmiiboDescription(amiibo: AmiiboModel) {
  Text(
    text = amiibo.name,
    color = AppTheme.color.secondary,
    style = Heading1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
  )
  Text(
    text = stringResource(id = R.string.amiibo_series),
    color = if (isSystemInDarkTheme()) gray_96 else gray_6a,
    style = Caption4,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp)
  )
  Text(
    text = amiibo.amiiboSeries,
    color = AppTheme.color.secondary,
    style = Body1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
  )
  Text(
    text = stringResource(id = R.string.character),
    color = if (isSystemInDarkTheme()) gray_96 else gray_6a,
    style = Caption4,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp)
  )
  Text(
    text = amiibo.character,
    color = AppTheme.color.secondary,
    style = Body1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
  )
  Text(
    text = stringResource(id = R.string.game_series),
    color = if (isSystemInDarkTheme()) gray_96 else gray_6a,
    style = Caption4,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp)
  )
  Text(
    text = amiibo.gameSeries,
    color = AppTheme.color.secondary,
    style = Body1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
  )
  Text(
    text = stringResource(id = R.string.released),
    color = if (isSystemInDarkTheme()) gray_96 else gray_6a,
    style = Caption4,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp)
  )
  Text(
    text = stringResource(id = R.string.jp_data, amiibo.releaseCountryMap["jp"] ?: "?"),
    color = AppTheme.color.secondary,
    style = Body1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
  )
  Text(
    text = stringResource(id = R.string.eu_data, amiibo.releaseCountryMap["eu"] ?: "?"),
    color = AppTheme.color.secondary,
    style = Body1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
  )
  Text(
    text = stringResource(id = R.string.na_data, amiibo.releaseCountryMap["na"] ?: "?"),
    color = AppTheme.color.secondary,
    style = Body1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
  )
  Text(
    text = stringResource(id = R.string.au_data, amiibo.releaseCountryMap["au"] ?: "?"),
    color = AppTheme.color.secondary,
    style = Body1,
    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
  )
}

@Composable
fun ButtonsContainer(
  amiibo: AmiiboModel,
  ownButtonClicked: () -> Unit,
  favouriteButtonClicked: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.Bottom,
    modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 24.dp)
  ) {
    Spacer(modifier = Modifier.width(24.dp))
    Button(
      onClick = ownButtonClicked,
      modifier = Modifier
        .weight(1f)
        .height(52.dp),
      shape = RoundedCornerShape(30.dp)
    ) {
      Text(
        text = if (amiibo.isOwned) "Remove from Collection" else "Add to Collection",
        style = Button,
        color = white,
      )
    }

    Spacer(modifier = Modifier.width(24.dp))
    FloatingActionButton(
      onClick = favouriteButtonClicked,
      backgroundColor = AppTheme.color.primary,
    ) {
      Icon(
        imageVector = ImageVector.vectorResource(id = if (amiibo.isFavourite) R.drawable.ic_favourite_added else R.drawable.ic_favourite),
        contentDescription = null,
        tint = white
      )
    }
    Spacer(modifier = Modifier.width(24.dp))
  }
}