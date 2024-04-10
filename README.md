# MindVocab

MindVocab is an application for learning, memorizing, and tracking statistics of English words.

## Technology Stack

MindVocab is developed using modern technologies and approaches:

- **Programming Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture Pattern**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Asynchronous Programming**: [Kotlin Flow](https://kotlinlang.org/docs/flow.html), [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Database**: [SQLite](https://www.sqlite.org/index.html) (with [Room](https://developer.android.com/training/data-storage/room))
- ~~**Networking**: [Retrofit](https://square.github.io/retrofit/)~~
- **Navigation**: Navigation component
- **View Layer**: XML (for layout design) with [Material Design Components](https://material.io/develop/android/docs/getting-started)
- **Localization**: Android resource system for localization

## Screenshots

### Authentication

<p float="left">
  <img alt="Splash" src="images/splash.png" width="250">
  <img alt="Sign in learning" src="images/sign_in.png" width="250">
  <img alt="Sign in learning" src="images/sign_up.png" width="250">
</p>

### Learning

<p float="left">
  <img alt="Learning" src="images/Learning.png" width="250">
  <img alt="Learning daily" src="images/learning_words_daily.png" width="250">
</p>

<p float="left">
  <img alt="Learning pending" src="images/learning_pending.png" width="250">
  <img alt="Learning daily" src="images/learning_words_daily.png" width="250">
  <img alt="Learning empty" src="images/learning_empty.png" width="250">
</p>

### Word sets

<p float="left">
  <img alt="Word sets" src="images/word_sets.png" width="250">
  <img alt="Word sets search" src="images/word_sets_search.png" width="250">
  <img alt="Word sets words" src="images/word_sets_words.png" width="250">
</p>

### Repeating

<p float="left">
  <img alt="Repeating" src="images/Repeating.png" width="250">
  <img alt="Repeating input" src="images/repeating_input.png" width="250">
</p>

<p float="left">
  <img alt="Repeating pending" src="images/repeating_pending.png" width="250">
  <img alt="Repeating words" src="images/repeating_words.png" width="250">
</p>

### Statistic

<p float="left">
  <img alt="Statistic main" src="images/statistic_main.png" width="250">
  <img alt="Statistic data" src="images/statistic_data.png" width="250">
</p>

<p float="left">
  <img alt="Statistic calendar" src="images/statistic_calendar.png" width="250">
  <img alt="Statistic calendar helping dialog" src="images/statistic_calendar_help_dialog.png" width="250">
  <img alt="Statistic calendar day" src="images/statistic_calendar_day.png" width="250">
</p>

### Achievements

<p float="left">
  <img alt="Achievements" src="images/Achievements.png" width="250">
  <img alt="Achievement detail" src="images/achievement_detail.png" width="250">
</p>

### Settings

<p float="left">
  <img alt="Settings" src="images/Settings.png" width="250">
  <img alt="Settings detail" src="images/settings_language.png" width="250">
  <img alt="Settings account edit" src="images/settings_account_edit.png" width="250">
</p>

### Light theme

<p float="left">
  <img alt="Settings" src="images/light_learning.png" width="250">
  <img alt="Settings detail" src="images/light_achievements.png" width="250">
  <img alt="Settings account edit" src="images/light_settings.png" width="250">
</p>

## Features

#### Word Categories
- Users can browse word categories for learning and choose which ones to study.

#### Multilingual Learning
- Ability to study words with translations for multiple languages.

#### Learning with Visuals
- Study words with images, explanations in English, and examples.

#### Flashcard Learning
- Utilize flashcards for studying words and track progress for each word.

#### Word Review
- Option to review words that were incorrectly selected for further learning.

#### Statistics and Achievements
- View statistics for all words and categories, including other relevant metrics.
- Achievements system to motivate users throughout their learning journey.

#### Motivation with Notifications
- Receive motivational notifications to encourage consistent learning.

#### Customizable Learning Experience
- Customize learning preferences, including the number of words to study per day, word selection system, and various word memorization techniques.

#### Offline Access and Progress Saving
- Access words offline and save learning progress locally on the device.

## Database Schema

![Database Schema](images/DB.drawio.png)