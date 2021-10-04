package com.example.jsonapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var convertedTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var euroEditText: EditText
    lateinit var spinner: Spinner
    lateinit var convertButton: Button

    var selected = 0
    private var currency : Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        convertedTextView = findViewById(R.id.tv_converted)
        dateTextView = findViewById(R.id.tv_date)
        euroEditText = findViewById(R.id.edt_euro)
        spinner = findViewById(R.id.spinner)
        convertButton = findViewById(R.id.btn_convert)

        ArrayAdapter.createFromResource(
            this,
            R.array.curr_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selected = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "please select a currency", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        convertButton.setOnClickListener {
            try {
                var euroInput = euroEditText.text.toString().toDouble()
                getCurrency(onResult = {
                    currency = it
                    dateTextView.text = currency?.date.toString()
                    if(selected==0){
                        val currencyRate = currency?.eur?.sar?.toDouble()
                        var converted = String.format("%.3f", euroInput * currencyRate!!)
                        convertedTextView.text = "$converted SAR"
                    }else  if(selected==1){
                        val currencyRate = currency?.eur?.usd?.toDouble()
                        var converted = String.format("%.3f", euroInput * currencyRate!!)
                        convertedTextView.text = "$converted USD"
                    }else  if(selected==2){
                        val currencyRate = currency?.eur?.cad?.toDouble()
                        var converted = String.format("%.3f", euroInput * currencyRate!!)
                        convertedTextView.text = "$converted CAD"
                    }else  if(selected==3){
                        val currencyRate = currency?.eur?.jpy?.toDouble()
                        var converted = String.format("%.3f", euroInput * currencyRate!!)
                        convertedTextView.text = "$converted JPY"
                    }
                })
            } catch (e: Exception){
                Toast.makeText(this@MainActivity, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrency(onResult: (Currency?)->Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.getCurrency()?.enqueue(object : Callback<Currency> {
                override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                    Log.d("MainActivity", response.body().toString())
                    onResult(response.body())
                }
                override fun onFailure(call: Call<Currency>, t: Throwable) {
                    onResult(null)
                    Log.d("MainActivity", "failed to call: ${t.message}")
                    Toast.makeText(this@MainActivity, "something went wrong", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}