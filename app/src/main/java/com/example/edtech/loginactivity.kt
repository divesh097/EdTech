package com.example.edtech

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class loginactivity : AppCompatActivity() {
    private lateinit var log: Button
    private lateinit var user: EditText
    private lateinit var pass: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var googlesign: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authrepo=autorepo()
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_loginactivity)
        user = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        log = findViewById(R.id.loginapp)

        var islogin=authrepo.isLoggedIn()
        if(islogin){
            Intent(this, MainActivity::class.java).also {
                it.putExtra("UID",auth.currentUser?.uid)
                startActivity(it)
                finish()
            }
        }
        else {
            findViewById<Button>(R.id.forgetyourpassword).setOnClickListener {
                if(user.text.toString()!="") {
                    auth.sendPasswordResetEmail(user.text.toString())
                    Toast.makeText(this,"forgot link send to email",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"enter the username",Toast.LENGTH_SHORT).show()
                }
            }
            log.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    // Call the login function, passing user credentials
                    val isLoginSuccessful =
                        authrepo.login(user.text.toString(), pass.text.toString())

                    if (isLoginSuccessful) {
                        Toast.makeText(this@loginactivity, "Login successful", Toast.LENGTH_SHORT)
                            .show()
                        islogin = true
                        Intent(this@loginactivity, MainActivity::class.java).also {
                            it.putExtra("UID",auth.currentUser?.uid)
                            startActivity(it)
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this@loginactivity,
                            "Invalid username or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            findViewById<Button>(R.id.signup).setOnClickListener{
                if(user.text.toString()=="" || pass.text.toString()==""){
                    Toast.makeText(this,"enter the username and password",Toast.LENGTH_SHORT).show()
                }
                CoroutineScope(Dispatchers.Main).launch {
                    // Call the login function, passing user credentials
                    val isLoginSuccessful =
                        authrepo.register(user.text.toString(), pass.text.toString())

                    if (isLoginSuccessful) {
                        Toast.makeText(this@loginactivity, "Login successful", Toast.LENGTH_SHORT)
                            .show()
                        islogin = true
                        Intent(this@loginactivity, MainActivity::class.java).also {
                            it.putExtra("UID",auth.currentUser?.uid)
                            startActivity(it)
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this@loginactivity,
                            "Invalid username or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        //google
        findViewById<ImageButton>(R.id.googlelogin).setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googlesign = GoogleSignIn.getClient(this, gso)
            signgoogle()
        }

    }

    private fun signgoogle() {
        val signinintent = googlesign.signInIntent
        launcher.launch(signinintent)
    }

    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleresult(task)
            }
        }

    private fun handleresult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateui(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateui(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                Toast.makeText(this, "Login succesfull", Toast.LENGTH_SHORT).show()
                Intent(this, MainActivity::class.java).also {
                    it.putExtra("UID",auth.currentUser?.uid)
                    startActivity(it)
                    finish()
                }
            }
            else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }


        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Intent(this, MainActivity::class.java).also {
                it.putExtra("UID",auth.currentUser?.uid)
                startActivity(it)
                finish()
            }
        }
    }
}