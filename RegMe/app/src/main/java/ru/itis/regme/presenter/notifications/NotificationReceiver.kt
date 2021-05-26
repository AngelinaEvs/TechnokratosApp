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
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel("14", "my", NotificationManager.IMPORTANCE_DEFAULT)
            nm.createNotificationChannel(ch)
        }
        val pi = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.root_nav_graph)
            .setDestination(R.id.numbers)
            .setArguments(null)
            .createPendingIntent()
        val n = NotificationCompat.Builder(context, "14")
            .setSmallIcon(android.R.drawable.ic_menu_edit)
            .setContentTitle("Отправить напоминания")
            .setContentText("Нажмите, чтобы отправить сообщения клиентам")
            .setContentIntent(pi)
            .setAutoCancel(true)
        val notManagerCompat = NotificationManagerCompat.from(context)
        notManagerCompat.notify(14, n.build())
    }
}