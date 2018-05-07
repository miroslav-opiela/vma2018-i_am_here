package sk.upjs.vma.iamhere;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;

/**
 * Service ktory stahuje zoznam pouzivatelov.
 */
public class UserListService extends IntentService {

    /**
     * Oznacenie action v ramci intentu.
     */
    protected static final String USER_LIST_INTENT_ACTION = "user list intent";

    /**
     * Oznacenie extra v ramci intentu, kde je ulozeny zoznam pouzivatelov.
     */
    protected static final String USER_LIST_EXTRA = "user list";

    /**
     * Bezparametricky konstruktor - vola rodicovsky s jednym parametrom.
     */
    public UserListService() {
        super(UserListService.class.getName());
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("SERVICE", "on start");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SERVICE", "on destroy");
    }

    /**
     * Pri spracovani intentu sa stiahne zoznam pouzivatelov pomocou REST API,
     * zobrazi sa notifikacia a posle intent sprava na local broadcast az k aktivte.
     *
     * @param intent intent ktory spusta service.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SERVICE", "handle intent");
        try {
            // retrofit REST API
            Api api = RetrofitFactory.getApi();
            Call<List<User>> call = api.getUsers();
            List<User> users = call.execute().body();
            // notifikacia
            triggerNotification(users);
            // broadcast sprava
            broadcast(users);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void triggerNotification(List<User> users) {
        // builduje sa notifikacia
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Here I am")
                .setContentText("Number of users in the room: " + users.size() +
                        ". Last logged user: " + users.get(users.size() - 1))
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        // ziska sa systemovy notification manager
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // zobrazi sa notifikacia
        notificationManager.notify(0, notification);

    }

    private void broadcast(List<User> users) {
        // intent ktory sa posiela na broadcast
        Intent intent = new Intent(USER_LIST_INTENT_ACTION);
        // intent obsahuje zoznam pouzivatelov ako extra. castovanie listu na serializable
        intent.putExtra(USER_LIST_EXTRA, (Serializable) users);

        // lokalny broadcast manager na odosielanie broadcastov
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(intent);
    }
}