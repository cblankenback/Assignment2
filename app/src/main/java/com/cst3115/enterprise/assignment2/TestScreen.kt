package com.cst3115.enterprise.assignment2

import TestViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import com.cst3115.enterprise.assignment2.ui.theme.Assignment2Theme

@Composable
fun TestScreen(testViewModel: TestViewModel = viewModel()) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            testViewModel.testApiCall()
            Toast.makeText(context, "API Call Initiated. Check Logcat for results.", Toast.LENGTH_SHORT).show()
        }) {
            Text("Test API Call")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    Assignment2Theme {
        TestScreen()
    }
}
