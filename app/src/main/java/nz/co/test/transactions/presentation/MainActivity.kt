package nz.co.test.transactions.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.R
import nz.co.test.transactions.presentation.theme.TransactionAppTheme
import nz.co.test.transactions.presentation.view.TransactionDetailsView
import nz.co.test.transactions.presentation.view.TransactionsListView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: TransactionsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Let the app draw behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Hide system bars (status and navigation)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())

        // Enable immersive mode (bars can be shown with swipe)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        val transactionsListRoute = "transactionsList"
        val transactionDetailsRoute = "transactionDetails"

        setContent {
            val navController = rememberNavController()
            val uiState by viewModel.state.collectAsStateWithLifecycle()

            TransactionAppTheme{
                Scaffold(
                    topBar = {
                        val currentRoute =
                            navController.currentBackStackEntryAsState().value?.destination?.route

                        TopAppBar(
                            title = {
                                when (currentRoute) {
                                    transactionsListRoute -> {
                                        Text(stringResource(R.string.top_app_bar_title_transactions))
                                    }

                                    transactionDetailsRoute -> {
                                        Text(stringResource(R.string.top_app_bar_title_transaction_details))
                                    }
                                }
                            },
                            navigationIcon = {
                                if (currentRoute == transactionDetailsRoute) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back Button"
                                        )
                                    }
                                }
                            },
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = transactionsListRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(transactionsListRoute) {
                            TransactionsListView(
                                transactionsList = uiState.transactionsList,
                                savedScrollPosition = uiState.savedScrollPosition,
                                onTransactionTap = {
                                    viewModel.onTransactionSelected(it)
                                    navController.navigate(transactionDetailsRoute)
                                },
                                onScrollPositionChanged = viewModel::saveScrollPosition
                            )
                        }
                        composable(transactionDetailsRoute) {
                            uiState.selectedTransaction?.let { transaction ->
                                TransactionDetailsView(transaction)
                            }
                        }
                    }
                }
            }
        }
    }
}