package com.coderwise.core.sample.ui.list

import com.coderwise.core.auth.ui.login.LoginRoute
import com.coderwise.core.sample.data.Sample
import com.coderwise.core.sample.data.SampleRepository
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.time.TimeService
import com.coderwise.core.sample.ui.EditRoute
import com.coderwise.core.sample.ui.ListRoute
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.arch.UiNotification
import com.coderwise.core.ui.arch.routeId

class ListViewModel(
    private val navRouter: NavigationRouter,
    private val uiMessenger: UiMessenger,
    private val sampleRepository: SampleRepository,
    private val timeService: TimeService
) : BaseViewModel<ListModelState, ListUiState>(
    initialState = ListModelState(),
    mapper = { it.asUiState() }
) {
    init {
        asyncAction {
            sampleRepository.flow.collect { outcome ->
                outcome.onSuccess { samples ->
                    reduce { it.copy(samples = samples) }
                }
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is ListAction.OnItemClicked -> asyncAction {
                uiMessenger.showNotification(UiNotification("Item clicked: ${action.id}"))
                navRouter.navigate(EditRoute(action.id))
            }

            is ListAction.OnAddButtonClicked -> asyncAction {
                sampleRepository.update(
                    Sample(
                        id = timeService.now().toEpochMilliseconds().toInt(),
                        value = "New sample"
                    )
                )
            }

            is ListAction.OnAccountClicked -> asyncAction {
                navRouter.navigate(LoginRoute(ListRoute.routeId()))
            }
        }
    }
}

private fun ListModelState.asUiState() = ListUiState(
    items = samples?.map { ListUiState.Item(it.id, it.value) } ?: emptyList()
)
