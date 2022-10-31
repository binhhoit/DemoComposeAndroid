package com.saigon.compose.ui.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.saigon.compose.utils.ScaffoldExt

@Composable
fun ProfileScreen(modifier: Modifier) {
    ScaffoldExt(title = "PROFILE") {
        Text(text = "Profile")
    }
}
