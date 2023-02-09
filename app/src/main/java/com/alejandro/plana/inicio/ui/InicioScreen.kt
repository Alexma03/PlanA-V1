package com.alejandro.plana.inicio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alejandro.plana.R
import com.alejandro.plana.inicio.model.DataBoton
import com.alejandro.plana.navigation.Routes
import com.alejandro.plana.ui.theme.BlueFacebook
import com.alejandro.plana.ui.theme.BlueGoogle
import com.alejandro.plana.ui.theme.BlueTwitter


@Composable
fun InicioScreen(
    navController: NavHostController,
) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        //logoImagen

        LazyColumn {
            item {
                Boton(
                    DataBoton(
                        R.drawable.google,
                        "google",
                        "Continuar con Google",
                        BlueGoogle,
                        Color.White,
                        null
                    )
                )
            }
            item {
                Boton(
                    DataBoton(
                        R.drawable.twitter,
                        "twitter",
                        "Continuar con Twitter",
                        Color.White,
                        BlueTwitter,
                        null
                    )
                )
            }
            item {
                Boton(
                    DataBoton(
                        R.drawable.facebook,
                        "facebook",
                        "Continuar con Facebook",
                        Color.White,
                        BlueFacebook,
                        null
                    )
                )
            }
            item {
                Boton(
                    DataBoton(
                        R.drawable.gmail_logo,
                        "email",
                        "Iniciar sesion Email",
                        Color.Gray,
                        Color.White,
                        null
                    )
                )
            }
            item {
                SignUp(navController)
            }
        }
    }
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
                    .clickable(onClick = { navController.navigate(Routes.Registro.route) }),
                fontSize = 15.sp,
                color = BlueTwitter,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
    }


}
