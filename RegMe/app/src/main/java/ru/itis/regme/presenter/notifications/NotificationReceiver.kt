package ru.itis.regme.presenter.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import ru.itis.regme.MainActivity
import ru.itis.regme.R

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        createNotification(context!!)
    }

    private fun createNotification(context: Context) {
        var nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var ch = NotificationChannel("14", "my", NotificationManager.IMPORTANCE_DEFAULT)
            nm.createNotificationChannel(ch)
        }
        val intent = Intent(context, MainActivity::class.java)
        var pi = NavDeepLinkBuilder(context)
//            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.root_nav_graph)
            .setDestination(R.id.numbers)
            .setArguments(null)
            .createPendingIntent()
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        var n = NotificationCompat.Builder(context, "14")
            .setSmallIcon(android.R.drawable.ic_menu_edit)
            .setContentTitle("Reg Me")
            .setContentText("Нажмите, чтобы отправить сообщения клиентам")
            .setContentIntent(pi)
            .setAutoCancel(true)
        var notManagerCompat = NotificationManagerCompat.from(context)
        notManagerCompat.notify(14, n.build())
    }
}