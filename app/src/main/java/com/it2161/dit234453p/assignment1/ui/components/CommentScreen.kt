package com.it2161.dit234453p.assignment1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit234453p.assignment1.MovieRaterApplication

@Composable
fun CommentMovieScreen(commentIndex: Int?, movieTitle: String, navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val movieRaterApp = context.applicationContext as MovieRaterApplication
    val movie = movieRaterApp.data.find { it.title == movieTitle } // Find the movie by title
    val comment = commentIndex?.let { movie?.comment?.getOrNull(it) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (comment != null) {
            Column(modifier = Modifier.padding(16.dp)) {
                // User Profile Section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Profile Picture (Placeholder for now)
                    UserProfilePicture(userName = comment.user)

                    Spacer(modifier = Modifier.width(16.dp))

                    // User Name
                    Text(
                        text = comment.user,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Date and Time
                    Text(
                        text = getTimeAgo(comment.date, comment.time),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Comment Text
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        } else {
            // Fallback for no comment
            Text(
                text = "Comment not found!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}



@Preview
@Composable
fun CommentMovieScreenPreview() {
    CommentMovieScreen(
        navController = rememberNavController(),
        commentIndex = 0,
        movieTitle = "Movie Title"
    )
}