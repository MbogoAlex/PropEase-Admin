package com.tms.propease_admin.utils

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tms.propease_admin.R
import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.model.PropertyDetails
import kotlinx.coroutines.delay

@Composable
fun InputForm(
    value: String,
    label: String,
    onValueChange: (newValue: String) -> Unit,
    keyboardOptions: KeyboardOptions,
    leadingIcon: Int,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        shape = RoundedCornerShape(10.dp),
        value = value,
        label = {
            Text(text = label)
        },
        leadingIcon = {
            Icon(painter = painterResource(id = leadingIcon), contentDescription = label)
        },
        keyboardOptions = keyboardOptions,
        isError = isError,
        onValueChange = onValueChange,
        modifier = modifier
    )
}
@Composable
fun PropertyItem(
    approved: Boolean,
    property: PropertyDetails,
    navigateToSpecificProperty: (propertyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                navigateToSpecificProperty(property.propertyId.toString())
            }
    ) {
        Column {
            if(property.images.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(property.images.first().name)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                    contentDescription = property.title,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .height(140.dp)
                        .fillMaxWidth()
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .alpha(0.5f)
                        .height(140.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(5.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(5.dp)
                        )
                ) {
                    Text(text = "No image")
                }

            }

            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = property.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = property.location.county
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${property.location.county}, ${property.location.address}",
                        fontSize = 13.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Light,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(
                        text = property.category.takeIf { it.length <= 6 } ?: "${property.category.substring(0, 4)}...",
                        fontSize = 11.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                top = 5.dp,
                                end = 10.dp,
                                bottom = 5.dp
                            )
                    )
                }

            }
        }
    }
}

@Composable
fun CategorySelection(
    categories: List<CategoryItem>,
    categoryNameSelected: String,
    onChangeCategory: (location: String?, rooms: Int?, categoryId: Int?, categoryName: String?) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    var dropDownIcon: ImageVector
    if(expanded) {
        dropDownIcon = Icons.Default.KeyboardArrowUp
    } else {
        dropDownIcon = Icons.Default.KeyboardArrowDown
    }
    Column {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier

            ) {
                Text(
                    text = "Category".takeIf { categoryNameSelected.isEmpty() } ?: categoryNameSelected,
                    modifier = Modifier
                        .padding(10.dp)
//                        .widthIn(120.dp)
                )
                Icon(
                    imageVector = dropDownIcon,
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEachIndexed { index, i ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = i.name
                        )
                    },
                    onClick = {
                        onChangeCategory(
                            null, null, i.id, i.name
                        )
                        expanded = !expanded
                    }
                )
            }
        }
    }
}

@Composable
fun NumberOfRoomsSelection(
    numberOfRoomsSelected: String,
    onChangeNumberOfRooms: (location: String?, rooms: Int?, categoryId: Int?, categoryName: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val rooms = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8)
//    var selectedRoom by remember {
//        mutableIntStateOf(0)
//    }
    var expanded by remember {
        mutableStateOf(false)
    }

    var dropDownIcon: ImageVector
    if(expanded) {
        dropDownIcon = Icons.Default.KeyboardArrowUp
    } else {
        dropDownIcon = Icons.Default.KeyboardArrowDown
    }

    Column {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Text(
                    text = "No. Rooms".takeIf { numberOfRoomsSelected.isEmpty() }
                        ?: "$numberOfRoomsSelected room".takeIf { numberOfRoomsSelected.toInt() == 1 }
                        ?: "$numberOfRoomsSelected rooms",
                    modifier = Modifier
                        .padding(10.dp)
//                        .widthIn(120.dp)
                )
                Icon(
                    imageVector = dropDownIcon,
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            rooms.forEachIndexed { index, i ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "1 room".takeIf { i == 1 } ?: "$i rooms"
                        )
                    },
                    onClick = {
                        onChangeNumberOfRooms(
                            null, i, null,null
                        )

                        expanded = !expanded
                    }
                )
            }
        }
    }
}

@Composable
fun LocationSearchForm(
    leadingIcon: Painter,
    labelText: String,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChanged: (newValue: String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        shape = RoundedCornerShape(10.dp),
        leadingIcon = {
            Icon(
                painter = leadingIcon,
                contentDescription = null
            )
        },

        label = {
            Text(text = labelText)
        },
        value = value,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = onValueChanged,
        modifier = modifier
    )

}

@Composable
fun PropertiesFilterSection(
    filteringOn: Boolean,
    selectedLocation: String,
    categories: List<CategoryItem>,
    categoryNameSelected: String,
    numberOfRoomsSelected: String,
    onSearchLocationChanged: (newValue: String) -> Unit,
    onChangeNumberOfRooms: (location: String?, rooms: Int?, categoryId: Int?, categoryName: String?) -> Unit,
    onChangeCategory: (location: String?, rooms: Int?, categoryId: Int?, categoryName: String?) -> Unit,
    unfilter: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column {
        LocationSearchForm(
            leadingIcon = painterResource(id = R.drawable.location),
            labelText = "Location",
            value = selectedLocation,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
            ),
            onValueChanged = onSearchLocationChanged,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            NumberOfRoomsSelection(
                numberOfRoomsSelected = numberOfRoomsSelected,
                onChangeNumberOfRooms = onChangeNumberOfRooms
            )
            Spacer(modifier = Modifier.width(10.dp))
            CategorySelection(
                categories = categories,
                categoryNameSelected = categoryNameSelected,
                onChangeCategory = onChangeCategory
            )
            Spacer(modifier = Modifier.weight(1f))
            if(filteringOn) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .clickable {
                            unfilter()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
//        if(!isConnected) {
//            Text(
//                text = "Check your internet connection",
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//            )
//        }
//        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun PropertiesDisplay(
    properties: List<PropertyDetails>,
    navigateToSpecificProperty: (propertyId: String) -> Unit,
    executionStatus: ExecutionStatus,
    modifier: Modifier = Modifier
) {
    var showText by remember { mutableStateOf(false) }

    if(executionStatus == ExecutionStatus.LOADING) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else if (executionStatus == ExecutionStatus.SUCCESS) {
//        if(!uiState.internetPresent) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Internet not present")
//            }
//        }
        if (properties.isEmpty()) {
            LaunchedEffect(Unit) {
                delay(2000L)
                showText = true
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if(showText) {
                    Text(text = "Properties not added yet")
                } else {
                    CircularProgressIndicator()
                }
            }
        } else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(properties) {
                    PropertyItem(
                        approved = it.approved,
                        navigateToSpecificProperty = navigateToSpecificProperty,
                        property = it,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }

}
