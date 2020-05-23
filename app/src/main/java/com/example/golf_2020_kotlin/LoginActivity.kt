

package com.example.golf_2020_kotlin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Base64.NO_WRAP
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.Utility.getPackageInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity: AppCompatActivity() {

    private var callback: SessionCallback = SessionCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        fun getHashKey(context: Context): String? {
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    val packageInfo =
                        getPackageInfo(context, PackageManager.GET_SIGNING_CERTIFICATES)
                    val signatures = packageInfo.signingInfo.apkContentsSigners
                    val md = MessageDigest.getInstance("SHA")
                    for (signature in signatures) {
                        md.update(signature.toByteArray())
                        return String(Base64.encode(md.digest(), NO_WRAP))
                    }
                } else {
                    val packageInfo =
                        getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

                    for (signature in packageInfo!!.signatures) {
                        try {
                            val md = MessageDigest.getInstance("SHA")
                            md.update(signature.toByteArray())
                            return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                        } catch (e: NoSuchAlgorithmException) {
                            // ERROR LOG
                        }
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return null
        }

        // 카카오 제공 버튼일 경우
        Session.getCurrentSession().addCallback(callback);

    }

    override fun onDestroy() {
        super.onDestroy()

        Session.getCurrentSession().removeCallback(callback);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Dlog.d("session get current session")
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private class SessionCallback : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            Dlog.e("Session Call back :: onSessionOpenFailed: ${exception?.message}")
        }

        override fun onSessionOpened() {
            UserManagement.getInstance().me(object : MeV2ResponseCallback() {

                override fun onFailure(errorResult: ErrorResult?) {
                    Dlog.d("Session Call back :: on failed ${errorResult?.errorMessage}")
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Dlog.e("Session Call back :: onSessionClosed ${errorResult?.errorMessage}")

                }

                override fun onSuccess(result: MeV2Response?) {
                    checkNotNull(result) { "session response null" }
                    // register or login
                }

            })
        }

    }
}
