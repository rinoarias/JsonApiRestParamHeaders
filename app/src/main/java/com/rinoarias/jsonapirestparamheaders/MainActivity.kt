package com.rinoarias.jsonapirestparamheaders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtResult = findViewById<TextView>(R.id.txtResultado)

        val queue = Volley.newRequestQueue(this)
        val url = "https://pay.payphonetodoesposible.com/api/Regions"

        val apiKey = "cKKUvHRmjPybq5Atw2GMPpOAa5jbAqQ0yT-1D7v-pNrgEv4curAd7LUWxrmKzB_hNkmivQf4v_VQuuIPwgGQwVbav5f6KXRfvNEo8tp_h8WHcVnEJ2aUFYObzhohbVUXTISdmISJy1aNhM3vHqSECcJeC-eOQRRS6jaXGo3EwyG7bBhWtrFuytfrFchJdY7-0_uMOx7HJzhip20at5NZldFXrgOD9UCYqCL5eIKWE1xMB1DahpHhumJ7R8xs_xsbjI79ZiCnc0N21mcg-iYhu-TxxosSNWFkEwTu5wYt76U2xZjFqcxg8cOOcViCx_bYsXlDig"

        getRegions(queue,url, txtResult, apiKey)

    }


    fun getRegions(queue: RequestQueue, url: String, control: TextView, apiKey: String){
        val arrayRequest = object: JsonArrayRequest(Request.Method.GET, url, null,
            {
                response ->
                    run {
                        control.text=""
                        for (i in 0 until response.length()){
                            var country = response.getJSONObject(i)
                            var countryName = country.getString("name")
                            var countryPrefixNumber = country.getInt("prefixNumber")
                            var countryIso = country.getString("iso")
                            //control.append("No. Pais: ${i+1}\n")
                            control.append("Pais: ${countryName}\n")
                            control.append("Codigo Pais: +${countryPrefixNumber}\n")
                            control.append("ISO Pais: ${countryIso}\n\n")
                        }
                    }
            },
            { error -> run {
                Toast.makeText(applicationContext,
                    "Error al obtener los datos",
                    Toast.LENGTH_LONG).show()
            }}
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $apiKey"
                return headers
            }
        }
        queue.add(arrayRequest)
    }
}