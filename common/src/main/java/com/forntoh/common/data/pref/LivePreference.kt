package com.forntoh.common.data.pref

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LivePreference<T> constructor(
    private val updates: Observable<String>,
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T
) : MutableLiveData<T>() {

    private var disposable: Disposable? = null

    @Suppress("UNCHECKED_CAST")
    override fun onActive() {
        super.onActive()
        value = (preferences.all[key] as T) ?: defaultValue

        disposable = updates.filter { t -> t == key }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<String>() {
                override fun onComplete() = Unit

                override fun onNext(t: String) =
                    postValue((preferences.all[t] as T) ?: defaultValue)

                override fun onError(e: Throwable) = Unit
            })
    }

    override fun onInactive() {
        super.onInactive()
        disposable?.dispose()
    }
}