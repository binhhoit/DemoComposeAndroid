@file:OptIn(ExperimentalGlideComposeApi::class)

package com.saigon.compose.ui.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.saigon.compose.data.model.Product
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.MyApplicationTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    modifier: Modifier,
    destination: (String) -> Unit
) {
    BodyCart(
        modifier = modifier
            .padding(bottom = 50.dp)
            .background(Color.White),
        viewModel = viewModel,
        destination = destination
    )
}

@Composable
fun BodyCart(
    modifier: Modifier,
    viewModel: CartViewModel,
    destination: (String) -> Unit
) {
    val result = viewModel.productState.value
    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            items(result.products.size) { index ->
                ItemCart(item = result.products[index], viewModel, destination)
            }
        }
        ButtonCheckout(
            onClick = {
                viewModel.clearDataCart()
                destination.invoke(Screen.PaymentMethod.route)
            },
            totalPrice = result.priceTotal,
            modifier.align(alignment = Alignment.BottomCenter)
        )
    }
    viewModel.getListProductAddToCart()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemCart(item: Product, viewModel: CartViewModel, destination: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        onClick = { destination.invoke(Screen.ProductDetails.route + "/${item.id}/${item.title}") }
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            GlideImage(
                model = item.thumb,
                contentDescription = item.thumb,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxHeight()
                    .width(100.dp)
            )
            ContentItemCart(item = item, viewModel)
            OptionAndPrice(item = item)
        }
    }
}

@Composable
fun ContentItemCart(item: Product, viewModel: CartViewModel) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 15.dp)
            .fillMaxWidth(0.75f)
    ) {
        Text(
            text = item.title ?: "",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            ),
            color = Color.Black,
            fontSize = 12.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewModel.removeProductAddToCart(item) }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "add",
                    modifier = Modifier
                        .shadow(2.dp, shape = CircleShape)
                        .background(color = Color.White),
                    tint = Color.LightGray
                )
            }
            Text(
                text = item.count.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Black,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            IconButton(onClick = { viewModel.addProductAddToCart(item) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    modifier = Modifier
                        .shadow(2.dp, shape = CircleShape)
                        .background(color = Color.White),
                    tint = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun OptionAndPrice(item: Product) {
    val expanded by remember { mutableStateOf(mutableStateOf(false)) }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 10.dp)
    ) {
        IconButton(onClick = { expanded.value = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "option",
                tint = Color.LightGray
            )

            PopupOption(expanded = expanded)
        }
        Text(
            text = "$" + ((item.price ?: 0) * item.count).toString(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            ),
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}

@Composable
fun PopupOption(expanded: MutableState<Boolean>) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
            Text("Remove")
        }
    }
}

@Composable
fun ButtonCheckout(onClick: () -> Unit, totalPrice: Int, modifier: Modifier) {
    val isClick = remember { mutableStateOf(false) }
    Button(
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Black,
            contentColor = if (isClick.value) Color.Black else Color.LightGray
        ),
        modifier = modifier
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
            text = "Checkout $${totalPrice.toFloat()}".uppercase(),
            color = Color.LightGray,
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif
            ),
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartPreview() {
    val vm = koinViewModel<CartViewModel>()
    MyApplicationTheme {
        CartScreen(
            viewModel = vm,
            modifier = Modifier.padding()
        ) {}
    }
}
