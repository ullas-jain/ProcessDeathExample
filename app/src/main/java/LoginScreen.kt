import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ullas.processdeathexample.MainActivityViewModel

@Preview
@Composable
fun LoginScreen(
    viewModel: MainActivityViewModel = hiltViewModel()
) {
    Surface(modifier = Modifier.padding(top = 48.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                NameField(viewModel)
            }
            item {
                EmailField(viewModel)
            }
            item {
                SubmitButton()
            }
        }
    }
}

@Composable
private fun NameField(viewModel: MainActivityViewModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        TextField(
            value = viewModel.nameField,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Name") }
        )
    }
}

@Composable
private fun EmailField(viewModel: MainActivityViewModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .padding(top = 16.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        TextField(
            value = viewModel.emailField,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") }
        )
    }
}

@Composable
private fun SubmitButton() {
    val shape = CircleShape
    Text(
        text = "Submit",
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 32.dp)
            .background(MaterialTheme.colors.primary, shape)
            .padding(16.dp)
    )
}

