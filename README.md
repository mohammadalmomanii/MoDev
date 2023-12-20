
# MoDev

![GitHub license](https://img.shields.io/github/license/mohammadalmomanii/MoDev)
(https://github.com/mohammadalmomanii/MoDev)

## Overview

Your library, `MoDev`, is a collection of utility classes and components for Android development. It aims to simplify common tasks, enhance user experience, and provide reusable elements that can be easily integrated into Android applications.

## Installation

Add the following dependency to your app's build.gradle file:

**1-**` settings.gradle.kts `
```gradle
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			 maven { url = uri("https://jitpack.io") }
		}
}
```
**2-**` build.gradle.kts (module :app) `
```gradle
dependencies {
    implementation("com.github.mohammadalmomanii:MoDev:0.0.3")

}
```

**3-**` build.gradle.kts (module :app) `
```gradle
android {
    minSdk = 27
    dataBinding { enable = true }
}
```






## Features

### AppHelper Class

The `AppHelper` class is a versatile utility that offers a range of functions to streamline Android development. Some key features include:

- `getCurrentDate`: Retrieve the current date.
- `setTimeZone`: Set the time zone for the application.
- `getShift`: Obtain the current shift information.
- `openFile`: Open files seamlessly.
- `hideKeyboard`: Dismiss the keyboard gracefully.
- `showSnackbar`: Display Snackbars easily.
- `setAppModeNight`: Set the application mode to night.
- `getAppMode`: Retrieve the current application mode.
- `setAnimateVisibility`: Apply smooth visibility animations.
- ... (List other functions with a brief description)

### Custom Dialog Fragment

The custom dialog fragment provides a customizable and reusable dialog for various use cases in your application.

#### Usage 

 Make your fragment extend TopSheetDialogFragment like this

```java
 CustomDialogFragment.newInstance().startShow(getSupportFragmentManager())
                    .setImage(getDrawable(R.drawable.ic_logout)).setTitle(getString(R.string.confirm_logout))
                    .setDescription("")
                    .setBtnPositive(getString(R.string.confirm), false, new MainInterface() {
                        @Override
                        public void onCustomDialogItemClick() {
                            AppStorage.logout();
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            finish();
                        }
                    }).setBtnNeutral(getString(R.string.cancel), new MainInterface() {
                        @Override
                        public void onCustomDialogItemClick() {
                            CustomDialogFragment.dismissDialog();
                        }
                    });
```

![Screenshot 2023-12-20 121911](https://github.com/mohammadalmomanii/MoDev/assets/91605807/0a66c039-8487-42d0-827a-afe95c63b04f)
![Screenshot 2023-12-20 121719](https://github.com/mohammadalmomanii/MoDev/assets/91605807/629affd9-1da4-4086-a764-e178ef688e16)
![Screenshot 2023-12-20 121744](https://github.com/mohammadalmomanii/MoDev/assets/91605807/b2bf378f-6e33-4e83-a9c0-837bad7cca54)
![Screenshot 2023-12-20 121842](https://github.com/mohammadalmomanii/MoDev/assets/91605807/fbba14f3-b2e3-49c5-af70-6d4a178788db)


### Loading Fragment

The loading fragment simplifies the integration of loading indicators within your app, enhancing the user experience during asynchronous operations.

```java
 LoadingFragment.newInstance().show(fragmentActivity.getSupportFragmentManager(), "");
```
       

### Zoom ImageView

The Zoom ImageView component allows users to zoom in and out of images with ease, providing an interactive image viewing experience.

#### Usage 

```xml
<com.mohammadalmomani.modevlib.zoomImage.ZoomImageView
        android:id="@+id/iv_zoom_image"
        android:layout_width="@dimen/_150dp"
        android:layout_height="@dimen/_150dp"
        android:src="@drawable/ic_add_image_2"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:zoomImage_animateOnReset="true"
        app:zoomImage_autoCenter="true"
        app:zoomImage_autoResetMode="UNDER"
        app:zoomImage_maxScale="8"
        app:zoomImage_minScale="0.6"
        app:zoomImage_restrictBounds="false"
        app:zoomImage_translatable="true"
        app:zoomImage_zoomable="true" />
```

![RfPGy](https://github.com/mohammadalmomanii/MoDev/assets/91605807/1197be96-84f3-4ccd-9b88-d396e566c798)



### Top Sheet Dialog
 The top sheet dialog is a versatile component that slides down from the top of the screen, offering a range of interactive features for user 
 interactions.

#### Usage 

 Make your fragment extend TopSheetDialogFragment like this

```java
           
            public class Fragment extends TopSheetDialogFragment {
                                                .
                                                .
                                                .
                                                .
            }
                       --------------------------------------------------------

            Fragment.newInstance().show(getSupportFragmentManager(),TAG);


```

![Android+Top+Sheet+Animation](https://github.com/mohammadalmomanii/MoDev/assets/91605807/9cc74bfb-56d3-4f03-895b-5052a390f668)






