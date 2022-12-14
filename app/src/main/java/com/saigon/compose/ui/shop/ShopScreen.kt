@file:OptIn(ExperimentalGlideComposeApi::class)

package com.saigon.compose.ui.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.saigon.compose.data.model.Product
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.MyApplicationTheme
import com.saigon.compose.ui.theme.red
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShopScreen(
    viewModel: ShopViewModel,
    modifier: Modifier,
    destination: (String) -> Unit
) {
    Column(
        modifier.fillMaxWidth().fillMaxHeight().background(color = Color.White)
    ) {
        TabHeader(viewModel = viewModel, destination)
    }
}

@Composable
fun TabHeader(viewModel: ShopViewModel, destination: (String) -> Unit) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Women", "Men", "Kids")
    Column {
        TabRow(
            selectedTabIndex = state,
            backgroundColor = Color.Black,
            contentColor = Color.White,
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(it[state]),
                    color = red,
                    height = TabRowDefaults.IndicatorHeight * 0.5F
                )
            }
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = state == index,
                    onClick = {
                        state = index
                        viewModel.getListProductCategory(titles[state].lowercase())
                    }
                )
            }
        }

        ViewListProductCategory(viewModel = viewModel, destination)
        viewModel.getListProductCategory(titles[state].lowercase())
    }
}

@Composable
fun ViewListProductCategory(viewModel: ShopViewModel, destination: (String) -> Unit) {
    // viewModel.getListProduct()
    val state = viewModel.productState.value
    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(top = 5.dp, bottom = 0.dp).fillMaxHeight(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    state.products.size
                ) { index ->
                    ItemProductCollectionCard(
                        modifier = Modifier,
                        item = state.products[index],
                        destination
                    )
                }
            }
        }
    }
}

@Composable
fun ItemProductCollectionCard(
    modifier: Modifier = Modifier,
    item: Product,
    destination: (String) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = Color.Transparent,
        modifier = modifier.clickable { destination.invoke(Screen.ProductDetails.route + "/${item.id}/${item.title}") }
    ) {
        Column(
            modifier = Modifier.height(320.dp)
        ) {
            GlideImage(
                model = item.thumb,
                contentDescription = item.thumb,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.subTitle ?: "",
                style = MaterialTheme.typography.caption,
                color = Color.LightGray,
                fontSize = 13.sp
            )
            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.h6,
                color = Color.Black,
                fontSize = 13.sp
            )
            Row(modifier) {
                Text(
                    text = "$20.0",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopPreview() {
    val vm = koinViewModel<ShopViewModel>()
    MyApplicationTheme {
        ShopScreen(viewModel = vm, modifier = Modifier.padding()) {
        }
    }
}
