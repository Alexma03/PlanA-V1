package com.alejandro.plana.inicio.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alejandro.plana.R
import com.alejandro.plana.ui.theme.BlueFacebook
import com.alejandro.plana.ui.theme.BlueGoogle
import com.alejandro.plana.ui.theme.BlueTwitter
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.core.navigation.Routes.*
import com.alejandro.plana.inicio.ui.components.OneTapSignIn
import com.alejandro.plana.inicio.ui.components.SignInWithGoogle
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.withContext


@Composable
fun InicioScreen(
    navController: NavHostController,
    viewModel: InicioViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit

) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.map_e1674497309430),
            contentDescription = "mapa",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                Surface(
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    color = Color.White,
                    modifier = Modifier
                        .wrapContentHeight()
                        .shadow(100.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Boton(
                            R.drawable.google,
                            "google",
                            "Continuar con Google",
                            Color.White,
                            BlueGoogle,
                        ) { viewModel.oneTapSignIn() }


                        Boton(
                            R.drawable.twitter,
                            "twitter",
                            "Continuar con Twitter",
                            BlueTwitter,
                            Color.White,
                        ) { navController.navigate(Profile.route) }


                        Boton(
                            R.drawable.facebook,
                            "facebook",
                            "Continuar con Facebook",
                            BlueFacebook,
                            Color.White,
                        ) { navController.navigate(Home.route) }


                        Boton(
                            R.drawable.gmail_logo,
                            "email",
                            "Iniciar sesion Email",
                            Color.White,
                            Color.Gray,
                        ) { navController.navigate(Login.route) }

                        SignUp(navController)
                    }

                }
            }
        }

    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                viewModel.signInWithGoogle(googleCredentials)
            } catch (it: ApiException) {
                print(it)
            }
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    OneTapSignIn(
        launch = {
            launch(it)
        }
    )

    SignInWithGoogle(
        navigateToHomeScreen = { signedIn ->
            if (signedIn) {
                navigateToProfileScreen()
            }
        }
    )
}


@Composable
fun SignUp(navController: NavHostController) {
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
                text = "¿No tienes cuenta?",
                fontSize = 15.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Sign up.",
                Modifier
                    .padding(horizontal = 8.dp)
                    .clickable(onClick = { navController.navigate(Registro.route) }),
                fontSize = 15.sp,
                color = BlueTwitter,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
    }
}


