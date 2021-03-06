# MovieDB Example App 🍿 

For this exercise I decided to use the Single Activity principle, additionally Clean Architecture as a pattern through modules, MVVM, Jetapck (view bindings, nav component, list adapter) so in the project structure it looks as follows:
    - App: Everything related to the graphical interface such as Fragments, Activity, ViewModels, Adapters, ViewHolders
    - Domain: Everything related to business logic, data conversions and connection with the data source
    - Framework: Practically a domain extension, necessary for the use of Paging and its dependency with Android APIs
    - Data: Data sources, endpoints

## Important Notes 

* About the caching requirement for images: I delegated that behavior directly to Glide, so no implementation was necessary.
* It was also not necessary to create a custom RatingView component as the similar Material component is now stable.

## Gradle

Dependencies are centralized inside the [dependencies.gradle](buildsystem) file in the `buildsystem` folder. This provides convenient centralization. It will allow us to update any dependency in a single place without the necessity to go manually to each gradle file where is used to change it, avoiding possible conflicts between versions if someone forgets to update one of these dependencies.

## Clean Architecture

The objective of [**Clean Architecture**](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) is the separation of concerns. Using a layers architecture like this one, allow us to be more independent of frameworks and limit proper boundaries. It also provide more benefits as higher testability and the possibility to scale well.

The project will start growing keeping in mind this, for now we don't have many modules but instead we'll handle the layer by using packages referencing them as such.

## Third Party libraries used
- Glide: Image loading and caching
- Retrofit: HTTP Client
- Retrofit Gson Converter: GSON converter for JSON responses
- MockitoKotlin: Mockito for Kotlin unit testing
- Hamcrest: Matchers for unit testing, make it more readable and efficient

## Screenshots

| Movie Detail      | Home |
| ----------- | ----------- |
| ![Screenshot_1624937436](https://user-images.githubusercontent.com/7938140/123827058-ebbb2700-d8c5-11eb-9720-34a1d40955e5.png) | ![Screenshot_1624937429](https://user-images.githubusercontent.com/7938140/123827151-fc6b9d00-d8c5-11eb-96a5-5ef18715f808.png) |

