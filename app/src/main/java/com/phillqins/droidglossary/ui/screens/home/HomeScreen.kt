package com.phillqins.droidglossary.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.phillqins.droidglossary.ui.navigation.AppScreens
import com.phillqins.droidglossary.ui.screens.common.LoadingDialog
import com.phillqins.droidglossary.utils.CollectOneTimeEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectOneTimeEvent(viewModel.event) {
        when(it){
            is HomeScreenEvent.ShowToast -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            HomeScreenEvent.NavigateToSignIn -> {
                navController.navigate(AppScreens.SignIn.route){
                    popUpTo(AppScreens.Home.route){
                        inclusive = true
                    }
                }
            }
        }
    }

    when{
        uiState.isLoading -> {
            LoadingDialog(message = uiState.loadingMessage)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Glossary") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onAction(HomeUIAction.SignOut)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Log Out",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        content = { padding ->
            HomeScreenContent(
                modifier = Modifier.padding(padding),
                uiState = uiState,
                onAction = {
                    viewModel.onAction(it)
                }
            )
        }
    )

}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    uiState: HomeUIState,
    onAction: (HomeUIAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Home Screen ${uiState.glossaryItems.size}",
            modifier = Modifier.padding(16.dp)
        )
    }
}