@file:OptIn(ExperimentalGlideComposeApi::class)

package com.saigon.compose.ui.product_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.saigon.compose.R
import com.saigon.compose.data.model.Product
import com.saigon.compose.ui.theme.MyApplicationTheme
import com.saigon.compose.ui.theme.red
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailsViewModel,
    idItem: String,
    modifier: Modifier
) {
    Timber.d("Product Details $idItem")
    val result = viewModel.productState.value
    when {
        result.isLoading -> {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> {
            BodyProductDetails(
                modifier = modifier.background(color = Color.White)
                    .padding(bottom = 60.dp),
                viewModel = viewModel,
                item = result.product
            )
        }
    }
    viewModel.getProductFindById(idItem)
}

@Composable
fun BodyProductDetails(
    modifier: Modifier,
    viewModel: ProductDetailsViewModel,
    item: Product
) {
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        ImageBanner(item = item)
        GroupTittle(item = item)
        GroupDescription(item = item)
        ButtonAddToCart {
            viewModel.addProductToCartId(item)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageBanner(item: Product) {
    val pagerState = rememberPagerState()
    Box {
        HorizontalPager(
            count = item.images.size,
            state = pagerState
        ) { index ->
            GlideImage(
                model = item.images[index],
                contentDescription = item.thumb,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 220.dp)
            )
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = red,
                inactiveColor = Color.White,
                indicatorHeight = 6.dp,
                indicatorWidth = 6.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(3.dp))
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun GroupTittle(item: Product) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.title ?: "",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Black,
                fontSize = 13.sp
            )
            Text(
                text = "$${item.price}",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Black,
                fontSize = 13.sp
            )
        }
        Text(
            text = item.subTitle ?: "",
            fontWeight = FontWeight.Light,
            color = Color.LightGray,
            fontSize = 10.sp
        )
    }
}

@Composable
fun GroupDescription(item: Product) {
    Text(
        text = item.description ?: "",
        style = TextStyle(
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Monospace,
        ),
        color = Color.Black,
        fontSize = 10.sp,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
}

@Composable
fun ButtonAddToCart(onClick: () -> Unit) {
    val isClick = remember { mutableStateOf(false) }
    Button(
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (isClick.value) Color.White else Color.Black,
            contentColor = if (isClick.value) Color.Black else Color.White
        ),
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .border(BorderStroke(1.dp, color = Color.Black))
            .height(40.dp)
            .fillMaxWidth(),
        onClick = {
            isClick.value = true
            onClick.invoke()
        }
    ) {
        Text(
            text = if (isClick.value) stringResource(R.string.added).uppercase()
            else stringResource(R.string.add_to_bag).uppercase(),
            color = if (isClick.value) Color.Black else Color.White,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            ),
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsPreview() {
    val vm = koinViewModel<ProductDetailsViewModel>()
    MyApplicationTheme {
        ProductDetailScreen(
            viewModel = vm,
            idItem = "M01",
            modifier = Modifier.padding()
        )
    }
}
