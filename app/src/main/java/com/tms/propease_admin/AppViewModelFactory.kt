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
import com.tms.propease_admin.ui.screen.accountManagement.ProfileScreenViewModel
import com.tms.propease_admin.ui.screen.accountManagement.RegistrationScreenViewModel
import com.tms.propease_admin.ui.screen.property.PropertyDetailsScreenViewModel
import com.tms.propease_admin.ui.screen.property.UnverifiedPropertiesScreenViewModel
import com.tms.propease_admin.ui.screen.property.VerifiedLivePropertiesScreenViewModel
import com.tms.propease_admin.ui.screen.property.VerifiedNotLivePropertiesScreenViewModel
import com.tms.propease_admin.ui.screen.property.category.CategoriesScreenViewModel
import com.tms.propease_admin.ui.screen.user.UnverifiedUsersScreenViewModel
import com.tms.propease_admin.ui.screen.user.UserDetailsScreenViewModel

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

        // initialize VerifiedNotLivePropertiesScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            VerifiedNotLivePropertiesScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize UnverifiedUsersScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            UnverifiedUsersScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize VerifiedLivePropertiesScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            VerifiedLivePropertiesScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize UserDetailsScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            val savedStateHandle = this.createSavedStateHandle()
            UserDetailsScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                savedStateHandle = savedStateHandle
            )
        }

        // initialize CategoriesScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            CategoriesScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize ProfileScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            val dsRepository = propEaseApplication().dsRepository
            ProfileScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }
    }
}

fun CreationExtras.propEaseApplication(): PropEaseAdminApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropEaseAdminApp)