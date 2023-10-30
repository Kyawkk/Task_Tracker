package com.kyawzinlinn.tasktracker.customview

import android.content.res.Resources
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.rpc.context.AttributeContext.Resource
import java.util.Random
import kotlin.math.exp
import kotlin.math.pow

fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density
fun Dp.dpToPx() = value.dpToPx()
fun Int.dpToPx() = toFloat().dpToPx()
private val random = Random()
fun Float.randomTillZero() = this * random.nextFloat()
fun randomInRange(min:Float,max:Float) = min + (max - min).randomTillZero()
fun Float.mapInRange(inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
    return outMin + (((this - inMin) / (inMax - inMin)) * (outMax - outMin))
}

@Composable
fun ControlledExplosion() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var progress by remember { mutableStateOf(0f) }

        Explosion(progress)

        Slider(
            modifier = Modifier.width(200.dp),
            value = progress,
            onValueChange = {progress = it}
        )
    }
}

@Composable
fun Explosion(progress: Float) {
    val sizeDp = 200.dp
    val sizePx = sizeDp.dpToPx()
    val sizePxHalf = sizePx / 2

    val velocity = 4 * sizePxHalf
    val acceleration = -2 * velocity
    val currentTime = progress
    val verticalDisplacement = velocity * currentTime + 0.5 * acceleration * currentTime.pow(2)

    val particles = remember {
        List(100) {
            Particle(
                color = Color(listOf(0xffea4335,0xff4285f4,0xfffbbc05,0xff34a853).random()),
                startXPosition = sizePxHalf.toInt(),
                startYPosition = sizePxHalf.toInt(),
                maxHorizontalDisplacement = sizePxHalf * randomInRange(-0.9f, 0.9f),
                maxVerticalDisplacement = sizePxHalf * randomInRange(0.2f, 0.38f),
            )
        }
    }

    particles.forEach {
        it.updateProgress(progress)
    }

    Canvas(
        modifier = Modifier
            .size(sizeDp)
            .border(width = 1.dp, color = Color(0x26000000))
    ) {
        drawLine(
            color = Color.Black,
            start = Offset(0f,sizePxHalf),
            end = Offset(sizePx, sizePxHalf),
            strokeWidth = 2.dpToPx()
        )

        drawLine(
            color = Color.Black,
            start = Offset(sizePxHalf,0f),
            end = Offset(sizePxHalf,sizePx),
            strokeWidth = 2.dpToPx()
        )

        particles.forEach { particle ->
            drawCircle(
                color = particle.color,
                radius = 2.dp.dpToPx(),
                center = Offset(
                    particle.currentXPosition,
                    particle.currentYPosition
                )
            )
        }
    }
}


class Particle(
    val color: Color,
    val startXPosition: Int,
    val startYPosition: Int,
    val maxHorizontalDisplacement: Float,
    val maxVerticalDisplacement: Float
) {
    val velocity = 4 * maxVerticalDisplacement
    val acceleration = -2 * velocity
    var currentXPosition = 0f
    var currentYPosition = 0f
    var alpha = 0f

    var visibilityThresholdLow = randomInRange(0f,0.14f)
    var visibilityThresholdHigh = randomInRange(0f,0.4f)

    fun updateProgress(explosionProgress: Float){
        val trajectoryProgress = if (explosionProgress < visibilityThresholdLow || explosionProgress > (1 - visibilityThresholdHigh)) {
            alpha = 0f; return
        } else (explosionProgress - visibilityThresholdLow).mapInRange(0f,1f - visibilityThresholdHigh - visibilityThresholdLow, 0f ,1f)

        alpha = 1f
        val currentTime = trajectoryProgress.mapInRange(0f,1f,0f,1.4f)
        val verticalDisplacement = currentTime * velocity + 0.5 * acceleration * currentTime.pow(2)
        currentYPosition = (startXPosition - verticalDisplacement).toFloat()
        currentXPosition = (startYPosition + maxHorizontalDisplacement * trajectoryProgress)
    }

}

@Composable
@Preview(showBackground = true)
fun ExplosionPreview() {
    ControlledExplosion()
}