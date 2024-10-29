package com.example.tracker.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tracker.R
import com.example.tracker.Utils
import com.example.tracker.data.modul.ExpanseEntity
import com.example.tracker.viewmodel.HomeViewModel
import com.example.tracker.viewmodel.HomeViewModelFactory
import androidx.compose.foundation.layout.Row as Row

@Composable
fun HomeScreen(navController: NavController) {

    val viewModel:HomeViewModel = HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topbar, nameRow, card, transaction, add) = createRefs()

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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(topbar.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column {
                    Text(text = "Good Afternoon", fontSize = 16.sp, color = Color.White)
                    Text(
                        text = "Hello Guys",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = null,
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.CenterEnd)
                        .size(30.dp, 30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            val state = viewModel.expanse.collectAsState(initial = emptyList())
            val Expanse = viewModel.getTotalExpanse(state.value)
            val Income = viewModel.getTotalIncome(state.value)
            val Balance = viewModel.getBalance(state.value)

            CardItems(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                Balance , Income , Expanse
            )

            TransactionList(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(transaction) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    } , list = state.value , viewModel
            )

            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_add),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier
                    .constrainAs(add) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("/add")
                    }
            )
        }
    }
}

@Composable
fun TransactionList(modifier: Modifier , list: List<ExpanseEntity> , viewModel: HomeViewModel) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ){
                Text(text = "Recent Transaction" , fontSize = 20.sp)
                Text(
                    text = "See All",
                    fontSize = 16.sp,
                )
            }
        }

        items(list) {item ->
            TransactionItem(
                titel = item.title,
                amount = item.amount.toString(),
                icon = viewModel.getItemIcone(item),
                date = Utils.formateDateToHumanReadableForm(item.date),
                color = if(item.type == "Income") Color.Green else Color.Red
            )
        }
    }
}

@Composable
fun TransactionItem(titel: String , amount: String , icon: Int , date : String , color: Color) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = titel , fontSize = 16.sp)
                Text(text = date , fontSize = 12.sp)
            }
        }
        Text(
            text = amount,
            fontSize = 20.sp,
            color = color,
            modifier = Modifier.align(CenterEnd)
        )
    }
}

@Composable
fun CardItems(modifier: Modifier , balance:String , income: String , expanses : String) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(19.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(red = 15, green = 117, blue = 88),
        ),
    ) {
        Box(
            modifier = modifier
                .padding(35.dp)
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Center
        ) {

            Column {
                Box(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(text = "Total Balance", fontSize = 16.sp, color = Color.White)
                        Text(
                            text = balance,
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.three_dots),
                        contentDescription = null,
                        modifier = Modifier
                            .align(androidx.compose.ui.Alignment.CenterEnd)
                            .size(20.dp, 20.dp),
                    )
                }

                Spacer(modifier = modifier.size(30.dp))

                Box(
                    modifier = modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CardRowItems(
                            modifier = modifier,
                            titel = "Income",
                            amount = income,
                            image = R.drawable.income
                        )

                        CardRowItems(
                            modifier = modifier,
                            titel = "Expense",
                            amount = expanses,
                            image = R.drawable.expense
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CardRowItems(modifier: Modifier,titel:String , amount:String , image: Int) {
    Column {
        Row {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = modifier.size(20.dp, 15.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = modifier.size(4.dp))
            Text(
                text = titel,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Text(
            text = amount,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}