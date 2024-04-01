package com.myrepo.base

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity() {

    fun periodicWorkManager(){
        val periodicWorkRequest = PeriodicWorkRequestBuilder<BackupWorker>(15, TimeUnit.MINUTES).build()
        val workManager =WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            "UpdateRecord",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,periodicWorkRequest
        )
        workManager.getWorkInfosForUniqueWorkLiveData("UpdateRecord").observe(this,
            object : Observer<List<WorkInfo>> {
                override fun onChanged(value: List<WorkInfo>) {
                    if(value!=null && (!(value.isEmpty()))) {
                        val info = value.get(0)
                        Log.e("WORK","MAN>> ${info.state}")
                    }
                }
            })
    }
}