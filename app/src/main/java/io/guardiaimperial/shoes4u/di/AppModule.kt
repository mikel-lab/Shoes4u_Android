package io.guardiaimperial.shoes4u.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.guardiaimperial.shoes4u.data.repository.AuthRepositoryImpl
import io.guardiaimperial.shoes4u.data.repository.ProductRepositoryImpl
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository
import io.guardiaimperial.shoes4u.domain.repository.ProductRepository
import io.guardiaimperial.shoes4u.utils.SharedPrefConstants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFireStoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImpl(auth, database,sharedPreferences, gson)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        database: FirebaseFirestore
    ): ProductRepository {
        return ProductRepositoryImpl(database)
    }


    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPrefConstants.LOCAL_SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}