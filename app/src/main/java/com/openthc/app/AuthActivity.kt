package com.openthc.app

import android.app.Activity
import android.content.SharedPreferences
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.openthc.app.ui.login.LoggedInUserView
import com.openthc.app.ui.login.LoginViewModel
import com.openthc.app.ui.login.LoginViewModelFactory
import android.content.Intent



class AuthActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth)

        val appConf = applicationContext.getSharedPreferences("auth", 0)
        var appConfEdit = appConf.edit()

        val hostname= findViewById<EditText>(R.id.hostname)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.btnAuth)
        val loading = findViewById<ProgressBar>(R.id.loading)

        hostname.setText( appConf.getString("hostname", "https://app.openthc.dev"), android.widget.TextView.BufferType.NORMAL)
        username.setText( appConf.getString("username", "demo@app.openthc.dev"), android.widget.TextView.BufferType.NORMAL)
        //hostname.text =
        //username.setText( appConf.getString("username") )
        //password.setText( appConf.getString("password") )

        // Observer of the Form Fields
        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@AuthActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@AuthActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }

            val intentResult = Intent()
            intentResult.putExtra("hostname", hostname.text.toString());
            intentResult.putExtra("username", username.text.toString());

            setResult(Activity.RESULT_OK, intentResult)

            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            hostname.text.toString(),
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

        }

        // Login Button
        btnLogin.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(hostname.text.toString(), username.text.toString(), password.text.toString())

            appConfEdit.putString("hostname", hostname.text.toString())
            appConfEdit.putString("username", username.text.toString())
            appConfEdit.putString("password", password.text.toString())
            appConfEdit.commit()
        }


        val btnScan= findViewById<Button>(R.id.btnScan)
        btnScan.setOnClickListener {
            val intent = android.content.Intent(this, ScanActivity::class.java).apply {
                // putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
