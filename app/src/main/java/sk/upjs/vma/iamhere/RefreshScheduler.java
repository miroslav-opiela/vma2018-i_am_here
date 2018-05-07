package sk.upjs.vma.iamhere;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class RefreshScheduler {

    /**
     * Naplanuje pravidelne opakovanie spustania IntentService.
     */
    public static void schedule(Context context) {
        // systemovy alarm manager
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // intent spustajuci service
        Intent intent = new Intent(context, UserListService.class);
        // aktivita dava token alarm managerovi, aby mohol spustat service
        PendingIntent pendingIntent = PendingIntent.getService(context,
                0, intent, 0);

        // nastavenie opakovania kazdych 10 sekund
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(),
                1000 * 10, pendingIntent);

    }

}