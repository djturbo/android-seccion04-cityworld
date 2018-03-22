package cityworld.sec04.fjarquellada.es.sec04cityworld.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cityworld.sec04.fjarquellada.es.sec04cityworld.R;
import cityworld.sec04.fjarquellada.es.sec04cityworld.adapter.CityAdapter;
import cityworld.sec04.fjarquellada.es.sec04cityworld.adapter.CityClickListener;
import cityworld.sec04.fjarquellada.es.sec04cityworld.model.City;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements CityClickListener, View.OnClickListener, RealmChangeListener<RealmResults<City>> {

    String TAG = "MainActivity";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add_city)
    FloatingActionButton fabAddCity;

    private CityAdapter cityAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private RealmResults<City>cities;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* INIT DATA BASE */
        this.realm = Realm.getDefaultInstance();

        this.cities = findAllCities();
        cities.addChangeListener(this);
        ButterKnife.bind(this);

        this.fabAddCity.setOnClickListener(this);

        /* CREATE ADAPTER */
        this.cityAdapter = new CityAdapter(this.cities, R.layout.cardview_city_layout, this);
        this.layoutManager = new LinearLayoutManager(this);

        this.recyclerView.setAdapter(this.cityAdapter);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.setHideShowFAB();
    }

    private void setHideShowFAB() {
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fabAddCity.hide();
                else if (dy < 0)
                    fabAddCity.show();
            }
        });
    }

    private RealmResults<City> findAllCities(){
        return this.realm.where(City.class).findAll();
    }

    private void showAlertForRemovingCity(String title, String message, final City city) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteCity(city);
                        Toast.makeText(MainActivity.this, "It has been deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }


    private void deleteCity(City city) {
        realm.beginTransaction();
        city.deleteFromRealm();
        realm.commitTransaction();
    }
    @Override
    public void onCityClickHandler(City city) {
        Log.d(TAG, "clicada city: "+city.getName());
        // showAlertForRemovingCity("Eliminar Ciudad", "Cuidadito, que vas a eliminar la ciudad seleccionada", city);
        Intent intent = new Intent(MainActivity.this, AddEditCityActivity.class);
        intent.putExtra("id", city.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == this.fabAddCity.getId()){
            Log.d(TAG, "AÑADIR CITY");
            Intent intent = new Intent(MainActivity.this, AddEditCityActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onChange(RealmResults<City> cities) {
        cityAdapter.notifyDataSetChanged();
    }
}
