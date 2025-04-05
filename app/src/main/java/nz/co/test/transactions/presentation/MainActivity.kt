package nz.co.test.transactions.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
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
                            modifier = Modifier.height(64.dp)
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
                                uiState.transactionsList,
                                onTransactionTap = {
                                    viewModel.onTransactionSelected(it)
                                    navController.navigate(transactionDetailsRoute)
                                },
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