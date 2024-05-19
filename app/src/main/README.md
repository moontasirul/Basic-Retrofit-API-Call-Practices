# Basic-Retrofit-API-Call-Practices

# Project Description:
The My API App is an Android application that demonstrates how to use Retrofit and Jetpack Compose with MVVM to consume data from an API. The app fetches a list of products from a remote API and displays them in a list.

# Key Features:
Fetches a list of products from a remote API using Retrofit.
Displays the list of products in a Jetpack Compose UI.
Uses MVVM architecture to separate the UI logic from the data fetching logic.

# Technical Stack:
 - Kotlin
 - Jetpack Compose
 - Android Architecture Components
 - Retrofit

# Project Structure:
 - **Model:** Contains data classes for representing products and other data models.
 - **Network:** Contains classes for fetching data from the remote API using Retrofit.
 - **Repository:** Provides an interface for accessing data from the network.
 - **ViewModel:** Contains a view model for managing the UI state and handling user interactions.
 - **UI:** Contains composable functions for building the app's UI.

# Getting Started:
 - Clone the project repository.
 - Open the project in Android Studio.
 - Update the base URL in the RetrofitInstance object to point to the API you want to consume.
 - Build and run the app on an Android device or emulator.

# Usage:
The app will automatically fetch the list of products from the API and display them in a list.
You can interact with the list items to view more details about each product.

