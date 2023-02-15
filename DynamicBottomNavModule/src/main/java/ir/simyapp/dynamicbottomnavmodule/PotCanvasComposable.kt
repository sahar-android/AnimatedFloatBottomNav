package ir.simyapp.dynamicbottomnavmodule

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun PotCanvas(
    modifier: Modifier,
    curvePostelorance: Float,
    color: Color,
    itemTabImage: Int,
    showCurveBottmBar: Boolean,
    centerOfPot: Float,
    itemIndex: Int
) {

    AnimatedVisibility(
        visible = !showCurveBottmBar,
        exit = fadeOut(animationSpec = tween(easing = FastOutSlowInEasing)),
        enter = fadeIn(animationSpec = tween(durationMillis = 50, easing = FastOutSlowInEasing))
    ) {
        Box(
            modifier = modifier
                .height(90.dp)
                .fillMaxWidth()
                .background(color)
        )
    }

    AnimatedVisibility(
        visible = showCurveBottmBar,
        exit = fadeOut(animationSpec = tween(durationMillis = 50, easing = FastOutSlowInEasing)),
        enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing))
    ) {

        Box(modifier = modifier.height(90.dp)) {
            val coroutine = rememberCoroutineScope()
            val diameter = 40.dp
            val radiusDp = diameter / 2

            val density = LocalDensity.current
            val cutoutRadius = density.run { radiusDp.toPx() }
            var fabRadius = cutoutRadius * 2

            val contextResources = LocalContext.current.resources

            var iconY by remember { mutableStateOf(50f) }
            var iconMoving by remember { mutableStateOf(50f) }
            var showFirstIconAtFirstRun by remember{ mutableStateOf(true) }
            if(itemIndex!=0) showFirstIconAtFirstRun=false
            Log.i("ghjfghjfg", "PotCanvasTest0: $showFirstIconAtFirstRun")

            LaunchedEffect(key1 = itemIndex) {
                animate(
                    50f,
                    iconY,
                    animationSpec = tween(
                        500,
                        100,
                        easing = FastOutSlowInEasing
                    )
                ) { value: Float, velocity: Float ->
                    iconMoving = value
                }
            }

            Canvas(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {


                val centerOfCircle = Offset(x = centerOfPot, y = 0f)
                val circleRadius = fabRadius * .8f
                iconY = centerOfCircle.y - 0.8f * circleRadius

                drawCircle(
                    color = color,
                    radius = circleRadius,
                    center = centerOfCircle
                )

                val icon = BitmapFactory.decodeResource(
                    contextResources,
                    itemTabImage
                )

                try {
                    val resizedBitmap = Bitmap.createScaledBitmap(
                        icon,
                        (1.5 * circleRadius).toInt(),
                        (1.5 * circleRadius).toInt(),
                        true
                    )


                    val topLeftOfImage =
                        Offset(
                            x = centerOfCircle.x - 0.8f * circleRadius, y = iconMoving
                        )

                    drawImage(
                        resizedBitmap.asImageBitmap(),
                        topLeftOfImage,
                        colorFilter = ColorFilter.tint(Color.White),
                    )

                    if(showFirstIconAtFirstRun){
                        Log.i("ghjfghjfg", "PotCanvasTest: ")
                        val topLeftOfImage =
                            Offset(x = centerOfCircle.x - 0.8f * circleRadius, y = iconY)
                        drawImage(
                            resizedBitmap.asImageBitmap(),
                            topLeftOfImage,
                            colorFilter = ColorFilter.tint(Color.White),
                        )
                    }
                }catch (e:java.lang.Exception){

                }






                val path = Path().apply {

                    val centerX = centerOfPot
                    val x0 = centerX - fabRadius * 1.15f
                    val y0 = 0f

                    // offset of the first control point (top part)
                    val topControlX = x0 + fabRadius * .5f
                    val topControlY = y0

                    // offset of the second control point (bottom part)
                    val bottomControlX = x0
                    val bottomControlY = y0 + fabRadius

                    // first curve
                    // set the starting point of the curve (P2)
                    val firstCurveStart = Offset(x0, y0)

                    // set the end point for the first curve (P3)
                    val firstCurveEnd = Offset(centerX, fabRadius * 1f)

                    // set the first control point (C1)
                    val firstCurveControlPoint1 = Offset(
                        x = topControlX,
                        y = topControlY
                    )

                    // set the second control point (C2)
                    val firstCurveControlPoint2 = Offset(
                        x = bottomControlX,
                        y = bottomControlY
                    )

                    // second curve
                    // end of first curve and start of second curve is the same (P3)
                    val secondCurveStart = Offset(
                        x = firstCurveEnd.x,
                        y = firstCurveEnd.y
                    )

                    // end of the second curve (P4)
                    val secondCurveEnd = Offset(
                        x = centerX + fabRadius * 1.15f,
                        y = 0f
                    )

                    // set the first control point of second curve (C4)
                    val secondCurveControlPoint1 = Offset(
                        x = secondCurveStart.x + fabRadius,
                        y = bottomControlY
                    )

                    // set the second control point (C3)
                    val secondCurveControlPoint2 = Offset(
                        x = secondCurveEnd.x - fabRadius / 2,
                        y = topControlY
                    )


                    lineTo(x = firstCurveStart.x, y = firstCurveStart.y)

                    // bezier curve with (P2, C1, C2, P3)

                    cubicTo(
                        x1 = firstCurveControlPoint1.x,
                        y1 = firstCurveControlPoint1.y,
                        x2 = firstCurveControlPoint2.x,
                        y2 = firstCurveControlPoint2.y,
                        x3 = firstCurveEnd.x,
                        y3 = firstCurveEnd.y
                    )

                    // bezier curve with (P3, C4, C3, P4)
                    cubicTo(
                        x1 = secondCurveControlPoint1.x,
                        y1 = secondCurveControlPoint1.y,
                        x2 = secondCurveControlPoint2.x,
                        y2 = secondCurveControlPoint2.y,
                        x3 = secondCurveEnd.x,
                        y3 = secondCurveEnd.y
                    )

                    lineTo(x = size.width, y = 0f)

                    lineTo(x = 0f + size.width, y = size.height)

                    lineTo(x = 0f, y = size.height)

                    close()
                }
                drawPath(
                    path,
                    color = color
                )


            }

        }
    }

}