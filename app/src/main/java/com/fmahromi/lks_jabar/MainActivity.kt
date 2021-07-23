package com.fmahromi.lks_jabar


import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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
//            val intent = Intent(this,RegisterActivity::class.java)
//            startActivity(intent)

            val url = URL("http://116.193.191.179:3000/menu/3")


            thread {
                with(url.openConnection() as HttpURLConnection) {
                    addRequestProperty("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRob3JpemVkIjp0cnVlLCJ1c2VyX2lkIjoiMiIsInVzZXJuYW1lIjoiYWRtaW4yIn0.kQn3BFuXAuBgB6W1FQQQ1SZhPIgplfFSiVXiT07LIng")
                    val result = inputStream.bufferedReader().readText()

                    //get LIS item
//                    val a = JSONArray(result)
//                    for (i in 0..a.length()) {
//                        val item = a.getJSONObject(i)
//                        val price = item["price"].toString()
//                    }

                    //get one item
                    val a = JSONObject(result)

                    // Fungsi Jika Berhasil
                    runOnUiThread {
                        Log.d("data", result)
                        Toast.makeText(this@MainActivity, a.get("name").toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }




        btnLogin.setOnClickListener {
            val url = URL("http://116.193.191.179:3000/login")

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

                    val data = JSONObject(result)

//                    Sample


                    // Fungsi Jika Berhasil
                    runOnUiThread {
                        token = "Bearer " + data["token"].toString()
                        Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }


    }



}