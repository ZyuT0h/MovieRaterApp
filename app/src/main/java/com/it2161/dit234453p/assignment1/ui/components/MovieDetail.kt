package com.it2161.dit234453p.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit234453p.assignment1.MovieRaterApplication
import com.it2161.dit234453p.assignment1.data.Comments
import com.it2161.dit234453p.assignment1.data.MovieItem
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MovieDetailScreen(movieIndex: Int, movieTitle: String, navController: NavHostController) {
    val context = LocalContext.current
    val movieRaterApp = context.applicationContext as MovieRaterApplication
    val movie = movieRaterApp.data[movieIndex]

    // Sort comments by date and time (latest first)
    val sortedComments = movie.comment.sortedWith { comment1, comment2 ->
        val comment1DateTime = LocalDateTime.parse("${comment1.date} ${comment1.time}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val comment2DateTime = LocalDateTime.parse("${comment2.date} ${comment2.time}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        // Compare comments and sort by descending order (latest first)
        comment2DateTime.compareTo(comment1DateTime)
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Movie details section
        item {
            Image(
                bitmap = movieRaterApp.getImgVector(movie.image).asImageBitmap(),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Director: ${movie.director}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Release Date: ${movie.releaseDate}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Rating: ${movie.ratings_score}/10",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Genre: ${movie.genre}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Length: ${movie.length} minutes",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Actors: ${movie.actors.toString().trim('[', ']').replace("\"", "")}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = movie.synopsis,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Comments section header
        item {
            Text(
                text = "Comments",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        itemsIndexed(sortedComments) { sortedIndex, comment ->
            // Pass the original index to the navigation screen
            val originalIndex = movie.comment.indexOf(comment) // Find the original index
            CommentItem(comment = comment, index = originalIndex, movieTitle = movieTitle, navController = navController)
        }
    }
}

fun getTimeAgo(commentDate: String, commentTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Combine date and time to create a LocalDateTime
    val commentDateTime = LocalDateTime.parse("$commentDate $commentTime", formatter)

    // Get current time
    val currentDateTime = LocalDateTime.now()

    // Calculate the duration between current time and comment time
    val duration = Duration.between(commentDateTime, currentDateTime)

    // Return a formatted string based on the duration
    return when {
        duration.toDays() > 0 -> "${duration.toDays()} days ago"
        duration.toHours() > 0 -> "${duration.toHours()} hrs ago"
        duration.toMinutes() > 0 -> "${duration.toMinutes()} mins ago"
        else -> "Just now"
    }
}

@Composable
fun CommentItem(comment: Comments, index: Int, movieTitle: String, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // Navigate to the View Comments screen
                navController.navigate("ViewComment/$index?movieTitle=$movieTitle")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Profile Picture
            UserProfilePicture(userName = comment.user)

            Spacer(modifier = Modifier.width(8.dp))

            // Text Section
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Username
                    Text(
                        text = comment.user,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between username and date/time

                    // Use getTimeAgo to calculate the time duration
                    val timeAgo = getTimeAgo(comment.date, comment.time)

                    // Date and Time (now showing the time ago)
                    Text(
                        text = timeAgo,
                        fontSize = 12.sp,
                        color = Color.Gray // Optional styling
                    )
                }

                // Comment text
                Text(
                    text = comment.comment,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


@Composable
fun UserProfilePicture(userName: String, size: Int = 50) {
    // Function to extract initials from a CamelCase username
    fun getInitials(userName: String): String {
        // Match uppercase letters and digits, and split the string accordingly
        val regex = "(?=[A-Z])".toRegex()  // Matches the start of each uppercase letter or a number
        val words = regex.split(userName).filter { it.isNotEmpty() }

        // Join the first letter of each word
        val initials = words.joinToString(".") {
            it.firstOrNull()?.uppercase().toString()
        }

        return initials
    }

    // Get the initials based on the username
    val initials = getInitials(userName)

    Box(
        modifier = Modifier
            .size(size.dp) // Set the size of the circle
            .background(
                color = androidx.compose.ui.graphics.Color.Gray,
                shape = androidx.compose.foundation.shape.CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = androidx.compose.ui.graphics.Color.White,
            fontSize = (size / 2.5).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun UserProfileWithName(userName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        UserProfilePicture(userName = userName)
        Text(
            text = userName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun MovieDetailScreenPreview() {
    UserProfileWithName(userName = "Jason")

    val sampleComments = mutableListOf(
        Comments(user = "User1", comment = "Great movie!", date = "2024-11-01", time = "10:00 AM"),
        Comments(user = "User2", comment = "Not bad", date = "2024-11-02", time = "12:30 PM")
    )

    val sampleMovie = MovieItem(
        title = "Sample Movie",
        director = "Director Name",
        releaseDate = "2024-11-01",
        ratings_score = 8.5f,
        actors = listOf("Actor 1", "Actor 2"),
        image = "image_url",
        genre = "Thriller",
        length = 120,
        synopsis = "This is a sample synopsis.",
        comment = sampleComments
    )
    val movieRaterApp = MovieRaterApplication()
    movieRaterApp.data.add(sampleMovie)

    MovieDetailScreen(movieIndex = 0, movieTitle = sampleMovie.title, navController = rememberNavController())
}