package com.alejandro.plana.prueba

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alejandro.plana.ui.theme.PlanA

@Composable
fun MantenerIniciada(mantenerIniciada: Boolean) {
    var state by remember { mutableStateOf(mantenerIniciada) }
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            checked = state,
            onCheckedChange = { state = !state },
            enabled = true,
            colors = CheckboxDefaults.colors(
                checkedColor = PlanA,
                uncheckedColor = Color(0xFFAD9D5F)
            )
        )

        Text(
            text = "Recordarme",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}