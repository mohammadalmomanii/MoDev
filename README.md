
# MoDev

![GitHub license](https://img.shields.io/github/license/mohammadalmomanii/MoDev)
(https://github.com/mohammadalmomanii/MoDev)

## Overview

 `MoDev`, is a collection of utility classes and components for Android development. It aims to simplify common tasks, enhance user experience, and provide reusable elements that can be easily integrated into Android applications.

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
    implementation("com.github.mohammadalmomanii:MoDev:1.4.8")

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
                .setImage(getDrawable(R.drawable.ic_logout)).setTitle(getString(com.mohammadalmomani.modevlib.R.string.logout_confirmation))
                .setDescription("")
                .setBtnPositive(getString(com.mohammadalmomani.modevlib.R.string.confirm), new MainInterface() {
                    @Override
                    public void onItemClick() {
			...
			...
			...
                    }
                }).setBtnNeutral(getString(com.mohammadalmomani.modevlib.R.string.cancel), new MainInterface() {
                    @Override
                    public void onItemClick() {
			...
			...
			...
                 }
                });
```

![Screenshot 2023-12-20 121911](https://github.com/mohammadalmomanii/MoDev/assets/91605807/0a66c039-8487-42d0-827a-afe95c63b04f)
![Screenshot 2023-12-20 121719](https://github.com/mohammadalmomanii/MoDev/assets/91605807/629affd9-1da4-4086-a764-e178ef688e16)
![Screenshot 2023-12-20 121744](https://github.com/mohammadalmomanii/MoDev/assets/91605807/b2bf378f-6e33-4e83-a9c0-837bad7cca54)
![Screenshot 2023-12-20 121842](https://github.com/mohammadalmomanii/MoDev/assets/91605807/fbba14f3-b2e3-49c5-af70-6d4a178788db)


### Show Notification

This class use to show notification

#### Usage 

**1-** in your `manifest`->`AndroidManifest.xml` put this permission 
```manifest
  <manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        ..../>
   </manifest>
```
 **2-** in your `activity` OR `fragment` put these CODES 

```java

  private ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission()
            , bool -> {

                Toast.makeText(getApplicationContext(), bool ? "PERMISSION GRANTED" : "PERMISSION NOT GRANTED", Toast.LENGTH_SHORT).show();

            });

protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	if (!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
	                == PackageManager.PERMISSION_GRANTED))
	            launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
}
```
 **3-** create your notification 

```java

 NotificationHelper notificationHelper = new NotificationHelper(this);
.
.
.

 notificationHelper.createNotification().setContentTitle("NOTIFICATION")
                    .setSmallIcon(com.mohammadalmomani.modevlib.R.drawable.ic_close)
                    .setContentText("MY NOTIFICATION IS SHOWING");
            notificationHelper.showNotification(30);
```

![screen-20240314-121127](https://github.com/mohammadalmomanii/MoDev/assets/91605807/92abd3cf-7b0e-4e5f-b20f-4052b0c6a4f6)



### Message Dialog Fragment

This dialog fragment use to show message with image (GIF/PNG/JPG/....) .

#### Usage 

**1-** you can use small image by change image type to `StaticString.SMALL`

![Screenshot_small](https://github.com/mohammadalmomanii/MoDev/assets/91605807/fce83fe7-45b9-498b-b3bb-7766d3a92627)

**2-** you can use small image by change image type to `StaticString.BIG`

![Screenshot_20240123-110500](https://github.com/mohammadalmomanii/MoDev/assets/91605807/af763fcf-df5c-4ebe-bc34-b1ad568a108b)


```java

                        MessageDialogFragment.builder()
                                .setImage(requireActivity().getDrawable(R.drawable.gif_like), StaticString.SMALL)
                                .setMessage(getString(R.string.thanks_for_your_rating))
                                .setCancelableFlag(true)
                                .build(getParentFragmentManager(), "");
```






### Preloder 

The loading fragment simplifies the integration of loading indicators within your app, enhancing the user experience during asynchronous operations
and add this code **`app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior"`**

#### Usage 
**1-** should you add this code in your activity or fragment 
```xml
<?xml version="1.0" encoding="utf-8"?>
  <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.mohammadalmomani.modevlib.support.SwipeRefreshView
            android:id="@+id/refresh_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:gifSrc="@drawable/gif"/>

    <your layout
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:orientation="horizontal"
        app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior">
	.
	.
	.
	.
    </your layout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

**2-** you can use it in java like this : 
```java

       RefrishFragment.newInstance((AppCompatActivity) getActivity(), binding.appBarLayout, binding.imageView)
                .startShow(getChildFragmentManager()).setGif(R.drawable.gif_pre_loader);

        AppHelper.delay(()->RefrishFragment.showLoading(),1000);
        AppHelper.delay(()->RefrishFragment.hideLoading(),10000);
```

![screen-20240108-090414~2](https://github.com/mohammadalmomanii/MoDev/assets/91605807/46499325-2eaa-4208-8c67-d24987fb67bd)



### Loading Fragment

The loading fragment simplifies the integration of loading indicators within your app, enhancing the user experience during asynchronous operations.

```java
 LoadingFragment.newInstance(R.drawble.gif,false,5000).show(fragmentActivity.getSupportFragmentManager(), "");
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

**1-** `create a fragment` :
```java
           
            public class Fragment extends TopSheetDialogFragment {
		.
		.
		.
            }
                      
```
**2-** `call your fragment` :
```java
            Fragment.newInstance().show(getSupportFragmentManager(),TAG);
```

### Image Viewer
The image viewer in this library is designed to be easy to use and visually attractive, as it can be used to display images , gif and drawable. Here's a preview

#### Usage 

 Make your fragment extend TopSheetDialogFragment like this

**2-** `java` :
```java
           
         List<Object> list=new ArrayList<>();
        list.add(getDrawable(com.mohammadalmomani.modevlib.R.drawable.img_1));
        list.add(getDrawable(com.mohammadalmomani.modevlib.R.drawable.img_2));
        list.add(getDrawable(com.mohammadalmomani.modevlib.R.drawable.img_3));
        list.add(getDrawable(com.mohammadalmomani.modevlib.R.drawable.img_4));
        list.add(getDrawable(com.mohammadalmomani.modevlib.R.drawable.img_5));
        list.add(getDrawable(com.mohammadalmomani.modevlib.R.drawable.img_6));
        list.add(getDrawable(com.mohammadalmomani.modevlib.R.drawable.img_7));

        ImageViewerFragment.newInstance(`list`).show(getSupportFragmentManager(),"");
   
                      
```
**2-** `Manifest` :
```Manifest
  <application
       ...
       ...
       ...
       >
        <activity
            ...
	    ...
            android:screenOrientation="portrait">
           
        </activity>
    </application>
```

![screen-20231226-080239](https://github.com/mohammadalmomanii/MoDev/assets/91605807/c262dab9-1992-49d4-9a3f-1f0ed5854e2f)






