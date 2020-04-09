# GithubSearch

Native Android github repo search app written in Kotlin, this was a weekend project where I wanted to test some of the
Jetpack releases

### Architecture

The overall architecture heavily borrows from Uncle Bob’s Clean Architecture, modules separated by “layer”.

Presentation module consists of the MVVM design pattern

Doman module is a pure Kotlin module which utilizes Kotlin coroutines for asynchronous flow

Data module consists of the standard android http package ( Retrofit + Okhttp )

# USAGE

Enter something inside the SearchView to initiate the search.

Click on the repository item to navigate to repository details,

Either click inside repo list view on the thumbnail or iniside the repository details screen to navigate to user details

Click on the "people" toolbar icon to navigate to the bare-bones login screen where you can login and see your private user data 
by clicking the "GET PRIVATE USER DATA"

### DISCLAIMER 
Clicking the "DELETE SAVED TOKEN" only erases the token from the app, it DOESN'T revoke the access token inside
the OAuth app, if you want to do that please revoke it by going into your Settings -> Applications -> Authorized Ouath Apps and revoke it there

# TESTS

Since this was just a fun project, the app isn't fully tested, I only put 1 unit test per layer so that the general
idea how to test this implemntation is out there :)

- Presentation module test: UserDetailViewModelTest
- Domain module test: PrepareUserDataTest
- Data module test: RemoteGithubRepositoryImplTest

# Libs used: 

- Jetpack Navigation
- Coroutines
- Dagger 
 ( altho a pretty poor setup, in a real world applications you'd scope things better and add more sub-components )
- Mockk
- Junit5
- LiveData
- Okhttp
- Retrofit
- ViewBinding
- Jetpack Security

# Screenshots

### NOTE 
I really didn't have that much emphasis on the whole design process, the design is pretty bland and there are no fancy
customViews, fancy MotionLayout transitions...

### Empty Repo Screen

<img width="350" alt="creation_page" src="/screenshots/empty_repo_screen.JPEG">

### Profile Screen

<img width="350" alt="creation_page" src="/screenshots/profile_screen.JPEG">

### Repo list Screen

<img width="350" alt="creation_page" src="/screenshots/repo_list.JPEG">

### Repo details Screen

<img width="350" alt="creation_page" src="/screenshots/repository_details.JPEG">

### Sort dialog

<img width="350" alt="creation_page" src="/screenshots/sort_dialog.JPEG">

### User details Screen

<img width="350" alt="creation_page" src="/screenshots/user_details.JPEG">
