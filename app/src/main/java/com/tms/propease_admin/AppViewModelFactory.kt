package com.tms.propease_admin

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.ui.screen.HomeScreenViewModel
import com.tms.propease_admin.ui.screen.SplashScreenViewModel
import com.tms.propease_admin.ui.screen.accountManagement.LoginScreenViewModel
import com.tms.propease_admin.ui.screen.accountManagement.RegistrationScreenViewModel
import com.tms.propease_admin.ui.screen.property.PropertyDetailsScreenViewModel
import com.tms.propease_admin.ui.screen.property.UnverifiedPropertiesScreenViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {
        // initialize RegistrationScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            RegistrationScreenViewModel(
                apiRepository = apiRepository
            )
        }

        // initialize LoginScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            val savedStateHandle = this.createSavedStateHandle()
            LoginScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                savedStateHandle = savedStateHandle
            )
        }

        // HomeScreenViewModel
        initializer {
            val dsRepository = propEaseApplication().dsRepository
            val savedStateHandle = this.createSavedStateHandle()
            HomeScreenViewModel(
                dsRepository = dsRepository,
                savedStateHandle = savedStateHandle
            )
        }

        // initialize SplashScreenViewModel
        initializer {
            val dsRepository = propEaseApplication().dsRepository
            SplashScreenViewModel(
                dsRepository = dsRepository
            )
        }

        // initialize UnverifiedPropertiesScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            UnverifiedPropertiesScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize PropertyDetailsScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            PropertyDetailsScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }
}

fun CreationExtras.propEaseApplication(): PropEaseAdminApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropEaseAdminApp)