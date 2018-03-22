package cityworld.sec04.fjarquellada.es.sec04cityworld.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cityworld.sec04.fjarquellada.es.sec04cityworld.R;
import cityworld.sec04.fjarquellada.es.sec04cityworld.model.City;
import io.realm.Realm;

public class AddEditCityActivity extends AppCompatActivity {

    private long cityId;
    private boolean isCreation;

    private City city;
    private Realm realm;
    @BindView(R.id.edit_text_city_name)
    EditText editTextCityName;
    @BindView(R.id.edit_text_city_description)
    EditText editTextCityDescription;
    @BindView(R.id.edit_text_city_image)
    EditText editTextCityLink;
    @BindView(R.id.imgage_view_preview)
    ImageView cityImage;
    @BindView(R.id.button_preview)
    Button btnPreview;
    @BindView(R.id.fab_edit_city)
    FloatingActionButton fab;
    @BindView(R.id.rating_bar_city)
    RatingBar ratingBarCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_city);

        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);

        // Comprobar si va a ser una acción para editar o para creación
        if (getIntent().getExtras() != null) {
            cityId = getIntent().getExtras().getLong("id");
            isCreation = false;
        } else {
            isCreation = true;
        }

        setActivityTitle();

        if (!isCreation) {
            city = getCityById(cityId);
            bindDataToFields();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditNewCity();
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = editTextCityLink.getText().toString();
                if (link.length() > 0)
                    loadImageLinkForPreview(editTextCityLink.getText().toString());
            }
        });

    }


    private void setActivityTitle() {
        String title = "Edit City";
        if (isCreation) title = "Create New City";
        setTitle(title);
    }

    private City getCityById(long cityId) {
        return realm.where(City.class).equalTo("id", cityId).findFirst();
    }

    private void bindDataToFields() {
        editTextCityName.setText(city.getName());
        editTextCityDescription.setText(city.getDescription());
        editTextCityLink.setText(city.getImage());
        loadImageLinkForPreview(city.getImage());
        ratingBarCity.setRating(city.getRanking());
    }

    private void loadImageLinkForPreview(String link) {
        Picasso.get().load(link)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .fit().into(cityImage);
    }

    private boolean isValidDataForNewCity() {
        if (editTextCityName.getText().toString().length() > 0 &&
                editTextCityDescription.getText().toString().length() > 0 &&
                editTextCityLink.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(AddEditCityActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void addEditNewCity() {
        if (isValidDataForNewCity()) {
            String name = editTextCityName.getText().toString();
            String description = editTextCityDescription.getText().toString();
            String link = editTextCityLink.getText().toString();
            int stars = (int) ratingBarCity.getRating();

            City city = new City(name, description, link, stars);
            // En caso de que sea una edición en vez de creación pasamos el ID
            if (!isCreation) city.setId(cityId);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(city);
            realm.commitTransaction();

            goToMainActivity();
        } else {
            Toast.makeText(this, "The data is not valid, please check the fields again", Toast.LENGTH_SHORT).show();
        }
    }

}
