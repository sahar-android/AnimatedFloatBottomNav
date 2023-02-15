package ir.simyapp.dynamicbottomnavmodule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class Items(
    val title:String,
    val icon:Int
)

fun ConvertToMutableItems(titles:List<String>,icons:List<Int>):MutableList<Items>{
    val items= mutableListOf<Items>()
    val itemsCount=when{
        titles.size>icons.size -> titles.size
        else -> icons.size
    }

    for (i in 0 until itemsCount){
        items.add(Items(titles[i], icons[i]))
    }

    return items
}

@Composable
fun MakeBottomNav(itemList:MutableList<Items>, themeColor: Color, onClickItems:(itemNumber:Int)->Unit){
    onClickItems(0)
    BoxWithConstraints(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

        ) {
        val coroutine = rememberCoroutineScope()
        val configuration = LocalConfiguration.current
        val heightDp = configuration.screenHeightDp.dp
        val widthDp = configuration.screenWidthDp.dp
        val density = LocalDensity.current
        val heightInFloat = density.run { heightDp.toPx() }
        val widthInFloat = density.run { widthDp.toPx() }
        var itemTabImage by remember{ mutableStateOf(itemList[0].icon) }
        val itemsCount=itemList.size
        val widthParagon=widthInFloat/(2*itemsCount)
        var indexItem by remember{ mutableStateOf(0) }
        var fontSize by remember { mutableStateOf(10.sp) }
        var checkWhichTabCilicked by remember{ mutableStateOf(false) }
        if(checkWhichTabCilicked) fontSize=14.sp else 10.sp



        PotCanvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .align(Alignment.BottomCenter),
            50f,
            themeColor,
            itemTabImage,
            true,
            widthParagon+(widthInFloat/itemList.size)*indexItem,
            itemIndex = indexItem
        )

        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(90.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.Top
        ){
            itemList.forEachIndexed { index, items ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable {
                            coroutine.launch {
                                indexItem=index
                                itemTabImage=items.icon
                                onClickItems(index)

                            }
                        }
                ){

                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painter = painterResource(id = items.icon),
                        contentDescription = items.title,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .weight(1f)
                            .alpha(if(index==indexItem) 0f else 1f))

                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = items.title,
                        color = Color.White,
                        fontSize = if(index==indexItem) 14.sp else 10.sp,
                        modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(1f))

                }
            }
        }
    }
}

@Composable
fun AnimBottomNavigation(titles:List<String>, icons:List<Int>, color: Color, onClickItems:(itemNumber:Int)->Unit){
    val mutableItems= ConvertToMutableItems(titles,icons)
    MakeBottomNav(mutableItems,color){
        onClickItems(it)
    }
}