package com.coderwise.core.sample.ui.list

import com.coderwise.core.auth.ui.login.LoginRoute
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.sample.data.sample.Sample
import com.coderwise.core.sample.data.sample.SampleRepository
import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.sample.ui.list.edit.EditRoute
import com.coderwise.core.sample.ui.profile.ProfileRoute
import com.coderwise.core.time.TimeService
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.arch.UiNotification
import com.coderwise.core.ui.arch.routeId

class ListViewModel(
    private val navRouter: NavigationRouter,
    private val uiMessenger: UiMessenger,
    private val sampleRepository: SampleRepository,
    private val timeService: TimeService,
    private val sessionRepository: SessionRepository
) : BaseViewModel<ListModelState, ListUiState>(
    initialState = ListModelState(),
    mapper = { it.asUiState() }
) {
    init {
        asyncAction {
            sessionRepository.session.collect {
                reduce { copy(isAuthenticated = it.authToken != null) }
            }
        }
        asyncAction {
            sampleRepository.flow.collect { outcome ->
                outcome.onSuccess { samples ->
                    reduce { copy(samples = samples) }
                }
            }
        }
        asyncAction {
            sampleRepository.fetchAll().onError {
                uiMessenger.showNotification(UiNotification(it.message!!))
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is ListAction.OnItemClicked -> asyncAction {
                navRouter.navigate(EditRoute(action.id))
            }

            is ListAction.OnAddButtonClicked -> asyncAction {
                sampleRepository.update(
                    Sample(
                        id = timeService.now().toEpochMilliseconds().toInt(),
                        value = "New sample"
                    )
                ).onError { uiMessenger.showNotification(UiNotification(it.message!!)) }
            }

            is ListAction.OnAccountClicked -> asyncAction { state ->
                navRouter.navigate(
                    if (!state.isAuthenticated)
                        LoginRoute(ListRoute.routeId())
                    else
                        ProfileRoute
                )
            }
        }
    }
}

private fun ListModelState.asUiState() = ListUiState(
    items = samples?.map { ListUiState.Item(it.id, it.value) } ?: emptyList()
)
