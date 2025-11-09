package de.r3chn3n.Rechenpate2App;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton bDeleteCircle;
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintView = findViewById(R.id.paint_view);
        bDeleteCircle = findViewById(R.id.btnDeleteCircle);

        // FloatingActionButton-Clicklistener
        bDeleteCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.deleteSquares();  // Aktion beim Klick
            }
        });
    }
}
