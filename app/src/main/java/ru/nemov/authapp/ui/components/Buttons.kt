package ru.nemov.authapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.nemov.authapp.ui.theme.Quinary
import ru.nemov.authapp.ui.theme.Transparent
import ru.nemov.authapp.ui.theme.White
import ru.nemov.authapp.ui.theme.btnContent

enum class BtnColor(val color: Color) {
    TransparentColor(Transparent), QuinaryColor(Quinary)
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    buttonColor: BtnColor = BtnColor.QuinaryColor,
    textStyle: TextStyle = btnContent,
    cornerRadius: Int = 4,
    enabled: Boolean = true
) {
    val color = when (buttonColor) {
        BtnColor.QuinaryColor -> White
        BtnColor.TransparentColor -> Quinary
    }
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor.color,
            contentColor = color
        ),
        shape = RoundedCornerShape(cornerRadius.dp),
        enabled = enabled
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = text,
            style = textStyle,
            textAlign = TextAlign.Center
        )
    }
}