package com.saigon.compose.data.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseDatabase> {
        Firebase.initialize(get())
        Firebase.database
    }
}
