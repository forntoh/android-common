package com.forntoh.common.data.pref

import android.content.SharedPreferences
import io.reactivex.subjects.PublishSubject

@Suppress("unused")
class LiveSharedPreferences constructor(private val preferences: SharedPreferences) {

    private val publisher = PublishSubject.create<String>()
    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key -> publisher.onNext(key) }

    private val updates = publisher.doOnSubscribe {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }.doOnDispose {
        if (!publisher.hasObservers())
            preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getPreferences(): SharedPreferences = preferences

    fun getString(key: String, defaultValue: String): LivePreference<String> =
        LivePreference(updates, preferences, key, defaultValue)

    fun getInt(key: String, defaultValue: Int): LivePreference<Int> =
        LivePreference(updates, preferences, key, defaultValue)

    fun getBoolean(key: String, defaultValue: Boolean): LivePreference<Boolean> =
        LivePreference(updates, preferences, key, defaultValue)

    fun getFloat(key: String, defaultValue: Float): LivePreference<Float> =
        LivePreference(updates, preferences, key, defaultValue)

    fun getLong(key: String, defaultValue: Long): LivePreference<Long> =
        LivePreference(updates, preferences, key, defaultValue)

    fun getStringSet(key: String, defaultValue: Set<String>): LivePreference<Set<String>> =
        LivePreference(updates, preferences, key, defaultValue)
}