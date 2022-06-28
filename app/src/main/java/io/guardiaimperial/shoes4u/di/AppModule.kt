package io.guardiaimperial.shoes4u.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.guardiaimperial.shoes4u.data.repository.AuthRepository
import io.guardiaimperial.shoes4u.data.repository.AuthRepositoryInterface

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFireStoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth
    ): AuthRepositoryInterface {
        return AuthRepository(auth, database)
    }
}