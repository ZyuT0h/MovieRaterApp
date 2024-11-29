package com.it2161.dit234453p.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.it2161.dit234453p.assignment1.MovieRaterApplication
import com.it2161.dit234453p.assignment1.R

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val movieRaterApp = context.applicationContext as MovieRaterApplication

    // Retrieve user information from MovieRaterApplication
    val userProfile = movieRaterApp.userProfile
    val avatarResource = userProfile?.avatar ?: R.drawable.avatar_2
    val userId = userProfile?.userName ?: "Unknown User"
    val userEmail = userProfile?.email ?: "No Email Provided"
    val userGender = userProfile?.gender ?: "Unknown Gender"
    val userMobile = userProfile?.mobile ?: "Unknown Mobile"
    val userYear = userProfile?.yob ?: "Unknown Year"
    val userUpdates = userProfile?.updates ?: false

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = avatarResource),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Profile Information",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ProfileInfoItem(label = "User ID", value = userId)
                ProfileInfoItem(label = "Email", value = userEmail)
                ProfileInfoItem(label = "Gender", value = userGender)
                ProfileInfoItem(label = "Mobile Number", value = userMobile)
                ProfileInfoItem(label = "Year of Birth", value = userYear)
                ProfileInfoItem(
                    label = "Receive Updates",
                    value = if (userUpdates) "Yes" else "No"
                )
            }
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.secondary
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Divider(
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}