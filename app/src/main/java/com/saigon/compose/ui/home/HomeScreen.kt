package com.saigon.compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.saigon.compose.R
import com.saigon.compose.data.model.Product
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.MyApplicationTheme
import com.saigon.compose.ui.theme.red
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier,
    destination: (String) -> Unit
) {
    val result = viewModel.dashboardState.value
    val scrollState = rememberScrollState()
    when {
        result.isLoading -> {
            Box(modifier = modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {
            Column(
                modifier = modifier
                    .padding(bottom = 50.dp)
                    .background(Color.White).fillMaxHeight()
                    .verticalScroll(state = scrollState)
            ) {
                BannerHomePage(
                    result.data?.promotion ?: Product(),
                    scrollState.value
                )
                SaleProducts(
                    result.data?.saleProducts ?: listOf(),
                    destination = destination
                )
                NewProducts(
                    result.data?.newProducts ?: listOf(),
                    destination = destination
                )
            }
        }
    }
    viewModel.getDashboard()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BannerHomePage(promotion: Product, valueScroll: Int) {
    val min = 400 - valueScroll
    val resize = if (min >= 250) {
        min
    } else {
        250
    }

    ConstraintLayout(modifier = Modifier.height(resize.dp)) {
        val (title, button, _) = createRefs()
        GlideImage(
            model = promotion.coverMage,
            contentDescription = promotion.coverMage,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            modifier = Modifier.constrainAs(title) {
                bottom.linkTo(button.top)
                start.linkTo(button.start)
            },
            text = "Fashion\nSale",
            color = Color.White,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
            fontSize = 32.sp
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button) {
                bottom.linkTo(parent.bottom, margin = 15.dp)
                start.linkTo(parent.start, margin = 15.dp)
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White

            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "Check",
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                ),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SaleProducts(products: List<Product>, destination: (String) -> Unit) {
    TitleSectionHome("Sale", "Supper summer sale")
    if (products.isNotEmpty()) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(products) { product ->
                ItemProductSale(item = product) {
                    destination.invoke(Screen.ProductDetails.route + "/${product.id}/${product.title}")
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun NewProducts(products: List<Product>, destination: (String) -> Unit) {
    val pagerState = rememberPagerState()
    TitleSectionHome("New", "you 're never sale it before!")
    if (products.isNotEmpty()) {
        ConstraintLayout() {
            val indicator = createRef()
            HorizontalPager(
                count = products.size,
                state = pagerState
            ) { index ->
                GlideImage(
                    model = products[index].thumb,
                    contentDescription = products[index].thumb,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .fillMaxWidth()
                        .height(height = 180.dp)
                        .clickable {
                            destination.invoke(Screen.ProductDetails.route + "/${products[index].id}/${products[index].title}")
                        }
                )
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = red,
                inactiveColor = Color.White,
                indicatorHeight = 6.dp,
                indicatorWidth = 6.dp,
                modifier = Modifier
                    .constrainAs(indicator) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clip(RoundedCornerShape(3.dp))
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun TitleSectionHome(title: String, des: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(
                text = title,
                color = Color.Black,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                ),
                fontSize = 16.sp
            )
            Text(
                text = des,
                color = Color.LightGray,
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.SansSerif
                ),
                fontSize = 13.sp
            )
        }
        Text(
            text = stringResource(R.string.view_all),
            color = Color.Red,
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif
            ),
            fontSize = 13.sp
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemProductSale(
    modifier: Modifier = Modifier,
    item: Product,
    destination: (String) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = Color.Transparent,
        modifier = modifier.clickable { destination.invoke(Screen.ProductDetails.route + "/${item.id}/${item.title}") }
    ) {
        Column() {
            GlideImage(
                model = item.thumb,
                contentDescription = item.thumb,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.subTitle ?: "",
                style = MaterialTheme.typography.caption,
                color = Color.LightGray,
                fontSize = 10.sp
            )
            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.h6,
                color = Color.Black,
                fontSize = 10.sp,
                modifier = modifier.width(130.dp)
            )
            Row(modifier) {
                Text(
                    text = "$20.0",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Red,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val vm = koinViewModel<HomeViewModel>()
    MyApplicationTheme {
        HomeScreen(
            viewModel = vm,
            modifier = Modifier.padding()
        ) {}
    }
}
