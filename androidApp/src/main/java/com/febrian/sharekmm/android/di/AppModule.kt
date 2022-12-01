package com.febrian.sharekmm.android.di

import android.content.Context
import com.febrian.sharekmm.android.utils.Helper
import com.febrian.sharekmm.android.utils.PreferenceManager
import com.febrian.sharekmm.auth.AuthSdk
import com.febrian.sharekmm.tweet.TweetSdk
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthSdk(): AuthSdk {
        return AuthSdk()
    }

    @Provides
    @Singleton
    fun provideTweetSdk(): TweetSdk {
        return TweetSdk()
    }

    @Provides
    @Singleton
    fun provideHelper(@ApplicationContext context: Context) = Helper(context)

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context) = PreferenceManager(context)
}