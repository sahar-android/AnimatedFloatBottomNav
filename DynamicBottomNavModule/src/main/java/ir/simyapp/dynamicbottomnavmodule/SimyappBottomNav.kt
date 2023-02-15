package ir.simyapp.dynamicbottomnavmodule

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class SimyappBottomNav {

    companion object{

        @Composable
        fun MyNav(titles:List<String>, icons:List<Int>, color: Color, onClickItems:(itemNumber:Int)->Unit){
            AnimBottomNavigation(titles,icons,color){
                onClickItems(it)
            }
        }
    }
}