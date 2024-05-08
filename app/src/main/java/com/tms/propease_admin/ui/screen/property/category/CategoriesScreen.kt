package com.tms.propease_admin.ui.screen.property.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tms.propease_admin.model.Category
import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme

val categories = listOf<CategoryItem>(
    CategoryItem(
        id = 1,
        name = "Rental"
    ),
    CategoryItem(
        id = 2,
        name = "Airbnb"
    ),
    CategoryItem(
        id = 3,
        name = "Shop"
    ),
    CategoryItem(
        id = 4,
        name = "On Sale"
    ),
)
@Composable
fun CategoriesScreenComposable() {
    Box {
        CategoriesScreen(
            categories = categories
        )
    }
}

@Composable
fun CategoriesScreen(
    categories: List<CategoryItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "+ Category")
            }
        }
        LazyColumn {
            items(categories) {
                CategoryItemCell(
                    category = it,
                    navigateToCategoryPropertiesScreen = {},
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryItemCell(
    category: CategoryItem,
    navigateToCategoryPropertiesScreen: (categoryId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigateToCategoryPropertiesScreen(category.id.toString())
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = category.name.uppercase(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateToCategoryPropertiesScreen(category.id.toString()) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "${category.name} properties"
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    PropEaseAdminTheme {
        CategoriesScreen(
            categories = categories
        )
    }
}