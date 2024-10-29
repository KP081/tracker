package com.example.tracker.feature.add_expanse

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tracker.R
import com.example.tracker.Utils.formateDateToHumanReadableForm
import com.example.tracker.data.modul.ExpanseEntity
import com.example.tracker.viewmodel.AddExpanseViewModel
import com.example.tracker.viewmodel.AddExpanseViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AddExpense(navController: NavController) {

    val viewModel: AddExpanseViewModel = AddExpanseViewModelFactory(LocalContext.current).create(AddExpanseViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topbar, nameRow, card, transaction) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.bg_1),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(topbar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
            )

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ){

                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(35.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Text(
                    text = "Add Expense",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.three_dots),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(20.dp)
                )
            }

            DataForm(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onAddExpanseClick = {
                    coroutineScope.launch {
                        if(viewModel.addExpanse(it)){
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun DataForm(modifier: Modifier , onAddExpanseClick : (model:ExpanseEntity) -> Unit) {

    val name = remember {
        mutableStateOf("")
    }
    val amount = remember {
        mutableStateOf("")
    }
    val date = remember {
        mutableStateOf(0L)
    }
    val dateDialogVisibility = remember {
        mutableStateOf(false)
    }
    val category = remember {
        mutableStateOf("")
    }
    val type = remember {
        mutableStateOf("")
    }
    ElevatedCard (
        modifier = modifier
            .padding(top = 60.dp)
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Name",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = modifier.size(4.dp))
            OutlinedTextField(value = name.value, onValueChange = {
                name.value = it
            } , modifier = modifier.fillMaxWidth())
            Spacer(modifier = modifier.size(8.dp))

            Text(
                text = "Amount",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = modifier.size(4.dp))
            OutlinedTextField(value = amount.value, onValueChange = {
                amount.value = it
            } , modifier = modifier.fillMaxWidth())
            Spacer(modifier = modifier.size(8.dp))

            //date
            Text(
                text = "Date",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = modifier.size(4.dp))
            OutlinedTextField(
                value = if(date.value == 0L) "" else formateDateToHumanReadableForm(date.value),
                onValueChange = {} ,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        dateDialogVisibility.value = true
                    },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Gray,
                    disabledTextColor = Color.Black
                )
            )
            Spacer(modifier = modifier.size(8.dp))

            //dropdown

            Text(
                text = "Category",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = modifier.size(4.dp))
            ExpanseDropDown(listOfItems = listOf("Netflix" , "Paypal" , "Starbucks" , "Salary" , "Upwork")) {
                category.value = it
            }
            Spacer(modifier = modifier.size(8.dp))
            //type

            Text(
                text = "Type",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = modifier.size(4.dp))
            ExpanseDropDown(listOfItems = listOf("Income" , "Expanse")) {
                type.value = it
            }
            Spacer(modifier = modifier.size(8.dp))

            Button(
                onClick = {
                    val model = ExpanseEntity(
                        null,
                        name.value,
                        amount.value.toDoubleOrNull() ?: 0.0,
                        date.value,
                        category.value,
                        type.value,
                    )
                    onAddExpanseClick(model)
                },
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Add Expense",
                    fontSize = 14.sp,
                )
            }
        }
        if(dateDialogVisibility.value){
            ExpanseDatePickerDialog(
                onDateSelected = {
                    date.value = it
                    dateDialogVisibility.value = false
                } ,
                onDismiss = {
                    dateDialogVisibility.value = false
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpanseDatePickerDialog(
    onDateSelected : (date : Long) -> Unit,
    onDismiss : () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Cancel")
            }
        })
    {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpanseDropDown(listOfItems : List<String> , onItemSelected : (item : String) -> Unit) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf<String>(listOfItems[0])
    }

    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = { expanded.value = it }) {
        TextField(value = selectedItem.value, onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            }
        )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = {  }) {
            listOfItems.forEach {
                DropdownMenuItem(text = { Text(text = it) }, onClick = {
                    selectedItem.value = it
                    onItemSelected(selectedItem.value)
                    expanded.value = false
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddExpense() {
    AddExpense(rememberNavController())
}