package xyz.ksharma.tinkandroiddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.ksharma.tinkandroiddemo.secure.CryptoManager
import xyz.ksharma.tinkandroiddemo.ui.theme.TinkAndroidDemoTheme
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalEncodingApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinkAndroidDemoTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.padding(32.dp))

                    var plainText: String by remember { mutableStateOf("") }
                    var cipher: String by remember { mutableStateOf("") }
                    var displayData by remember {
                        mutableStateOf(DisplayData.Decrypted)
                    }

                    TextField(value = plainText,
                        placeholder = {
                            Text(text = "Plain Text")
                        },
                        onValueChange = { plainText = it }
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            cipher = Base64.encode(source = CryptoManager.encrypt(plainText))
                            displayData = DisplayData.Encrypted
                            println("cipher: $cipher")
                        }) {
                            Text(text = "Encrypt")
                        }

                        Button(onClick = {
                            plainText = CryptoManager.decrypt(Base64.decode(cipher))
                            displayData = DisplayData.Decrypted
                            println("plainText: $plainText")
                        }) {
                            Text(text = "Decrypt")
                        }
                    }

                    val displayText = when (displayData) {
                        DisplayData.Encrypted -> cipher
                        DisplayData.Decrypted -> plainText
                    }
                    Text(
                        text = "$displayData: $displayText",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

enum class DisplayData {
    Encrypted,
    Decrypted
}