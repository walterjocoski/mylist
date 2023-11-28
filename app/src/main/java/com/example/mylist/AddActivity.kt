package com.example.mylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mylist.model.Product

class AddActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editQtd: EditText
    private lateinit var editPrice: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editName = findViewById(R.id.edit_name)
        editQtd = findViewById(R.id.edit_quantity)
        editPrice = findViewById(R.id.edit_price)

        val btnConfirm: Button = findViewById(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val name = editName.text.toString()
            val qtd = editQtd.text.toString().toInt()
            val price = editPrice.text.toString().toDouble()
            val total = qtd * price

            AlertDialog.Builder(this)
                .setTitle(R.string.title_dialog)
                .setMessage(getString(R.string.dialog_preview, name, qtd, price))
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, which ->

                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.product()

                        val updatedId = intent.extras?.getInt("updateId")
                        if (updatedId != null) {
                            dao.update(
                                Product(
                                    id = updatedId,
                                    productName = name,
                                    quantity = qtd,
                                    unitValue = price,
                                    totalValue = total,
                                    type = "true"
                                )
                            )
                        } else {
                            dao.insert(
                                Product(
                                    productName = name,
                                    quantity = qtd,
                                    unitValue = price,
                                    totalValue = total,
                                    type = "true"
                                )
                            )
                        }
                        runOnUiThread {
                            openMainActivity()
                        }
                    }.start()
                }
                .create()
                .show()

        }
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun validate(): Boolean {
        return (editName.text.toString().isNotEmpty()
                && editQtd.text.toString().isNotEmpty()
                && editPrice.text.toString().isNotEmpty()
                && !editQtd.text.toString().startsWith("0"))
    }
}