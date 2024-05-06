package org.mifospay.notification.presenter

import androidx.lifecycle.ViewModel
import com.mifospay.core.model.domain.NotificationPayload
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.mifospay.core.data.base.UseCase
import org.mifospay.core.data.base.UseCaseHandler
import org.mifospay.core.data.domain.usecase.notification.FetchNotifications
import org.mifospay.data.local.LocalRepository
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val mUseCaseHandler: UseCaseHandler,
    private val mLocalRepository: LocalRepository,
    private val fetchNotificationsUseCase: FetchNotifications
) : ViewModel() {

    private val _notificationUiState: MutableStateFlow<NotificationUiState> =
        MutableStateFlow(NotificationUiState.Loading)
    val notificationUiState: StateFlow<NotificationUiState> = _notificationUiState

    fun fetchNotifications() {
        mUseCaseHandler.execute(fetchNotificationsUseCase,
            FetchNotifications.RequestValues(
                mLocalRepository.clientDetails.clientId
            ),
            object : UseCase.UseCaseCallback<FetchNotifications.ResponseValue> {
                override fun onSuccess(response: FetchNotifications.ResponseValue) {
                    _notificationUiState.value =
                        NotificationUiState.Success(response.notificationPayloadList.orEmpty())
                }

                override fun onError(message: String) {
                    _notificationUiState.value = NotificationUiState.Error(message)
                }
            })
    }
}

sealed interface NotificationUiState {
    data object Loading : NotificationUiState
    data object Empty : NotificationUiState
    data class Success(val notificationList: List<NotificationPayload>) : NotificationUiState
    data class Error(val message: String) : NotificationUiState
}