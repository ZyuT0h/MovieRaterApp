package com.it2161.dit234453p.assignment1.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit234453p.assignment1.MovieRaterApplication

@Composable
fun LandingScreen(navController: NavHostController) {
    val context = LocalContext.current
    val movieRaterApp = context.applicationContext as MovieRaterApplication
    val movieList = movieRaterApp.data

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(movieList.size) { index ->
                val movie = movieList[index]
                MovieItemCard(
                    posterImage = movieRaterApp.getImgVector(movie.image),
                    title = movie.title,
                    synopsis = movie.synopsis,
                    rating = movie.ratings_score,
                    onClick = {
                        navController.navigate("MovieDetail/$index/${movie.title}")
                    }
                )
            }
        }
    }
}

@Composable
fun MovieItemCard(
    posterImage: Bitmap,
    title: String,
    synopsis: String,
    rating: Float,
    onClick: () -> Unit
) {
    val validRating = if (rating.isNaN() || rating < 0) 0f else rating

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                bitmap = posterImage.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = synopsis, fontSize = 12.sp, maxLines = 2)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "⭐ $validRating/10",
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun LandingScreenPreview() {
    LandingScreen(navController = rememberNavController())
}