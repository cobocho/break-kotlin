package com.example.shoppingcart

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.location.LocationUtils

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var isEditing: Boolean = false,
    var address: String = ""
)

@Composable
fun ShoppingList(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String
) {
    var products by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var addProductName by remember { mutableStateOf("") }
    var addProductQuantity by remember { mutableStateOf("") }


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            if (it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && it[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                if (rationaleRequired) {
                    Toast.makeText(context, "이 기능은 권한이 필요합니다.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "이 기능은 권한이 필요합니다. 설정해주세요.", Toast.LENGTH_LONG).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(products) { item ->
                if (item.isEditing) {
                    ShoppingItemEditor(item = item, onEditComplete = { editName, editQuatity ->
                        products = products.map { it.copy(isEditing = false) }
                        val editedItem = products.find { it.id == item.id }
                        editedItem?.let {
                            it.name = editName
                            it.quantity = editQuatity
                        }
                    })
                } else {
                    ShoppingListItem(item, onEditClick = {
                        products = products.map { it.copy(isEditing = it.id == item.id) }
                    }, onDeleteClick = {
                        products = products.filter { it.id != item.id }
                    })
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                addProductQuantity = ""
                addProductName = ""
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        showDialog = false
                    }) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        val newItem = ShoppingItem(
                            products.size + 1,
                            name = addProductName,
                            quantity = addProductQuantity.toInt(),
                            address = address
                        )
                        products = products + newItem
                        showDialog = false
                    }) {
                        Text("Add")
                    }
                }
            },
            text = {
                Column {
                    Text("Add Shopping Item")
                    OutlinedTextField(
                        value = addProductName,
                        onValueChange = { value -> addProductName = value })
                    OutlinedTextField(
                        value = addProductQuantity.toString(),
                        onValueChange = { newText ->
                            addProductQuantity = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Button(
                        onClick = {
                            if (locationUtils.hasLocationPermission((context))) {
                                locationUtils.requestLocationUpdates(viewModel)
                                navController.navigate("locationscreen") {
                                    this.launchSingleTop
                                }
                            } else {
                                requestPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        }
                    ) {
                        Text("Address")
                    }
                }
            }
        )
    }
}

@Composable
fun ShoppingItemEditor(item: ShoppingItem, onEditComplete: (String, Int) -> Unit) {
    var editProductName by remember {
        mutableStateOf(item.name)
    }
    var editProductQuantity by remember {
        mutableStateOf(item.quantity.toString())
    }
    var isEditing by remember {
        mutableStateOf(item.isEditing)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BasicTextField(
            value = editProductName,
            onValueChange = { editProductName = it },
            singleLine = true,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        )
        BasicTextField(
            value = editProductQuantity,
            onValueChange = { editProductQuantity = it },
            singleLine = true,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        )
        Button(onClick = {
            isEditing = false
            onEditComplete(editProductName, editProductQuantity.toIntOrNull() ?: 0)
        }) {
            Text("Edit")
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Row {
                Text(item.name, modifier = Modifier.padding(8.dp))
                Text("${item.quantity}EA", Modifier.padding(8.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Text(text = item.address)
            }
        }
        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "${item.name} Edit")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "${item.name} Delete")
            }
        }
    }
}