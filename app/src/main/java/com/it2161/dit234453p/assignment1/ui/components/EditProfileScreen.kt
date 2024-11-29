package com.it2161.dit234453p.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.it2161.dit234453p.assignment1.MovieRaterApplication
import com.it2161.dit234453p.assignment1.R
import com.it2161.dit234453p.assignment1.data.UserProfile
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onUpdateButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    val movieRaterApp = LocalContext.current.applicationContext as MovieRaterApplication

    // Read user data from file (loaded through MovieRaterApplication)
    var selectedAvatar by remember { mutableStateOf(movieRaterApp.userProfile?.avatar ?: R.drawable.avatar_2) }
    var username by remember { mutableStateOf(movieRaterApp.userProfile?.userName ?: "") }
    var password by rememberSaveable { mutableStateOf(movieRaterApp.userProfile?.password ?: "") }
    var confirmPassword by rememberSaveable { mutableStateOf(password) }
    var email by remember { mutableStateOf(movieRaterApp.userProfile?.email ?: "") }
    var gender by rememberSaveable { mutableStateOf(movieRaterApp.userProfile?.gender ?: "") }
    var mobileNumber by remember { mutableStateOf(movieRaterApp.userProfile?.mobile ?: "") }
    var unchecked by remember { mutableStateOf(movieRaterApp.userProfile?.updates ?: false) }
    var selectedYear by rememberSaveable {
        mutableStateOf(movieRaterApp.userProfile?.yob ?: "Select Year of Birth")
    }
    var expanded by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (1920..currentYear).toList()

    val avatars = listOf(
        R.drawable.avatar_1,
        R.drawable.avatar_2,
        R.drawable.avatar_3
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Use background color
            .padding(16.dp) // Increased padding for spacing
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Choose your avatar", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            avatars.forEach { avatar ->
                Image(
                    painter = painterResource(id = avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp) // Larger avatar size for better visibility
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedAvatar = avatar }
                        .border(2.dp, if (selectedAvatar == avatar) MaterialTheme.colorScheme.primary else Color.Transparent, RoundedCornerShape(20.dp))
                )
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        // Form fields
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("User name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.9f) // Ensure proper width
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally), // Center horizontally
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally), // Center horizontally
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally), // Center horizontally
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally), // Center horizontally
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { mobileNumber = it },
            label = { Text("Mobile number") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally), // Center horizontally
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            )
        )

        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedYear,
                onValueChange = { },
                readOnly = true,
                label = { Text("Year of Birth") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        onClick = {
                            selectedYear = year.toString()
                            expanded = false
                        },
                        text = { Text(year.toString()) }
                    )
                }
            }
        }

        // Gender selection
        Text("Gender", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            options.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == item,
                        onClick = {
                            gender = item
                            onSelectionChanged(item)
                        }
                    )
                    Text(item, modifier = Modifier.padding(start = 6.dp))
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = unchecked,
                onCheckedChange = { unchecked = it }
            )
            Text("Receive updates")
        }

        // Update Profile Button
        Button(
            onClick = {
                movieRaterApp.userProfile = UserProfile(
                    userName = username,
                    password = password,
                    email = email,
                    gender = gender,
                    mobile = mobileNumber,
                    avatar = selectedAvatar,
                    yob = selectedYear,
                    updates = unchecked,
                )
                onUpdateButtonClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp) // Rounded corners for button
        ) {
            Text("Save")
        }
    }
}


@Preview
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(
        options = listOf("Male", "Female","Non-Binary","Prefer not to say"),
        onUpdateButtonClicked = { /* Do nothing */ }
    )
}