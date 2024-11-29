package com.it2161.dit234453p.assignment1.ui.components


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
fun RegisterUserScreen(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onRegisterButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    val movieRaterApp = context.applicationContext as MovieRaterApplication
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var unchecked by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedYear by rememberSaveable { mutableStateOf("Select Year of Birth") }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (1920..currentYear).toList()

    // For new users, randomly assign an avatar
    val defaultAvatar = R.drawable.avatar_2 // Randomly assign an avatar
    val selectedAvatar by rememberSaveable { mutableIntStateOf(defaultAvatar) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Form fields
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("User name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Ensure proper width
                    .padding(bottom = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = { Text("Mobile number") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 4.dp)
                    .align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
            )

            // Year of Birth dropdown
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
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
            Text(
                "Gender",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            options.forEach { item ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = gender == item,
                            onClick = {
                                gender = item
                                onSelectionChanged(item)
                            }
                        )
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == item,
                        onClick = {
                            gender = item
                            onSelectionChanged(item)
                        },
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(item)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = unchecked,
                    onCheckedChange = { unchecked = it }
                )
                Text("Receive updates")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onCancelButtonClicked,
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        // Validation logic with Toast messages
                        if (username.isBlank()) {
                            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (password.isBlank()) {
                            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (confirmPassword.isBlank()) {
                            Toast.makeText(
                                context,
                                "Confirm password cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        } else if (password != confirmPassword) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (email.isBlank()) {
                            Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (gender.isBlank()) {
                            Toast.makeText(context, "Please select your gender", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (mobileNumber.isBlank()) {
                            Toast.makeText(
                                context,
                                "Mobile number cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        } else if (!mobileNumber.matches(Regex("^\\d{8}\$"))) {
                            Toast.makeText(context, "Invalid mobile number", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (selectedYear == "Select Year of Birth") {
                            Toast.makeText(
                                context,
                                "Please select your year of birth",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        // If all validations pass, proceed with registration
                        val userProfile = UserProfile(
                            userName = username,
                            password = password,
                            email = email,
                            gender = gender,
                            mobile = mobileNumber,
                            updates = unchecked,
                            yob = selectedYear,
                            avatar = selectedAvatar
                        )

                        movieRaterApp.userProfile = userProfile

                        Toast.makeText(context, "User registered successfully!", Toast.LENGTH_SHORT)
                            .show()
                        onRegisterButtonClicked()
                    },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Register")
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterUserScreenPreview() {
    RegisterUserScreen(
        options = listOf("Male", "Female","Non-Binary","Prefer not to say"),
        onRegisterButtonClicked = { /* Do nothing */ }
    )
}