package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Surface(onClick = { /*TODO*/ }) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var textValue by remember { mutableStateOf("") }
        var inputUnit by remember { mutableStateOf("Centimeters") }
        var outputUnit by remember { mutableStateOf("Meters") }
        var factor by remember { mutableStateOf(1.00) }
        var ofactor by remember { mutableStateOf(1.00) }
        var output by remember { mutableStateOf("") }


        fun convert() {
            var textValueDouble = textValue.toDoubleOrNull() ?: 0.0
            var result = (textValueDouble * factor * 100.0 / ofactor).roundToInt() / 100.0
            output = result.toString()
        }

        val titleStyle = TextStyle(
            fontSize = 32.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Red
        )

        Text("Unit Converter", style = titleStyle)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                value ->
                textValue = value
                convert()
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            label = { Text("Enter Value") }

        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box {
                var iOpen by remember { mutableStateOf(false) }

                Button(onClick = { iOpen = true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, "dropdown icon")
                }
                DropdownMenu(expanded = iOpen, onDismissRequest = { iOpen = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            inputUnit = "Centimeters"
                            iOpen = false
                            factor = 0.01
                            convert()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            inputUnit = "Meters"
                            iOpen = false
                            factor = 1.0
                            convert()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            inputUnit = "Feet"
                            iOpen = false
                            factor = 0.3048
                            convert()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Milimeters") },
                        onClick = {
                            inputUnit = "Milimeters"
                            iOpen = false
                            factor = 0.001
                            convert()
                        }
                    )
                }
            }
            Box {
                var oOpen by remember { mutableStateOf(false) }

                Button(onClick = {
                    oOpen = true
                }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, "dropdown icon")
                }
                DropdownMenu(expanded = oOpen, onDismissRequest = { oOpen = false } ) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            outputUnit = "Centimeters"
                            oOpen = false
                            ofactor = 0.01
                            convert()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            outputUnit = "Meters"
                            oOpen = false
                            ofactor = 1.0
                            convert()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            outputUnit = "Feet"
                            oOpen = false
                            ofactor = 0.3048
                            convert()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Milimeters") },
                        onClick = {
                            outputUnit = "Milimeters"
                            oOpen = false
                            ofactor = 0.001
                            convert()
                        }
                    )
                }
            }
        }
        Text("Result: $output $outputUnit",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}