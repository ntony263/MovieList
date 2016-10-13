package coderschool.movielist.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import coderschool.movielist.R;

public class MainActivity extends AppCompatActivity {
    private ListView lvMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMovie = findViewById(R.id.lvMovie);

    }
}
