package com.saigon.compose.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saigon.compose.R
import com.saigon.compose.ui.theme.MyApplicationTheme

@Composable
fun HomeScreenExampleGoogle(modifier: Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        SearchBar(modifier = Modifier.padding(horizontal = 15.dp))

        val data = mutableListOf<Int>(100)
        for (i in 0..100)
            data.add(i)

        HomeSection(
            title = R.string.align_your_body,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(data) {
                    AlignYourBodyElement(modifier = Modifier)
                }
            }
        }
        HomeSection(
            title = R.string.favorite,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .height(200.dp)
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = modifier,
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data.size) { item ->
                    FavoriteCollectionCard(modifier = Modifier.height(100.dp))
                }
            }
            // FavoriteCollectionCard(modifier = Modifier)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            stringResource(title),
            modifier = Modifier.padding(vertical = 10.dp),
            style = MaterialTheme.typography.subtitle2
        )
        content()
    }
}

@Composable
fun SearchBar(modifier: Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text("Search")
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun AlignYourBodyElement(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.iv_cat),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = "Inverter"
        )
    }
}

@Composable
fun FavoriteCollectionCard(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.iv_cat),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = "National",
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Scaffold(
            backgroundColor = Color(0xFFF0EAE2)
        ) { padding ->
            HomeScreenExampleGoogle(modifier = Modifier.padding(padding))
        }
    }
}
