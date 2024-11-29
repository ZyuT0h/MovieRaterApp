package com.it2161.dit234453p.assignment1

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.it2161.dit234453p.assignment1.ui.components.AddCommentScreen
import com.it2161.dit234453p.assignment1.ui.components.CommentMovieScreen
import com.it2161.dit234453p.assignment1.ui.components.EditProfileScreen
import com.it2161.dit234453p.assignment1.ui.components.LandingScreen
import com.it2161.dit234453p.assignment1.ui.components.LoginScreen
import com.it2161.dit234453p.assignment1.ui.components.MovieDetailScreen
import com.it2161.dit234453p.assignment1.ui.components.ProfileScreen
import com.it2161.dit234453p.assignment1.ui.components.RegisterUserScreen


enum class MovieViewerScreen {
    Login,
    Register,
    Landing,
    ViewProfile,
    ProfileEdit,
    MovieDetail,
    AddComment,
    ViewComment
}

fun getScreenTitle(screen: MovieViewerScreen, movieTitle: String? = null): String {
    return when (screen) {
        MovieViewerScreen.MovieDetail -> movieTitle ?: "Movie Detail"
        MovieViewerScreen.AddComment,
        MovieViewerScreen.ViewComment -> movieTitle ?: "Comments"
        MovieViewerScreen.Login -> "Login"
        MovieViewerScreen.Register -> "Register"
        MovieViewerScreen.Landing -> "PopCornMovie"
        MovieViewerScreen.ViewProfile -> "View Profile"
        MovieViewerScreen.ProfileEdit -> "Edit Profile"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieViewerApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MovieViewerScreen.valueOf(
        backStackEntry?.destination?.route?.substringBefore("/") ?: MovieViewerScreen.Login.name
    )
    val movieTitle = backStackEntry?.arguments?.getString("movieTitle") ?: "Unknown"
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentScreen != MovieViewerScreen.Login && currentScreen != MovieViewerScreen.Register) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = getScreenTitle(currentScreen, movieTitle),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        if (currentScreen != MovieViewerScreen.Landing) {
                            IconButton(onClick = {
                                if (currentScreen == MovieViewerScreen.ViewProfile) {
                                    navController.navigate(MovieViewerScreen.Landing.name) {
                                        popUpTo(MovieViewerScreen.Landing.name) { inclusive = true }
                                    }
                                } else {
                                    navController.popBackStack()
                                }
                            }) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        if (currentScreen == MovieViewerScreen.MovieDetail) {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More Actions")
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Add Comment") },
                                    onClick = {
                                        val movieIndex = backStackEntry?.arguments?.getInt("movieIndex") ?: 0
                                        navController.navigate("AddComment/$movieIndex?movieTitle=$movieTitle")
                                        expanded = false
                                    }
                                )
                            }
                        } else {
                            TopBarActions(currentScreen = currentScreen, navController = navController)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieViewerScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MovieViewerScreen.Login.name) {
                LoginScreen(
                    onRegisterButtonClicked = { navController.navigate(MovieViewerScreen.Register.name) },
                    onLoginSuccess = { navController.navigate(MovieViewerScreen.Landing.name) }
                )
            }
            composable(MovieViewerScreen.Register.name) {
                RegisterUserScreen(
                    options = listOf("Male", "Female", "Non-Binary", "Prefer not to say"),
                    onCancelButtonClicked = { navController.popBackStack() },
                    onRegisterButtonClicked = {
                        navController.navigate(MovieViewerScreen.Login.name) {
                            popUpTo(MovieViewerScreen.Login.name) { inclusive = true }
                        }
                    }
                )
            }
            composable(MovieViewerScreen.Landing.name) {
                LandingScreen(navController = navController)
            }
            composable(
                route = "MovieDetail/{movieIndex}/{movieTitle}",
                arguments = listOf(
                    navArgument("movieIndex") { type = NavType.IntType },
                    navArgument("movieTitle") { type = NavType.StringType }
                )
            ) {
                val movieIndex = it.arguments?.getInt("movieIndex") ?: 0
                val movieTitle = it.arguments?.getString("movieTitle") ?: "Movie Detail"
                MovieDetailScreen(movieIndex = movieIndex, movieTitle = movieTitle, navController = navController)
            }
            composable(
                route = "ViewComment/{commentIndex}?movieTitle={movieTitle}",
                arguments = listOf(
                    navArgument("commentIndex") { type = NavType.IntType },
                    navArgument("movieTitle") { type = NavType.StringType }
                )
            ) {
                val commentIndex = it.arguments?.getInt("commentIndex") ?: 0
                val movieTitle = it.arguments?.getString("movieTitle") ?: ""
                CommentMovieScreen(commentIndex = commentIndex, movieTitle = movieTitle, navController = navController)
            }
            composable(
                route = "AddComment/{movieIndex}?movieTitle={movieTitle}",
                arguments = listOf(
                    navArgument("movieIndex") { type = NavType.IntType },
                    navArgument("movieTitle") { type = NavType.StringType; nullable = true }
                )
            ) {
                val movieIndex = it.arguments?.getInt("movieIndex") ?: 0
                val movieTitle = it.arguments?.getString("movieTitle")
                AddCommentScreen(movieIndex = movieIndex, movieTitle = movieTitle, navController = navController)
            }
            composable(MovieViewerScreen.ViewProfile.name) {
                ProfileScreen()
            }
            composable(MovieViewerScreen.ProfileEdit.name) {
                EditProfileScreen(
                    options = listOf("Male", "Female", "Non-Binary", "Prefer not to say"),
                    onUpdateButtonClicked = {
                        navController.navigate(MovieViewerScreen.ViewProfile.name)
                    }
                )
            }
        }
    }
}

@Composable
fun TopBarActions(currentScreen: MovieViewerScreen, navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    when (currentScreen) {
        MovieViewerScreen.Landing -> {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More Actions")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("View Profile") },
                    onClick = {
                        navController.navigate(MovieViewerScreen.ViewProfile.name)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        navController.navigate(MovieViewerScreen.Login.name) {
                            popUpTo(MovieViewerScreen.Landing.name) { inclusive = true }
                        }
                        expanded = false
                    }
                )
            }
        }
        MovieViewerScreen.ViewProfile -> {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More Actions")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Edit Profile") },
                    onClick = {
                        navController.navigate(MovieViewerScreen.ProfileEdit.name)
                        expanded = false
                    }
                )
            }
        }
        else -> {}
    }
}