# News-App
App that displays the most relevant news from your country

This application consumes the News API (https://newsapi.org/) and displays a paged list with the top relevant news from the country you select.

From each headline, you can access more details about that article and also view the full article embedded in the app.

In the main page there you can also access a settings page where the country can be changed.

The application uses the **MVVM** architecture and **PagedListAdapter** to handle the "infinite" scroll (getting more pages from the API). Also, for the API calls, **Retrofit** and **RxJava** are being used to model the responses and deal with the UI updates.
For dependency injection, the choice was **Koin**, because of its setup simplicity.
