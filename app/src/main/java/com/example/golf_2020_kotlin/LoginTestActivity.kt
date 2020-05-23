package com.example.golf_2020_kotlin
/*   2020. 05.23 김정선 작성. FireBase realtime DB에 Write 구현.
      차후 채팅 구현을 위해 사용할 RealTime NOSql 베이스 DB사용법을 익히는 과정이다. */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import org.w3c.dom.Text
import kotlinx.android.synthetic.main.activity_login2.* // findViewById 를 안쓰기위해 사용함.

class LoginTestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        login.setOnClickListener {   // login  : 버튼 아이디 명.  아래에도 EditView 아이디 그냥 가져다씀.
            //editText에서 입력되어 있는 텍스트를 가져 온다.
            var id = username.text.toString()    //json 방식이라 String으로 변환하지않으면 에
            var passwd = password.text.toString()  //윗 아이디와 같은방식으로 비밀번호도!
            val database = FirebaseDatabase.getInstance()   // FIREBASE의 db를 참조한다.
            val myRef = database.getReference("id")       //getReference() API 사용하여 참조하는
            val myRef2 = database.getReference("passwd")   // 변수를 지정 - 포인터같은느낌
            myRef.setValue(id)    // 값저장.
            myRef2.setValue(passwd)


            setContentView(R.layout.welcome)   //임시로 띄워주는 뷰. 그렇지않으면 앱이 종료되서 DB저장 실패됨.



        }

    }
}