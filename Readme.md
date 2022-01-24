# Please find the blog post at - https://ullasjain.com/process-death-in-android/

Let's say your app uses text input for user's name and email on login screen. User enters their info on those fields, but they had to minimise your app and open another application before hitting the submit button. Now, two things can happen if the user comes back to your application. The input entered by the user on those fields,
1. Might still be present 😄
2. Might not be present 😭

The second flow is frustrating to the user. It is a bad user experience. The state wasn't persisted on the app. It could be due to,
1. Process death, Android system kills your application if it is required to free up the RAM.
2. User has set the "Background process limit"(in developer options) as "No background processes".

Let's look at how to address this problem in this post. We'll simulate the same on a simple login form.


## Process death

The system kills processes when it needs to free up RAM; the likelihood of the system killing a given process depends on the state of the process at the time. Process state, in turn, depends on the state of the activity running in the process. Below table shows the correlation among process state, activity state, and likelihood of the system’s killing the process.

| Likelihood of being killed	      | Process state | Activity state |
| ----------- | ----------- | ----------- |
| Least		  | Foreground (having or about to get focus)       |  Created/Started/Resumed       |
| More	      | Background (lost focus)                         | Paused                         |
| Most	      | Background (not visible)                        | Stopped/Destroyed              |


# Example
We'll look at a simple login form and simulate process death on the same. It has two fields, `name` and `email` and looks like below.

<p style="text-align:center;"> <img src="/assets/login_form.png" alt="drawing" width="350" height="700" /></p>


# Show me code
I have used `jetpack compose` to create a simple login form. It uses `hilt` to bind the dependencies. Showcasing the main parts of the app.

#### MainActivityViewModel
```kotlin
@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {
    var nameField by mutableStateOf("")
        private set

    var emailField by mutableStateOf("")
        private set

    fun onNameChange(updatedName: CharSequence) {
        nameField = updatedName.toString()
    }

    fun onEmailChange(updatedName: CharSequence) {
        emailField = updatedName.toString()
    }
}
```

## Simulating process death
Option 1: Run your app and enter your data in text fields. Minimise the app and click on `Terminate Application` in Android studio's `Logcat`.

<p style="text-align:center;"> <img src="/assets/system_process_death.png" alt="drawing" width="600" height="1200" /></p>

Option 2: Manually set the "Background process limit"(in developer options) as "No background processes"

<p style="text-align:center;"> <img src="/assets/manual_process_death.png" alt="drawing" width="350" height="700" /></p>

## The current state of the app

Here we'll simulate `Process death` by manually setting the "Background process limit"(in developer options) as "No background processes". Then upon entering user details on `name` and `email` fields, minimise the current app and open another app (say, playstore). As you may notice, after opening the current app back, the `state` of `name` and `email` fields are missing.

<p style="text-align:center;"> <img src="/assets/simulate_process_death.gif" alt="drawing" width="350" height="700" /></p>

## Save state to fix the process death
We'll use `ViewModel` objects's `onSaveInstanceState()` method to save the state. Now the `MainActivityViewModel` would look like
```kotlin
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var nameField by mutableStateOf(savedStateHandle.get("nameKey") ?: "")
        private set

    var emailField by mutableStateOf(savedStateHandle.get("emailKey") ?: "")
        private set

    fun onNameChange(updatedName: CharSequence) {
        nameField = updatedName.toString()
        savedStateHandle.set("nameKey", nameField)
    }

    fun onEmailChange(updatedName: CharSequence) {
        emailField = updatedName.toString()
        savedStateHandle.set("emailKey", emailField)
    }
}
```
As you can see in the below gif, even after we simulate `Process death` as before, the state of the application is retained. All thanks to `SavedStateHandle`.

<p style="text-align:center;"> <img src="/assets/fix_process_death.gif" alt="drawing" width="350" height="700" /></p>
