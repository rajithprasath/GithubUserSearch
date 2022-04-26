package com.rajith.payconiq.base

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.rajith.payconiq.GithubUserSearchApplication

class HiltTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, GithubUserSearchApplication::class.java.name, context)
    }
}