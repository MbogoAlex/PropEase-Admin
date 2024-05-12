package com.tms.propease_admin.ui.screen.property.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tms.propease_admin.R
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.InputForm

@Composable
fun CategoryAdditionScreenComposable() {

}

@Composable
fun CategoryAdditionScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        InputFields(
            onValueChange = {}
        )
    }
}

@Composable
fun InputFields(
    onValueChange: (newValue: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        InputForm(
            value = "",
            label = "Category name",
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            leadingIcon = R.drawable.category,
            isError = false,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryAdditionScreenPreview() {
    PropEaseAdminTheme {
        CategoryAdditionScreen()
    }
}