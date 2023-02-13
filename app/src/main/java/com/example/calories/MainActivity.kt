package com.example.calories

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.calories.ui.theme.CaloriesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalorieScreen()
                }
            }
        }
    }
}

@Composable
fun CalorieScreen() {
    var weightInput by rememberSaveable { mutableStateOf("") }
    var weight = weightInput.toIntOrNull() ?: 0
    var male by rememberSaveable { mutableStateOf(true) }
    var intensity by rememberSaveable { mutableStateOf(1.3f) }
    var result by rememberSaveable { mutableStateOf(0) }
    Column(

        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Heading(title = stringResource(R.string.title))
        WeightField(weightInput = weightInput, onChange = { weightInput = it })
        SexChoice(male = male, setSexMale = { male = it })
        IntensityList(onClick = { intensity = it })
        ResultButton(
            male = male,
            weight = weight,
            intensity = intensity,
            setResult = { result = it })
        Text(
            text = result.toString(),
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Heading(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun WeightField(weightInput: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = weightInput,
        onValueChange = onChange,
        label = { Text(text = stringResource(R.string.weightLabel)) },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun SexChoice(male: Boolean, setSexMale: (Boolean) -> Unit) {
    Column(Modifier.selectableGroup()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = male, onClick = { setSexMale(true) })
            Text(text = "Male")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = !male, onClick = { setSexMale(false) })
            Text(text = "Female")
        }
    }
}

@Composable
fun IntensityList(onClick: (Float) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf("Light") }
    val items = listOf("Light", "Usual", "Moderate", "Hard", "Very Hard")
    var textSize by remember { mutableStateOf(Size.Zero) }


    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textSize = coordinates.size.toSize()
                },
            label = { Text(text = "Select intensity") },
            trailingIcon = {
                Icon(icon,
                    contentDescription = stringResource(R.string.keyboardIconDescription),
                    Modifier.clickable { expanded = !expanded })
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textSize.width.toDp() })
        ) {
            items.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label

                    val intensity: Float = when (label) {
                        "Light" -> 1.3f
                        "Usual" -> 1.5f
                        "Moderate" -> 1.7f
                        "Hard" -> 2f
                        "Very Hard" -> 2.2f
                        else -> 0.0f
                    }
                    onClick(intensity)
                    expanded = false
                }) {
                    Text(text = label)
                }

            }
        }
    }
}

@Composable
fun ResultButton(male: Boolean, weight: Int, intensity: Float, setResult: (Int) -> Unit) {
    Button(
        onClick = {
            if (male) {
                setResult(((879 + 10.2 * weight) * intensity).toInt())
            } else {
                setResult(((795 + 7.18 * weight) * intensity).toInt())
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Calculate")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CaloriesTheme {
        CalorieScreen()
    }
}