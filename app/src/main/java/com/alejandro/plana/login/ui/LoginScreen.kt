package com.alejandro.plana.login.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alejandro.plana.R
import com.alejandro.plana.core.navigation.Routes
import com.alejandro.plana.ui.theme.PlanA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.map_e1674497309430),
            contentDescription = "mapa",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logocompleton),
                    contentDescription = "logo",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.background(
                        Color(
                            red = 1f,
                            green = 1f,
                            blue = 1f,
                            alpha = 0.25f
                        )
                    )

                )
            }

            item {
                Alignment.BottomCenter; Box(
                Modifier
                    .fillMaxSize()
            ) { Login(navController, viewModel) }
            }
        }
    }
}

@Composable
fun Login(navController: NavController, loginViewModel: LoginViewModel) {
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val isLoginEnable by loginViewModel.isLoginEnabled.observeAsState(false)

    Surface(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = Color.White
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(16.dp))
            EmailLogin(email) {
                loginViewModel.onLoginChanged(it, password)
            }
            TextoRequisitos(
                texto = "Un formato de email valido",
                modifier = Modifier.align(Alignment.Start),
                color = loginViewModel.colorTextorequisitos(email, 6)
            )
            PasswordLogin(password, loginViewModel) {
                loginViewModel.onLoginChanged(email, it)
            }
            TextoRequisitos(
                texto = "6 caracteres minimo",
                modifier = Modifier.align(Alignment.Start),
                color = loginViewModel.colorTextorequisitos(password, 2)
            )
            LoginButton(isLoginEnable, navController)
            MantenerIniciada(loginViewModel)
        }
    }
}

@Composable
fun EmailLogin(email: String, onTextchanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextchanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(50.dp),
        placeholder = {
            Text(
                text = "Email",
                color = Color.Gray,
                modifier = Modifier.padding(5.dp)
            )
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFFB2B2B2),
            backgroundColor = Color(0xFFE6E6E6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
fun PasswordLogin(
    password: String,
    loginViewModel: LoginViewModel,
    onTextchanged: (String) -> Unit
) {
    val passwordVisibility by loginViewModel.passwordVisibility.observeAsState(false)
    TextField(
        value = password,
        onValueChange = { onTextchanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(50.dp),
        placeholder = {
            Text(
                text = "Password",
                color = Color.Gray,
                modifier = Modifier.padding(5.dp)
            )
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFFB2B2B2),
            backgroundColor = Color(0xFFE6E6E6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            val imagen = if (passwordVisibility) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { loginViewModel.onPasswordVisibilityChanged() }) {
                Icon(
                    imageVector = imagen,
                    contentDescription = "show password",
                    tint = Color(0xFFB2B2B2),
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, navController: NavController) {
    val context = LocalContext.current
    Button(
        onClick = {
            navController.navigate(Routes.Home.route)
            suspend {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Has iniciado sesion correctamente", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        },
        enabled = loginEnable,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFECC1B),
            disabledBackgroundColor = Color(0xFFAD9D5F),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = "Sign up",
            style = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp
            )
        )
    }
}

@Composable
fun MantenerIniciada(loginViewModel: LoginViewModel) {
    var state by rememberSaveable { mutableStateOf(loginViewModel.mantenerIniciada.value) }
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        state?.let {
            Checkbox(
                checked = it,
                onCheckedChange = { state = !state!! },
                enabled = true,
                colors = CheckboxDefaults.colors(
                    checkedColor = PlanA,
                    uncheckedColor = Color(0xFFAD9D5F)
                )
            )
        }
        Text(
            text = "Recordarme",
            style = TextStyle(color = Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
fun TextoRequisitos(texto: String, modifier: Modifier, color: Color) {
    Text(
        text = "* $texto",
        style = TextStyle(
            color = color,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        ),
        modifier = modifier.padding(top = 2.dp, bottom = 5.dp, start = 20.dp)
    )
}