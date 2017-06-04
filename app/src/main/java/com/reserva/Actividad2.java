package com.reserva;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Actividad2 extends Activity {

	String nombre = "", fecha = "", hora = "";
	int personas = 0;
	TextView muestraDatos;
	boolean ServicioValetP=false;
	CheckBox servicio;
	String servicioContratado="Ninguno";
	Spinner intenciones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad2);

		intenciones=(Spinner)findViewById(R.id.acciones);
		servicio=(CheckBox) findViewById(R.id.servicioValet);
		muestraDatos = (TextView) findViewById(R.id.muestraDatos);

		Bundle recibe = new Bundle();
		recibe = this.getIntent().getExtras();

		nombre = recibe.getString("nombre");
		personas = recibe.getInt("personas");
		fecha = recibe.getString("fecha");
		hora = recibe.getString("hora");

		muestraDatos.setText("Reservacion a nombre de:\n" + nombre + "\n" + personas
				+ " personas\nFecha: " + fecha + "\nHora: " + hora + "\n");
		intenciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if(i!=0){
					switch(i){
						case 1:
							mandarCorreo();
							break;
						case 2:
							googleMaps();
							break;
						case 3:
							abrirPaginaWeb();
							break;
						case 4:
							llamadaTelefono();
							break;
					}
				}else
				{
					Toast.makeText(getApplicationContext(),"Puedes seleccionar una acción",Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

	}

	public void abrirPaginaWeb()
	{
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://italiannis.com.mx/"));
		startActivity(intent);
	}

	public void llamadaTelefono()
	{
		Intent intent = new Intent(Intent.ACTION_CALL,
				Uri.parse("tel:015510002370"));
		startActivity(intent);
	}
	public void mandarCorreo()
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Asunto: Correo Italliani's");
		intent.putExtra(Intent.EXTRA_TEXT, "Contenido del correo: Esta es una prueba de correo.");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "itallianiscdmx@gmail.com"} );
		startActivity(intent);
	}

	public void googleMaps()
	{
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("geo:19.58386, -99.02276"));
		startActivity(intent);
	}
	public void onClickServicio(View v){
		ServicioValetP=((CheckBox)v).isChecked();
		if(ServicioValetP)
			servicioContratado="Valet Parking";
		else
			servicioContratado="Ninguno";
	}
    public void hacerOtraReserva(View v) {
        Intent envia = new Intent(this, MainActivity.class);
		Bundle regreso=new Bundle();
		regreso.putString("ServicioExtra",servicioContratado);
		envia.putExtras(regreso);
        finish();
        startActivity(envia);
    }

}
