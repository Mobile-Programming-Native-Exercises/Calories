package com.example.calories

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var male by rememberSaveable { mutableStateOf(true) }
    Column(

        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Heading(title = stringResource(R.string.title))
        WeightField(weightInput = weightInput, onChange = { Log.d("a","test") })
        SexChoice(male = male, setSexMale = { male = it })
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
    Log.d("a","test 2")
    OutlinedTextField(
        value = weightInput,
        onValueChange = { onChange },
        label = { Text(text = stringResource(R.string.weightLabel)) }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CaloriesTheme {
        CalorieScreen()
    }
}