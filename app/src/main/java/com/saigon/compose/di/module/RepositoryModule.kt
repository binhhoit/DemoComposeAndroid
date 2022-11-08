package com.saigon.compose.di.module

import com.saigon.compose.data.repository.ProductRepository
import com.saigon.compose.data.repository.ProductRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}
