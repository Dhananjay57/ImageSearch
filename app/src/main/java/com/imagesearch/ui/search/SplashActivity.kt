package com.imagesearch.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private var compositeDisposable = CompositeDisposable()
    var delay: Long =2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null) {
            // for (key in intent.extras!!.keySet()) {
            val value = intent.extras!!.getString("search_term")
            Log.d("@@@", "Key:  Value: $value")
            // }

            if (value != null)
                delay = 0

            compositeDisposable.add(Completable.timer(delay, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val intent = Intent(this,
                                SearchActivity::class.java)
                        intent.putExtra("search_term", value)
                        startActivity(intent)
                        finish()
                    })
        }
        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        if (!compositeDisposable.isDisposed)
            compositeDisposable.clear()
    }

    public override fun onDestroy() {
        super.onDestroy()
    }
}