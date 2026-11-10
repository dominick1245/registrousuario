package com.example.registrousuario;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] tiposDocumento = new String[]{"Cédula", "Tarjeta de identidad", "Cédula de extranjería", "Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tiposDocumento);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(adapter);
        }

        // Día (1-31)
        String[] dias = new String[31];
        for (int i = 1; i <= 31; i++) {
            dias[i - 1] = String.valueOf(i);
        }
        ArrayAdapter<String> adapterDia = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dias);
        AutoCompleteTextView autoCompleteDia = findViewById(R.id.autoCompleteDia);
        if (autoCompleteDia != null) {
            autoCompleteDia.setAdapter(adapterDia);
        }

        // Mes
        String[] meses = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        ArrayAdapter<String> adapterMes = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, meses);
        AutoCompleteTextView autoCompleteMes = findViewById(R.id.autoCompleteMes);
        if (autoCompleteMes != null) {
            autoCompleteMes.setAdapter(adapterMes);
        }

        // Año (1940 - 2025)
        String[] anios = new String[86];
        for (int i = 0; i < 86; i++) {
            anios[i] = String.valueOf(2025 - i);
        }
        ArrayAdapter<String> adapterAnio = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, anios);
        AutoCompleteTextView autoCompleteAnio = findViewById(R.id.autoCompleteAnio);
        if (autoCompleteAnio != null) {
            autoCompleteAnio.setAdapter(adapterAnio);
        }
    }

    /**
     * Valida una cédula ecuatoriana (10 dígitos, provincia 01-24 o 30, tercer dígito < 6 y algoritmo módulo 10).
     */
    public boolean isValidEcuadorianCedula(String cedula) {
        if (cedula == null || cedula.length() != 10) return false;
        try {
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if ((provincia < 1 || provincia > 24) && provincia != 30) return false;

            int tercerDigito = Character.getNumericValue(cedula.charAt(2));
            if (tercerDigito < 0 || tercerDigito > 5) return false;

            int suma = 0;
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            for (int i = 0; i < 9; i++) {
                int digito = Character.getNumericValue(cedula.charAt(i));
                int producto = digito * coeficientes[i];
                if (producto >= 10) {
                    producto -= 9;
                }
                suma += producto;
            }

            int digitoVerificadorCalculado = suma % 10 == 0 ? 0 : 10 - (suma % 10);
            int digitoVerificadorReal = Character.getNumericValue(cedula.charAt(9));

            return digitoVerificadorCalculado == digitoVerificadorReal;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}