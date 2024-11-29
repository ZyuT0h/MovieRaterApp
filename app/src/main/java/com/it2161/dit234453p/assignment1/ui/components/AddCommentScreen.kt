package com.it2161.dit234453p.assignment1.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.it2161.dit234453p.assignment1.MovieRaterApplication
import com.it2161.dit234453p.assignment1.data.Comments
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AddCommentScreen(movieIndex: Int, movieTitle: String?, navController: NavHostController) {
    val context = LocalContext.current
    val movieRaterApp = context.applicationContext as MovieRaterApplication
    val movie = movieRaterApp.data[movieIndex]
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            bitmap = movieRaterApp.getImgVector(movie.image).asImageBitmap(),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(bottom = 16.dp)
        )

        // Centering "Add comment" Label
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center // Align content in the center
        ) {
            Text(
                text = "Add comment",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Comment Input Field
        TextField(
            value = commentText,
            onValueChange = { commentText = it },
            placeholder = { Text("Enter your comment here") }, // Placeholder for input
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            maxLines = 5,
            singleLine = false
        )

        Button(
            onClick = {
                if (commentText.isBlank()) {
                    // Show a Toast if the comment is empty
                    Toast.makeText(context, "Comment is required.", Toast.LENGTH_SHORT).show()
                } else {
                    // Validation passed: Save the comment
                    val currentDateTime = LocalDateTime.now()
                    val formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))

                    val userName = movieRaterApp.userProfile?.userName ?: "Unknown User"

                    movie.comment.add(
                        Comments(
                            user = userName,
                            comment = commentText,
                            date = formattedDate,
                            time = formattedTime
                        )
                    )
                    movieRaterApp.saveListFile(context)
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Submit")
        }
    }
}