package com.example.mylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.model.OnListClickListener
import com.example.mylist.model.Product
import com.example.mylist.model.ProductDao

class MainActivity : AppCompatActivity(), OnListClickListener {

    private lateinit var adapter: MainAdapter
    private lateinit var result: MutableList<Product>
    private lateinit var rvMain: RecyclerView
    private lateinit var btn: Button
    private lateinit var vlTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_add)
        vlTotal = findViewById(R.id.txt_vltotal)

        btn.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        result = mutableListOf<Product>()
        adapter = MainAdapter(result, this)


        rvMain = findViewById(R.id.rv_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = adapter


        Thread {
            val app = application as App
            val dao = app.db.product()
            val response = dao.getRegister()
            val soma = String.format("%.2f", dao.getTotalValue())
            vlTotal.text = soma

            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()


    }

    override fun onClick(id: Int) {
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra("updateId", id)
        startActivity(intent)
        finish()
    }

    override fun onLongClick(position: Int, product: Product) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(android.R.string.cancel) { dialog, which ->
            }
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Thread {
                    val app = application as App
                    val dao = app.db.product()

                    val response = dao.delete(product)

                    if (response > 0) {
                        runOnUiThread {
                            result.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                    }
                }.start()

            }
            .create()
            .show()
    }

    private inner class MainAdapter(
        private val listProduct: List<Product>,
        private val listener: OnListClickListener
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.items, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val currentItem = listProduct[position]
            holder.bind(currentItem)
        }

        override fun getItemCount(): Int {
            return listProduct.size
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Product) {

                val container: LinearLayout = itemView.findViewById(R.id.linear_container)
                val name: TextView = itemView.findViewById(R.id.txt_product_name)
                val quantity: TextView = itemView.findViewById(R.id.txt_product_quant)
                val value: TextView = itemView.findViewById(R.id.txt_product_value)
                val totalValue: TextView = itemView.findViewById(R.id.txt_product_total_value)

                val qtd = item.quantity
                val vl = item.unitValue
                val totalVl = item.totalValue

                name.text = item.productName
                quantity.text = getString(R.string.result_int, qtd)
                value.text = getString(R.string.result_double, vl)
                totalValue.text = getString(R.string.result_double, totalVl)

                container.setOnLongClickListener {
                    listener.onLongClick(adapterPosition, item)
                    true
                }

                container.setOnClickListener {
                    listener.onClick(item.id)
                }

            }
        }

    }
}