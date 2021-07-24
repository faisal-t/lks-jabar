package com.fmahromi.lks_jabar


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    companion object {
        var token : String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtUsername = findViewById<TextInputEditText>(R.id.edt_username)
        val txtPassword = findViewById<TextInputEditText>(R.id.edt_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        val status = intent.getStringExtra("status");
        if (status != null){
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }




        btnRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }




        btnLogin.setOnClickListener {
            val url = URL("http://116.193.191.179:3000/login")

            thread {
                with(url.openConnection() as HttpURLConnection) {
                    val username = txtUsername.text.toString()
                    val password = txtPassword.text.toString()



                    requestMethod = "POST"
                    addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    with (outputStream.bufferedWriter()) {
                        write("username=${Uri.encode(username)}&password=${Uri.encode(password)}")
                        flush()

                    }

                    if (responseCode == 200){
                        val result = inputStream.bufferedReader().readText()
                        val data = JSONObject(result)

                        runOnUiThread {
                            token = "Bearer " + data["token"].toString()
                            val intent = Intent(this@MainActivity,ActivityMenu::class.java)
                            startActivity(intent)

                        }
                    }
                    else{

                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                        }

                    }


                }
            }
        }


    }



}