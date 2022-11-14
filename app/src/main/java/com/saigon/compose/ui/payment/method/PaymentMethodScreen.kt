package com.saigon.compose.ui.payment.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.saigon.compose.R
import com.saigon.compose.data.model.PaymentMethodCustomer
import com.saigon.compose.data.model.PaymentMethods
import com.saigon.compose.data.model.image
import com.saigon.compose.ui.theme.MyApplicationTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PaymentMethodsScreen(
    viewModel: PaymentMethodViewModel,
    modifier: Modifier,
    destination: (String) -> Unit
) {
    viewModel.getPaymentMethods()
    val result = viewModel.paymentMethodsState.value
    when {
        result.isLoading -> {
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = modifier.align(alignment = Alignment.Center))
            }
        }
        else -> {
            Column(
                modifier = modifier
                    .padding(
                        horizontal = 15.dp,
                        vertical = 15.dp
                    )
            ) {
                Title()
                if (result.paymentMethods.data.isNotEmpty()) {
                    BodyPaymentMethods(result.paymentMethods)
                }
                ButtonAddCard(destination)
            }
        }
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(R.string.debit_card),
        style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace
        ),
        color = Color.Black,
        fontSize = 13.sp,
        modifier = Modifier.padding(vertical = 5.dp)
    )
}

@Composable
fun BodyPaymentMethods(paymentMethods: PaymentMethods) {
    Divider(color = Color.LightGray, thickness = 1.dp)
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(5.dp)
    ) {
        items(paymentMethods.data.size) { index ->
            ItemCard(paymentMethod = paymentMethods.data[index])
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemCard(paymentMethod: PaymentMethodCustomer) {
    val expanded by remember { mutableStateOf(mutableStateOf(false)) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = paymentMethod.image(),
            contentDescription = stringResource(R.string.add_payment_method),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(40.dp)
                .defaultMinSize(minHeight = 40.dp)
        )

        Column {
            Text(
                text = paymentMethod.card.brand,
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Black,
                fontSize = 13.sp
            )

            Text(
                text = "**** **** **** " + paymentMethod.card.last4,
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Black,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.fillMaxWidth(0.3f))

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.LightGray
        )

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = Color.LightGray
            )
            PopupOption(expanded)
        }
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
        Divider()
        DropdownMenuItem(onClick = { /* Handle settings! */ }) {
            Text("Set Default")
        }
    }
}

@Composable
fun ButtonAddCard(destination: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { destination.invoke("new Page") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AddCard,
            contentDescription = null,
            tint = Color.LightGray
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.1f))

        Text(
            text = stringResource(R.string.add_payment_method),
            style = TextStyle(
                fontWeight = FontWeight.ExtraLight,
                fontFamily = FontFamily.Monospace
            ),
            color = Color.LightGray,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentMethodsPreview() {
    val vm = koinViewModel<PaymentMethodViewModel>()
    MyApplicationTheme {
        PaymentMethodsScreen(
            viewModel = vm,
            modifier = Modifier.padding()
        ) {}
    }
}
