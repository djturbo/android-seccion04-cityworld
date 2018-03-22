package cityworld.sec04.fjarquellada.es.sec04cityworld;

import android.app.Application;

import java.util.concurrent.atomic.AtomicLong;

import cityworld.sec04.fjarquellada.es.sec04cityworld.model.City;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by francisco on 21/3/18.
 */

public class CustomApplication extends Application {
    public static AtomicLong CITY_ID = new AtomicLong();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        this.setupRealm();

        Realm realm = Realm.getDefaultInstance();

        CITY_ID = getIdByTable(realm, City.class);

        realm.close();
    }

    private void setupRealm(){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicLong getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results =  realm.where(anyClass).findAll();
        return (results.size() > 0 ) ? new AtomicLong(results.max("id").longValue()) : new AtomicLong();

    }
}
