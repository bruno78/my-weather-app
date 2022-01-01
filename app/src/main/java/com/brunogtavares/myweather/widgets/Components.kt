package com.brunogtavares.myweather.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.brunogtavares.myweather.R
import com.brunogtavares.myweather.model.WeatherInfo
import com.brunogtavares.myweather.ui.theme.MyWeatherSurface
import com.brunogtavares.myweather.ui.theme.MyWeatherYellow
import com.brunogtavares.myweather.ui.theme.Typography
import com.brunogtavares.myweather.utils.Constants
import com.brunogtavares.myweather.utils.formatDateTime
import com.brunogtavares.myweather.utils.formatDecimals
import com.brunogtavares.myweather.utils.formatWeekDate

@Composable
fun WeatherInfoIconText(
    text: String,
    @DrawableRes drawableRes: Int,
    size: Dp = 20.dp
) {
    Row(
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(size)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun WeatherInfoTextIcon(
    text: String,
    @DrawableRes drawableRes: Int,
    size: Dp = 20.dp
) {
    Row(
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption
        )
        Icon(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 4.dp)
                .size(size)
        )
    }
}

@Composable
fun WeatherImage(icon: String, size: Dp = 80.dp) {
    Image(
        painter = rememberImagePainter(Constants.WEATHER_IMAGE_BASE_URL + "${icon}.png"),
        contentDescription = null, // not important for accessibility
        modifier = Modifier.size(size)
    )
}


@Composable
fun SunriseSunsetRows(weatherInfo: WeatherInfo) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        WeatherInfoIconText(
            text = formatDateTime(weatherInfo.sunrise),
            drawableRes = R.drawable.sunrise,
            size = 30.dp
        )
        WeatherInfoTextIcon(
            text = formatDateTime(weatherInfo.sunset),
            drawableRes = R.drawable.sunset,
            size = 30.dp
        )
    }
}

@Composable
fun HumidityWindPressureRow(weatherInfo: WeatherInfo) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        WeatherInfoIconText(text = "${weatherInfo.humidity}%", drawableRes = R.drawable.humidity)
        WeatherInfoIconText(text = "${weatherInfo.pressure} psi", drawableRes = R.drawable.pressure)
        WeatherInfoIconText(text = "${weatherInfo.humidity} mph", drawableRes = R.drawable.wind)
    }
}

@Composable
fun DailyWeather(dailyWeather: List<WeatherInfo>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "This Week",
            style = Typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = MyWeatherSurface,
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(dailyWeather) { item ->
                    DailyWeatherRow(
                        dayOfWeek = formatWeekDate(item.dt),
                        weatherIcon = item.weather[0].icon,
                        weatherCondition = item.weather[0].description,
                        min = formatDecimals(item.temp.min),
                        max = formatDecimals(item.temp.max)
                    )
                }
            }
        }
    }
}

@Composable
fun DailyWeatherRow(
    dayOfWeek: String,
    weatherIcon: String,
    weatherCondition: String,
    min: String,
    max: String,
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dayOfWeek,
                style = Typography.h6,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 5.dp)
            )
            WeatherImage(weatherIcon)
            WeatherCondition(weatherCondition)
            MinAndMax(min, max)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCondition(
    text: String = "light rain"
) {
    Surface(
        modifier = Modifier.padding(0.dp),
        shape = CircleShape,
        color = MyWeatherYellow
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = text,
            style = Typography.caption
        )
    }
}

@Preview
@Composable
fun MinAndMax(
    min: String = "43",
    max: String = "37",
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Blue.copy(alpha = 0.7f),
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("$max°")
            withStyle(
                style = SpanStyle(
                    color = Color.LightGray
                )
            ) {
                append("$min°")
            }
        }
    }
    Text(text = annotatedString)
}