package com.fmahromi.lks_jabar

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val txtUsername = findViewById<TextInputEditText>(R.id.edt_username)
        val txtPassword = findViewById<TextInputEditText>(R.id.edt_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {

            val url = URL("http://116.193.191.179:3000/register")

            thread {
                with(url.openConnection() as HttpURLConnection) {
                    val username = txtUsername.text.toString()
                    val password = txtPassword.text.toString()

                    doOutput = true
                    doInput = true
                    requestMethod = "POST"
                    addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    with (outputStream.bufferedWriter()) {
                        write("username=${Uri.encode(username)}&password=${Uri.encode(password)}")
                        flush()

                    }
                    val result = inputStream.bufferedReader().readText()

                    // Fungsi Jika Berhasil
                    runOnUiThread {
                        Log.d("data", result)
                        val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                        intent.putExtra("status","berhasil Register")
                        startActivity(intent)
                    }
                }
            }

        }


    }
}