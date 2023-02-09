package com.alejandro.plana.inicio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.plana.inicio.model.DataBoton

@Composable
fun Boton(dataBoton: DataBoton) {
    Card(
        elevation = 15.dp,
        shape = RoundedCornerShape(50.dp),
        backgroundColor = dataBoton.backgroundColor,
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth()
            .clickable(onClick = {
                dataBoton.onClicked
            })
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight(Alignment.CenterVertically)
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(dataBoton.idPhoto),
                contentDescription = dataBoton.nombre,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 5.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = dataBoton.texto,
                    style = TextStyle(
                        color = dataBoton.textColor,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                )
            }
        }
    }
}
