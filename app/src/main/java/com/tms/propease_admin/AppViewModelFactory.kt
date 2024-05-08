package com.tms.propease_admin

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.ui.screen.accountManagement.RegistrationScreenViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {
        // initialize RegistrationScreenViewModel
        initializer {
            val apiRepository = propEaseApplication().container.apiRepository
            RegistrationScreenViewModel(
                apiRepository = apiRepository
            )
        }
    }
}

fun CreationExtras.propEaseApplication(): PropEaseAdminApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropEaseAdminApp)