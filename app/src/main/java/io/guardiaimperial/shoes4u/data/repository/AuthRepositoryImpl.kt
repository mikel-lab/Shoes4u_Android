package io.guardiaimperial.shoes4u.data.repository

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.utils.FireStoreCollection
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository
import io.guardiaimperial.shoes4u.utils.SharedPrefConstants

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore,
    val sharedPreferences: SharedPreferences,
    val gson: Gson
) : AuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        user: User, result: (Response<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.id = it.result.user?.uid ?: ""
                    updateUserInfo(user) { state ->
                        when(state){
                            is Response.Success -> {
                                storeSession(id = it.result.user?.uid ?: "") {
                                    if (it == null){
                                        result.invoke(Response.Failure("Usuario registrado, pero no se ha guardado la sesión"))
                                    }else{
                                        result.invoke(Response.Success("Usuario registrado"))
                                    }
                                }
                            }
                            is Response.Failure -> {
                                result.invoke(Response.Failure(state.error))
                            }
                            is Response.Loading -> {

                            }
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(Response.Failure("Autenticación fallida, la contraseña debe tener al menos 6 caracteres"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(Response.Failure("Autenticación fallida, has introducido un email incorrecto"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(Response.Failure("Autenticación fallid, el email ya existe"))
                    } catch (e: Exception) {
                        result.invoke(Response.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(Response.Failure(it.localizedMessage))
            }
    }

    /** Actualiza los datos de usuario dentro de la colección de usuarios de FireStore */
    override fun updateUserInfo(user: User, result: (Response<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document(user.id)
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(Response.Success("Usuario actualizado con exito"))
            }
            .addOnFailureListener {
                result.invoke(Response.Failure(it.localizedMessage))
            }
    }

    override fun loginUser(email: String, password: String, result: (Response<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storeSession(id = task.result.user?.uid ?: "") {
                        if (it == null){
                            result.invoke(Response.Failure("Fallo al guardar la sesión local"))
                        }else{
                            result.invoke(Response.Success("Has iniciado sesión"))
                        }
                    }
                }
            }.addOnFailureListener {
                result.invoke(Response.Failure("Autenticación fallida, comprueba tu email y contraseña"))
            }
    }

    override fun forgotPassword(email: String, result: (Response<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(Response.Success("Email has been sent"))
                } else {
                    result.invoke(Response.Failure(task.exception?.message))
                }
            }.addOnFailureListener {
                result.invoke(
                    Response.Failure("Autenticación fallida, comprueba el email"))
            }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        sharedPreferences.edit().putString(SharedPrefConstants.USER_SESSION,null).apply()
        result.invoke()
    }

    override fun storeSession(id: String, result: (User?) -> Unit) {
        database.collection(FireStoreCollection.USER).document(id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val user = it.result.toObject(User::class.java)
                    sharedPreferences.edit().putString(SharedPrefConstants.USER_SESSION,gson.toJson(user)).apply()
                    result.invoke(user)
                }else{
                    result.invoke(null)
                }
            }
            .addOnFailureListener {
                result.invoke(null)
            }
    }

    override fun getSession(result: (User?) -> Unit) {
        val userStr = sharedPreferences.getString(SharedPrefConstants.USER_SESSION,null)
        if (userStr == null){
            result.invoke(null)
        }else{
            val user = gson.fromJson(userStr,User::class.java)
            result.invoke(user)
        }
    }
}