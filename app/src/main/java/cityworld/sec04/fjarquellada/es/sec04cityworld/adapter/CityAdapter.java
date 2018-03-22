package cityworld.sec04.fjarquellada.es.sec04cityworld.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cityworld.sec04.fjarquellada.es.sec04cityworld.R;
import cityworld.sec04.fjarquellada.es.sec04cityworld.activity.MainActivity;
import cityworld.sec04.fjarquellada.es.sec04cityworld.model.City;

/**
 * Created by francisco on 21/3/18.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<City>cities;
    private int layout;
    private MainActivity context;

    public CityAdapter(List<City> cities, int layout, MainActivity context) {
        this.cities = cities;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = this.cities.get(position);
        holder.bind(city, (CityClickListener) this.context);
    }

    @Override
    public int getItemCount() {
        return this.cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image_view_city_image)ImageView imgViewCity;
        @BindView(R.id.text_view_city_name)TextView txtVwCityName;
        @BindView(R.id.text_view_city_ranking)TextView txtVwCityRanking;
        @BindView(R.id.text_view_city_description)TextView txtVwCityDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            ));
        }

        public void bind(final City city, final CityClickListener listener){
            Picasso.get().load(city.getImage())
                    .fit()
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder_error)
                    .into(this.getImgViewCity());
            this.imgViewCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCityClickHandler(city);
                }
            });
            this.getTxtVwCityDescription().setText(city.getDescription());
            this.getTxtVwCityName().setText(city.getName());
            this.getTxtVwCityRanking().setText(city.getRanking() +"");
        }

        public ImageView getImgViewCity() {
            return imgViewCity;
        }

        public void setImgViewCity(ImageView imgViewCity) {
            this.imgViewCity = imgViewCity;
        }

        public TextView getTxtVwCityName() {
            return txtVwCityName;
        }

        public void setTxtVwCityName(TextView txtVwCityName) {
            this.txtVwCityName = txtVwCityName;
        }

        public TextView getTxtVwCityRanking() {
            return txtVwCityRanking;
        }

        public void setTxtVwCityRanking(TextView txtVwCityRanking) {
            this.txtVwCityRanking = txtVwCityRanking;
        }

        public TextView getTxtVwCityDescription() {
            return txtVwCityDescription;
        }

        public void setTxtVwCityDescription(TextView txtVwCityDescription) {
            this.txtVwCityDescription = txtVwCityDescription;
        }
    }
}
