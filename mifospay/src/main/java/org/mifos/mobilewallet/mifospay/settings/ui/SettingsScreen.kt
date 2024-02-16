package org.mifos.mobilewallet.mifospay.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mifos.mobilewallet.mifospay.R
import org.mifos.mobilewallet.mifospay.designsystem.component.MifosItemCard
import org.mifos.mobilewallet.mifospay.designsystem.component.MifosTopBar
import org.mifos.mobilewallet.mifospay.designsystem.theme.mifosText
import org.mifos.mobilewallet.mifospay.designsystem.theme.styleSettingsButton
import org.mifos.mobilewallet.mifospay.ui.utility.DialogState
import org.mifos.mobilewallet.mifospay.ui.utility.DialogType

/**
 * @author pratyush
 * @since 12/02/2024
 */

@Composable
fun SettingsScreen(
    backPress: () -> Unit,
    disable: () -> Unit,
    logout: () -> Unit
) {
    var dialogState by remember { mutableStateOf(DialogState()) }

    DialogManager(
        dialogState = dialogState,
        onDismiss = { dialogState = DialogState(type = DialogType.NONE) }
    )

    Scaffold(
        topBar = {
            MifosTopBar(topBarTitle = R.string.settings, backPress = { backPress.invoke() })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            Row(modifier = Modifier.padding(20.dp)) {
                MifosItemCard(
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Text(
                        text = stringResource(id = R.string.notification_settings),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        style = TextStyle(fontSize = 17.sp, color = mifosText)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {}

            Row(modifier = Modifier.padding(8.dp)) {
                MifosItemCard(
                    onClick = {
                        dialogState = DialogState(
                            type = DialogType.DISABLE_ACCOUNT,
                            onConfirm = { disable.invoke() }
                        )
                    },
                    colors = CardDefaults.cardColors(Color.Black)
                ) {
                    Text(
                        text = stringResource(id = R.string.disable_account).uppercase(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        style = styleSettingsButton
                    )
                }
            }

            Row(modifier = Modifier.padding(8.dp)) {
                MifosItemCard(
                    onClick = {
                        dialogState = DialogState(
                            type = DialogType.LOGOUT,
                            onConfirm = { logout() }
                        )
                    },
                    colors = CardDefaults.cardColors(Color.Black)
                ) {
                    Text(
                        text = stringResource(id = R.string.log_out).uppercase(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        style = styleSettingsButton
                    )
                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen({}, {}, {})
}