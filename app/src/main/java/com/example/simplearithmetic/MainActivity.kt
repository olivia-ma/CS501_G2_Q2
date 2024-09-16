package com.example.simplearithmetic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplearithmetic.ui.theme.SimpleArithmeticTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleArithmeticTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Arithmetic()
                }
            }
        }
    }
}

@Composable
fun Arithmetic() {

    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }


    var result by remember { mutableStateOf(0.0) }
    var errorMessage by remember { mutableStateOf("") }
    // used to prevent error message/result showing up before anything has been calculated
    var hasCalculated by remember {mutableStateOf(false)}

    val radioOptions = listOf("Addition", "Subtraction", "Multiplication", "Division", "Modulus")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }


    fun calculate() {
        // fixes case where there is a error message -> user fixes it (wasnt showing the new result because previous error message was still there)
        errorMessage = ""
        // helps to check if inputs are numbers
        val a = operand1.toDoubleOrNull()
        val b = operand2.toDoubleOrNull()

        if (a == null || b == null) {
            errorMessage = "Please enter valid numbers for both operands!"
        }
        else{
            when (selectedOption) {
                "Addition" -> {
                    result = a+b
                    hasCalculated = true
                }
                "Subtraction" -> {
                    result = a-b
                    hasCalculated = true
                }
                "Multiplication" -> {
                    result = a*b
                    hasCalculated = true
                }

                "Division" -> {
                    if (b == 0.0) {
                        errorMessage = "You cannot divide by 0!"
                    } else {
                        result = a/b
                        hasCalculated = true
                    }
                }
                "Modulus" -> {
                    if (b == 0.0) {
                        errorMessage = "Modulus by 0 is not allowed!"
                    } else {
                        result = a%b
                        hasCalculated = true
                    }
                }
                }
        }
    }

    // two text fields
    // radio buttons to select operands
    // calculate button
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = operand1,
            onValueChange = { operand1 = it },
            label = { Text("Operand 1") },
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = operand2,
            onValueChange = { operand2 = it },
            label = { Text("Operand 2") },
        )
        Spacer(modifier = Modifier.height(10.dp))

        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),


                        onClick = { onOptionSelected(text) }
                    )
                    .padding(horizontal = 16.dp),

                horizontalArrangement = Arrangement.Start,

                verticalAlignment = Alignment.CenterVertically
            ) {
                val context = LocalContext.current
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text) },
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 3.dp),
                )
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        ElevatedButton(onClick = {calculate() }) {
            Text(text = "Calculate", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 30.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        } else if (hasCalculated){
            Text(
                text = "Result: $result",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleArithmeticTheme {
        Arithmetic()
    }
}