// app/src/main/java/com/cs407/cardfolio/MainActivity.kt
package com.cs407.cardfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs407.cardfolio.ui.theme.AppTheme
import com.cs407.cardfolio.ui.theme.CardfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardfolioTheme {
                val gradientTopColor = AppTheme.customColors.gradientTop
                val gradientBottomColor = AppTheme.customColors.gradientBottom

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(gradientTopColor, gradientBottomColor)
                            )
                        ),
                    color = Color.Transparent
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                        )
                        Cardfolio()
                    }
                }
            }
        }
    }
}

@Composable
fun Cardfolio() {
    var name by remember { mutableStateOf("") }
    var hobby by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // 2.5 AssistChip at the top-right of the card
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    AssistChip(
                        onClick = { isEditing = !isEditing },
                        label = {
                            Text(
                                if (isEditing)
                                    stringResource(R.string.chip_editing)
                                else
                                    stringResource(R.string.chip_locked)
                            )
                        },
                        leadingIcon = {
                            if (isEditing)
                                Icon(Icons.Default.Edit, contentDescription = null)
                            else
                                Icon(Icons.Default.Lock, contentDescription = null)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Profile header row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.download),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (name.isBlank()) stringResource(id = R.string.card_name) else name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = if (hobby.isBlank()) stringResource(id = R.string.card_hobby) else hobby,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)

                // Inputs
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(id = R.string.card_name_label)) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        readOnly = !isEditing,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = hobby,
                        onValueChange = { hobby = it },
                        label = { Text(stringResource(id = R.string.card_hobby_label)) },
                        leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                        readOnly = !isEditing,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = age,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() }) age = input
                        },
                        label = { Text(stringResource(id = R.string.card_age_label)) },
                        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        readOnly = !isEditing,
                        supportingText = {
                            if (isEditing) Text(stringResource(id = R.string.age_warning))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // 2.6 Buttons row aligned to end with spacing
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { isEditing = true },
                        enabled = !isEditing
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.button_edit))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            // lock only if all fields are filled (missing-field handling in Milestone 3)
                            if (name.isNotBlank() && hobby.isNotBlank() && age.isNotBlank()) {
                                isEditing = false
                            }
                        },
                        enabled = isEditing
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.button_show))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardfolioPreview() {
    CardfolioTheme {
        Cardfolio()
    }
}
