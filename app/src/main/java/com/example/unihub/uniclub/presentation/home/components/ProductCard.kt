package com.example.unihub.uniclub.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.example.unihub.R
import com.example.vibestore.ui.theme.poppinsFontFamily


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    rating: String,
    price: String,
    categories: List<String>,
    addToCart: () -> Unit,
) {
    val ratingState by remember { mutableFloatStateOf(rating.toFloat()) }

    Column(
        modifier = modifier
            .width(160.dp)
            .padding(16.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = "https://marinaracewear.com/storage/media/attributes/9/0/8/6/6/90866/conversions/2-category.jpg",
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .width(160.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(10.dp))

        )
        if (categories.isNotEmpty()) {
            categories.map {
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.White
                    )
                }
            }

        }
        Column {
            Text(
                text = title,
                maxLines = 1,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    text = "$rating/5",
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(4.dp))
                RatingBar(
                    modifier = Modifier.offset(y = (-2).dp),
                    rating = ratingState,
                )
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .offset(y = (5).dp)
                        .weight(1f),
                    text = price,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily,
                )

                Icon(
                    painter = painterResource(R.drawable.icon_cart_outlined),
                    contentDescription = "Cart",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .clickable { addToCart() }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}


@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    starCount: Int = 5,
    filledStarColor: Color = Color("#FFB000".toColorInt()),
    unfilledStarColor: Color = MaterialTheme.colorScheme.outline,
) {
    Row(modifier = modifier) {
        for (i in 1..starCount) {
            val starFraction = rating - i + 1
            val iconTint = when {
                starFraction >= 1 -> filledStarColor
                else -> unfilledStarColor
            }
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    val rating by remember { mutableFloatStateOf(3.5f) }

    RatingBar(
        rating = rating,
    )
}
