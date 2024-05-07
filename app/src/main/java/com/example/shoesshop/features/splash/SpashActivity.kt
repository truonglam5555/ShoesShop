package com.example.shoesshop.features.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.window.layout.WindowMetricsCalculator
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.constants.DataShared
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.ActivitySplashBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.welcome.WelcomeActivity

class SpashActivity() : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this@SpashActivity)
        val currentBounds = windowMetrics.bounds
        val width = currentBounds.width()
        val height = currentBounds.height()
        DataShared.setWidth(width)
        DataShared.setHeight(height)

        val myVersion = Build.VERSION.RELEASE
        val deviceName = Build.MODEL
        DataShared.setDeviceModel(deviceName)
        DataShared.setDeviceVersion(myVersion)

        android.os.Handler().postDelayed({
            val data =   MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
            if (data != null && data!!.isNotEmpty())
            {
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
            finish()
        }, 3000)
    }
}