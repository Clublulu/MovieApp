# The Movie App

The Movie App is a movie application displaying the latest movie releases with the help of The Movie Database API.
It utilizes a combination of the Model-View-ViewModel (MVVM) design pattern, 
popular third party libraries like Retrofit, Picasso, and RxJava, 
along with components from Android Jetpack to compose a clean and robust system architecture.

## Dependencies

The Movie App uses the following dependencies:

* Room - A persistence library that helps create a local database cache of the application's data.
* LiveData - A life-cycle aware, data holder object that observes for data changes to update the UI.
* ViewModel - Stores and manages UI-related data in a life-cycle conscious way, allowing data to survive during 
 configuration changes like a screen rotation.
* Retrofit2 - A type-safe REST client making it easy to fetch & upload JSON (or any other structure data) via REST based web services.
* Retrofit2 RxJava Adapter - A type of Retrofit Call Adapter for extending support to RxJava service method return types.
* RxJava - Third party library allowing for event-based programming with the help of Observables and Subscribers.
* RxJava Android - Provides additional utility classes for the Android platform.
* Gson - Java based library used to help serialize / deserialize JSON objects.
* Gson Converter - Utility library that creates a Converter Factory for Retrofit to serialize / deserialize Gson objects.
* RecyclerView - A View displaying scrollable list of data sets, utilizing a combination of the ViewHolder & Adapter patterns to adjust for data changes.
* Picasso - Library used for manipulating and caching images.
* Material Design Components - Building blocks for designing UI's that provide a user rich experience.
* Youtube Android Player API - Third party library enabling video playback functionality, exposing methods to load / cue Youtube videos with ease.
* ExpandableTextView API - Enables TextViews to easily expand / collapse, showing more text.
* Paging Library - Allows for displaying small chunks of data at a time, reducing network bandwidth and system resource usage.

## Installation

Below are the prerequisites and steps to be taken in order to get the app up and running:

1. Android Studio 4.0 and Gradle version 4.0.1 installed.
2. The Movie Database API contains authentication requiring a valid API key in order to consume their data.
   - Create an Account with The Movie Database
   - Log into your account and request an API Key by clicking the "API" link in the left hand side bar of the account page.
  
3. Find class MoviesDataSource and modify the field MOVIE_DB_API_KEY with your movie database API key.
4. Rebuild project and press Play!
