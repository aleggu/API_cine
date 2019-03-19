package mx.edu.ittepic.api_cine;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView datos;
    EditText titulo;
    Button buscar;
    ImageView imagen, estrellas;
    //String url="https://www.imagen.com.mx/assets/img/imagen_share.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datos=findViewById(R.id.datos);
        titulo=findViewById(R.id.nombre);
        buscar=findViewById(R.id.buscar);
        imagen=findViewById(R.id.imagePoster);
        estrellas=findViewById(R.id.puntuacion);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPelicula();
            }
        });

    }

    private void cargarImagen(String url) {
        Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).into(imagen);
    }

    private void buscarPelicula() {
        String url ="http://www.omdbapi.com/?apikey=3de8da5c&t="+titulo.getText().toString()+"&plot=full";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    String titulo = response.getString("Title");
                    String year = response.getString("Year");
                    String duracion = response.getString("Runtime");
                    String director = response.getString("Director");
                    String url =response.getString("Poster");
                    String puntuacion = response.getString("imdbRating");

                    //Toast.makeText(getApplicationContext(),poster, Toast.LENGTH_LONG).show();
                    datos.setText("Nombre:  "+titulo+"\n"
                            +"Año:  "+year+"\n"
                            +"Duración:  "+duracion+"\n"
                            +"Director:  "+director+"\n"
                            +"Puntuacion:  "+puntuacion+"\n");

                    float numero=Float.parseFloat(puntuacion);
                    if(numero>0 && numero <2)
                    {
                        estrellas.setImageResource(R.drawable.imagenuno);
                    }
                    if(numero>2 && numero <4)
                    {
                        estrellas.setImageResource(R.drawable.imagendos);
                    }
                    if(numero>4 && numero <6)
                    {
                        estrellas.setImageResource(R.drawable.imagentres);
                    }
                    if(numero>6 && numero <8)
                    {
                        estrellas.setImageResource(R.drawable.imagencuatro);
                    }
                    if(numero>8 && numero <10)
                    {
                        estrellas.setImageResource(R.drawable.imagencinco);
                    }

                    cargarImagen(url);


                }catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);


    }


    }
