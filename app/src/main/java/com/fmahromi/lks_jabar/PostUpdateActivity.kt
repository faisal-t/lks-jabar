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

class PostUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_update)

        val btnAddPost = findViewById<Button>(R.id.btn_post_edit)
        val edtName = findViewById<TextInputEditText>(R.id.edt_post_name)
        val edtDescription = findViewById<TextInputEditText>(R.id.edt_post_description)
        val edtPrice = findViewById<TextInputEditText>(R.id.edt_post_price)

        val idMenu = intent.getStringExtra("id") ?: null
        val jenis = intent.getStringExtra("jenis") ?: null

        if (jenis == "post"){
            btnAddPost.setOnClickListener {
                val url = URL("http://116.193.191.179:3000/menu")

                thread {
                    with(url.openConnection() as HttpURLConnection) {
                        val name = edtName.text.toString()
                        val desc = edtDescription.text.toString()
                        val price = edtPrice.text.toString()


                        doOutput = true
                        doInput = true
                        requestMethod = "POST"
                        addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                        addRequestProperty("Authorization", MainActivity.token.toString())
                        with (outputStream.bufferedWriter()) {
                            write("name=${Uri.encode(name)}&description=${Uri.encode(desc)}&price=${Uri.encode(price)}")
                            flush()

                        }
                        val result = inputStream.bufferedReader().readText()

                        // Fungsi Jika Berhasil
                        runOnUiThread {
                            Log.d("data", result)
                            val intent = Intent(this@PostUpdateActivity, ActivityMenu::class.java)
                            intent.putExtra("status","berhasil tambah Menu")
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        else{
            btnAddPost.setOnClickListener {
                val url = URL("http://116.193.191.179:3000/menu/$idMenu")

                thread {
                    with(url.openConnection() as HttpURLConnection) {
                        val name = edtName.text.toString()
                        val desc = edtDescription.text.toString()
                        val price = edtPrice.text.toString()


                        doOutput = true
                        doInput = true
                        requestMethod = "PUT"
                        addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                        addRequestProperty("Authorization", MainActivity.token.toString())
                        with (outputStream.bufferedWriter()) {
                            write("name=${Uri.encode(name)}&description=${Uri.encode(desc)}&price=${Uri.encode(price)}")
                            flush()

                        }
                        val result = inputStream.bufferedReader().readText()

                        // Fungsi Jika Berhasil
                        runOnUiThread {
                            Log.d("data", result)
                            val intent = Intent(this@PostUpdateActivity, ActivityMenu::class.java)
                            intent.putExtra("status","berhasil Edit Menu")
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}