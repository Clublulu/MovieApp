# The Movie App

The Movie App is a movie application displaying the latest movie releases with the help of The Movie Database API.
It utilizes a combination of the Model-View-ViewModel (MVVM) design pattern, 
popular third party libraries like Retrofit, Picasso, and RxJava, 
along with components from Android Jetpack to compose a clean and robust system architecture.

## Dependencies

The Movie App uses the following dependencies:

* Room - A persistence library that helps create a local database cache of the application's data.
* LiveData - A life-cycle aware, data holder object that observes for data changes to update the UI.
* ViewModel - Manages UI-related data in a life-cycle conscious way, allowing it to survive configuration changes.
* Retrofit2 - A type-safe REST client that fetches & uploads structured data like JSON via REST based web services.
* Retrofit2 RxJava Adapter - A type of Retrofit Call Adapter for extending support to RxJava service method return types.
* RxJava - Allows for event-based programming with the help of Observables and Subscribers.
* RxJava Android - Provides additional utility classes for the Android platform.
* Gson - Java based library used to help serialize / deserialize JSON objects.
* Gson Converter - Creates a Converter Factory for Retrofit to serialize / deserialize Gson objects.
* RecyclerView - Supports displaying lists of data, working with Adapter & View Holder patterns to account for data changes.
* Picasso - Library used for manipulating and caching images.
* Material Design Components - Building blocks for designing UI's that provide a user rich experience.
* Youtube Android Player API - Enables video playback functionality, exposing methods to easily load / cue Youtube videos.
* ExpandableTextView API - Enables TextViews to easily expand / collapse, showing more text.
* Paging Library - Allows for displaying small chunks of data at a time, reducing network bandwidth and system resource usage.

## Installation

Below are the prerequisites and steps to be taken in order to get the app up and running:

1. Android Studio 4.0 and Gradle version 4.0.1 or higher should be installed.
2. The Movie Database API contains authentication requiring a valid API key in order to consume their data.
   - Create an account with The Movie Database.
   - Log into your account and request an API Key by clicking the "API" link in the left hand side bar of the account page.
3. Find the MoviesDataSource class and modify the field MOVIE_DB_API_KEY with your movie database API key.
4. Rebuild project and press Play!
