package com.example.autenticaofirebase2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

data class UserModelo (
    val email:String,
    val senha:String
)

class MainActivity : AppCompatActivity() {
    lateinit var btCadastrar: Button
    lateinit var btLogin: Button
    lateinit var etEmail: EditText
    lateinit var etSenha: EditText

    private val autentication by lazy { FirebaseAuth.getInstance()
    }
    fun cadastrar() {
        var user = UserModelo (
            etEmail.text.toString(),
            etSenha.text.toString())
        autentication.createUserWithEmailAndPassword(
            user.email, user.senha).addOnSuccessListener { Toast.makeText(this, "cadastrado com sucesso!", Toast.LENGTH_LONG).show()
            abrirInicio()
        }.addOnFailureListener { Toast.makeText(this, "erro ao efetuar o cadastro", Toast.LENGTH_LONG).show()
        }
    }

    private fun abrirInicio() {
        val intent = Intent(this, Inicio::class.java)
        startActivity(intent)

    }

    fun login() {
        var user = UserModelo(
            etEmail.text.toString(),
            etSenha.text.toString())

        autentication.signInWithEmailAndPassword(
            user.email, user.senha).addOnSuccessListener { Toast.makeText(this, "logado com sucesso!", Toast.LENGTH_LONG).show()
            abrirInicio()
        }.addOnFailureListener { Toast.makeText(this, "erro ao efetuar o login", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.etEmail)
        etSenha = findViewById(R.id.etSenha)
        btCadastrar = findViewById(R.id.btCadastrar)
        btLogin = findViewById(R.id.btLogin)

        btLogin.setOnClickListener { if(etEmail.text.toString().isNullOrEmpty() || etSenha.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "preencha todos os campos", Toast.LENGTH_LONG).show()
        } else {
            login()
        }
        }
        btCadastrar.setOnClickListener { if(etEmail.text.toString().isNullOrEmpty() || etSenha.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "preencha todos os campos", Toast.LENGTH_LONG).show()
        } else {
            cadastrar()
        }
        } }

    override fun onStart() {
        super.onStart()
        verificarLogin()
    }

    private fun verificarLogin() {
        val user = autentication.currentUser
        if (user != null) {
            abrirInicio()
        }
    }
}

