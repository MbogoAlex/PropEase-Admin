package com.tms.propease_admin.utils

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tms.propease_admin.R
import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.model.PropertyImage
import com.tms.propease_admin.ui.screen.property.property
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
    paid: Boolean,
    property: PropertyDetails,
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                navigateToSpecificPropertyScreen(property.propertyId.toString())
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
                if(!approved) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Gray
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Unverified".uppercase(),
                            fontSize = 11.sp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(
                                    start = 10.dp,
                                    top = 5.dp,
                                    end = 10.dp,
                                    bottom = 5.dp,
                                )

                        )
                    }
                } else if(approved && !paid) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Gray
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "unpaid".uppercase(),
                            fontSize = 11.sp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(
                                    start = 10.dp,
                                    top = 5.dp,
                                    end = 10.dp,
                                    bottom = 5.dp,
                                )

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategorySelection(
    categories: List<CategoryItem>,
    categoryNameSelected: String,
    onChangeCategory: (categoryItem: CategoryItem) -> Unit,
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
        Button(
            shape = RoundedCornerShape(10.dp),
            onClick = { expanded = !expanded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier

            ) {
                Text(
                    text = "Category".takeIf { categoryNameSelected.isEmpty() } ?: categoryNameSelected,
                    modifier = Modifier
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
                            i
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
    rooms: List<String>,
    numberOfRoomsSelected: String,
    onChangeNumberOfRooms: (rooms: String) -> Unit,
    modifier: Modifier = Modifier
) {
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

    val defaultRooms = listOf(
        "Bedsitter - Rental, Airbnb, On sale",
        "One bedroom - Rental, Airbnb, On sale",
        "Two bedrooms - Rental, Airbnb, On sale",
        "Three bedrooms - Rental, Airbnb, On sale",
        "Four bedrooms - Rental, Airbnb, On sale",
        "Five bedrooms - Rental, Airbnb, On sale",
        "Single room - Shop, Office, On sale",
        "Two rooms - Shop, Office, On sale",
        "Three rooms - Shop, Office, On sale",

        )

    Column {
        Button(
            shape = RoundedCornerShape(10.dp),
            onClick = { expanded = !expanded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Text(
                    text = "No. Rooms".takeIf { numberOfRoomsSelected.isEmpty() }
                        ?: numberOfRoomsSelected,
                    modifier = Modifier
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
            if(rooms.isEmpty()) {
                defaultRooms.forEachIndexed { index, i ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = i
                            )
                        },
                        onClick = {
                            onChangeNumberOfRooms(
                                i
                            )

                            expanded = !expanded
                        }
                    )
                }
            } else {
                rooms.forEachIndexed { index, i ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = i
                            )
                        },
                        onClick = {
                            onChangeNumberOfRooms(
                                i
                            )

                            expanded = !expanded
                        }
                    )
                }
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
    rooms: List<String>,
    filteringOn: Boolean,
    selectedLocation: String,
    categories: List<CategoryItem>,
    categoryNameSelected: String,
    numberOfRoomsSelected: String,
    onSearchLocationChanged: (newValue: String) -> Unit,
    onChangeNumberOfRooms: (rooms: String) -> Unit,
    onChangeCategory: (categoryItem: CategoryItem) -> Unit,
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
                rooms = rooms,
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
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit,
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
                    Text(text = "No properties")
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
                        paid = it.paid,
                        navigateToSpecificPropertyScreen = navigateToSpecificPropertyScreen,
                        property = it,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PropertyImages(
    images: List<PropertyImage>
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column {

        Card {
            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                if(images.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .alpha(0.5f)
                            .height(250.dp)
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
                HorizontalPager(count = images.size, state = pagerState) { page ->
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(images[page].name)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.loading_img),
                        error = painterResource(id = R.drawable.ic_broken_image),
                        contentScale = ContentScale.Crop,
                        contentDescription = property.title,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                    )

                }
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
                        color = Color.White,
                        modifier = Modifier
                            .alpha(0.5f)
                            .background(Color.Black)
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                            )
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}

@Composable
fun PropertyInfo(
    property: PropertyDetails,
    executionStatus: ExecutionStatus,
    onVerifyPropertyClicked: () -> Unit,
    onRejectPropertyClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Black)
                ) {
                    Text(
                        text = property.category,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                top = 5.dp,
                                end = 10.dp,
                                bottom = 10.dp
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.house),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${property.rooms} room".takeIf { it.length == 1 } ?: "${property.rooms} rooms",
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Posted on ${property.postedDate}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )


        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = property.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null
            )
            Text(
                text = "${property.location.county}, ${property.location.address}",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        property.features.distinct().forEachIndexed { index, s ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.circle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(10.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = property.features[index])
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .heightIn(max = 150.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = property.description,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatMoneyValue(property.price),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "/month"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(30.dp))
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(property.user.profilePic == null) {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(property.user.profilePic)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                    contentDescription = property.title,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
            ) {
                Text(
                    text = "${property.user.fname} ${property.user.lname}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = "Owner",
                    fontSize = 13.sp
                )
                Text(
                    text = property.user.phoneNumber,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Card {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Green)
                        .padding(10.dp)
                        .clickable {
                            val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${property.user.phoneNumber}")
                            }
                            context.startActivity(phoneIntent)
                        }
                ) {
                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.phone),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Card {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Blue)
                        .padding(10.dp)
                        .clickable {
                            val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("smsto:${property.user.phoneNumber}")
                            }
                            context.startActivity(smsIntent)
                        }
                ) {
                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
        }
        if(!property.approved) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                enabled = executionStatus != ExecutionStatus.LOADING,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onVerifyPropertyClicked()
                }
            ) {
                if(executionStatus == ExecutionStatus.LOADING) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Verify")
                }

            }
        } else if(property.approved) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                enabled = executionStatus != ExecutionStatus.LOADING,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onRejectPropertyClicked()
                }
            ) {
                if(executionStatus == ExecutionStatus.LOADING) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Disapprove")
                }

            }
        }

    }
}
