package com.imagesearch.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable.add(Completable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val intent = Intent(this,
                            SearchActivity::class.java)
                    startActivity(intent)
                    finish()
                })
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