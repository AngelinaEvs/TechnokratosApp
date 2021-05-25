//package ru.itis.regme.presenter.notifications
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import android.util.Log
//import androidx.annotation.NonNull
//import androidx.annotation.RequiresApi
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.core.content.ContextCompat.getSystemService
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import kotlinx.android.synthetic.main.phone_numbers_fragment.*
//import java.util.*
//
//class NotificationWorkerManager(context: Context, workerParams: WorkerParameters) : Worker(
//    context,
//    workerParams
//) {
//
//        @NonNull
//    override fun doWork(): Result {
//        Log.e("LALAL", "WORK MANAGER HI " + Calendar.getInstance().time)
//        createNotification()
//        return Result.success()
//    }
//
//    private fun createNotification() {
//        var nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            var ch = NotificationChannel("14", "my", NotificationManager.IMPORTANCE_DEFAULT)
//            nm.createNotificationChannel(ch)
//        }
//        var n = NotificationCompat.Builder(applicationContext, "14")
//            .setSmallIcon(android.R.drawable.ic_menu_edit)
//            .setContentTitle("Reg Me")
//            .setContentText("Нажмите, чтобы отправить сообщения клиентам")
//            .setAutoCancel(true)
//        var notManagerCompat = NotificationManagerCompat.from(applicationContext)
//        notManagerCompat.notify(14, n.build())
//    }
//
//}