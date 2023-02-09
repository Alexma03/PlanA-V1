package com.alejandro.plana.resgistro.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alejandro.plana.ui.theme.BlueTwitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(registroViewModel: RegistroViewModel, navController: NavHostController) {
    LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        item { Alignment.Start }

        item {
            Alignment.BottomCenter; Box(
            Modifier
                .fillMaxSize()
        ) { RegistroUsuario(navController, registroViewModel) }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegistroUsuario(navController: NavHostController, registroViewModel: RegistroViewModel) {
    val email: String by registroViewModel.email.observeAsState("")
    val name: String by registroViewModel.name.observeAsState("")
    val password: String by registroViewModel.password.observeAsState("")
    val repeatPaassword: String by registroViewModel.repeatPassword.observeAsState("")
    val isSingUpEnable by registroViewModel.isSignUpEnabled.observeAsState(false)


    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    Surface(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = Color.White
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(16.dp))
            Name(name) {
                registroViewModel.onLoginChanged(
                    it,
                    email,
                    password,
                    repeatPaassword,
                    isSingUpEnable
                )
            }
            TextoRequisitos(
                texto = "1 letra en mayuscula minimo",
                Modifier.align(Alignment.Start),
                registroViewModel.colorTextorequisitos(name, 5)
            )
            EmailRegister(email) {
                registroViewModel.onLoginChanged(name, it, password, repeatPaassword, isSingUpEnable)
            }
            TextoRequisitos(
                texto = "Un formato de email valido",
                Modifier.align(Alignment.Start),
                registroViewModel.colorTextorequisitos(email, 6)
            )
            PasswordRegister(
                password,
                registroViewModel,
                bringIntoViewRequester,
                coroutineScope,
                focusManager
            ) {
                registroViewModel.onLoginChanged(name, email, it, repeatPaassword, isSingUpEnable)
            }
            TextoRequisitos(
                texto = "6 caracteres minimo",
                Modifier.align(Alignment.Start),
                registroViewModel.colorTextorequisitos(password, 2)
            )
            RepeatPassword(
                repeatPaassword,
                registroViewModel,
                bringIntoViewRequester,
                coroutineScope,
                focusManager
            ) {
                registroViewModel.onLoginChanged(name, email, password, it, isSingUpEnable)
            }
            TextoRequisitos(
                texto = "Misma contraseña",
                Modifier.align(Alignment.Start),
                registroViewModel.colorTextorequisitos(repeatPaassword, 3, password)
            )
            SignUpButton(
                isSingUpEnable,
                password,
                name,
                email,
                navController,
                bringIntoViewRequester
            )
            Login(navController = navController)
        }
    }
}

@Composable
fun Name(name: String, onTextchanged: (String) -> Unit) {
    TextField(
        value = name, onValueChange = { onTextchanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(50.dp),
        placeholder = {
            Text(
                text = "Name",
                color = Color.Gray,
                modifier = Modifier.padding(5.dp)
            )
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFFB2B2B2),
            backgroundColor = Color(0xFFE6E6E6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

}

@Composable
fun EmailRegister(email: String, onTextchanged: (String) -> Unit) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordRegister(
    password: String,
    registroViewModel: RegistroViewModel,
    bringIntoViewRequester: BringIntoViewRequester,
    coroutineScope: CoroutineScope,
    focusManager: FocusManager,
    onTextchanged: (String) -> Unit
) {
    val passwordVisibility = registroViewModel.passwordVisibility.observeAsState(false)
    TextField(
        value = password,
        onValueChange = { onTextchanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
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
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        trailingIcon = {
            val imagen = if (passwordVisibility.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { registroViewModel.onPasswordVisibilityChanged() }) {
                Icon(
                    imageVector = imagen,
                    contentDescription = "show password",
                    tint = Color(0xFFB2B2B2),
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        },
        visualTransformation = if (passwordVisibility.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepeatPassword(
    password: String,
    registroViewModel: RegistroViewModel,
    bringIntoViewRequester: BringIntoViewRequester,
    coroutineScope: CoroutineScope, focusManager: FocusManager,
    onTextchanged: (String) -> Unit
) {
    val passwordVisibility = registroViewModel.passwordVisibility.observeAsState(false)
    TextField(
        value = password,
        onValueChange = { onTextchanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        shape = RoundedCornerShape(50.dp),
        placeholder = {
            Text(
                text = "Repeat Password",
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
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        trailingIcon = {
            val imagen = if (passwordVisibility.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { registroViewModel.onPasswordVisibilityChanged() }) {
                Icon(
                    imageVector = imagen,
                    contentDescription = "show password",
                    tint = Color(0xFFB2B2B2),
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        },
        visualTransformation = if (passwordVisibility.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpButton(
    SignUpEnable: Boolean,
    password: String,
    name: String,
    repeatPassword: String,
    navController: NavHostController,
    bringIntoViewRequester: BringIntoViewRequester
) {
    val context = LocalContext.current
    /*val scope = rememberCoroutineScope()
    val dataStore = RegisterUser(context)*/
    Button(
        onClick = {
/*
                navController.navigate(EmailLogin.route)
*/
            /*scope.launch {
                dataStore.saveName(name)
                dataStore.savePassword(password)
                dataStore.saveEmail(email)
                dataStore.savePostalCode(codigoPostal)
            }*/
            Toast.makeText(
                context, "$name has sido registrado correctamente", Toast.LENGTH_SHORT
            ).show()

        },
        enabled = SignUpEnable,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester),
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
fun Login(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            Modifier
                .padding(24.dp)
                .background(Color.Gray)
                .height(2.dp)
                .fillMaxWidth()

        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "¿Tienes cuenta?",
                fontSize = 15.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Log in.",
                Modifier
                    .padding(horizontal = 8.dp)
                    .clickable(onClick = { navController.popBackStack() }),
                fontSize = 15.sp,
                color = BlueTwitter,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
    }


}


//Otro

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