package io.guardiaimperial.shoes4u.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.utils.FireStoreCollection
import io.guardiaimperial.shoes4u.domain.model.Response
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository

/** Pasamos como parámentros a la clase las dependencias de FirebaseAuth y FirebaseFireStore */
class AuthRepositoryImpl(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore
) : AuthRepository {

    /** Llamamos a la función "createUserWithEmailAndPassword", incluida en la dependencia
    FirebaseAuth, y le pasamos los parámetros email y password. */
    override fun registerUser(
        email: String,
        password: String,
        user: User, result: (Response<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            /** Comprueba si el proceso se ha completado o no. Si ha tenido exito devuelve
             * la cadena "User register successfully", sino lanza distintas excepciones.*/
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUserInfo(user) { state ->
                        when (state) {
                            is Response.Success -> {
                                result.invoke(
                                    Response.Success("User register sucessfully")
                                )
                            }
                            is Response.Failure -> {
                                result.invoke(Response.Failure(state.error))
                            }
                            else -> {}
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authenticacion")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(Response.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(Response.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(Response.Failure("Authenticacion failed, Email already registered"))
                    } catch (e: Exception) {
                        result.invoke(Response.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    Response.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    /** Actualiza los datos de usuario dentro de la colección de usuarios de FireStore */
    override fun updateUserInfo(user: User, result: (Response<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document()
        user.id = document.id
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(
                    Response.Success("User has been updated successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Response.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun loginUser(email: String, password: String, result: (Response<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(Response.Success("Login successfully"))
                }
            }.addOnFailureListener {
                result.invoke(
                    Response.Failure("Authentication failed, Check email and password")
                )
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
                    Response.Failure("Authentication failed, Check email")
                )
            }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }
}