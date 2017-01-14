<p align="center">
<img src="https://www.cdn.customerly.io/assets/img/Logo_Customerly_Name_Colored.svg">
</p>


 
  [![Language](https://img.shields.io/badge/Android-9+-green.svg)]()
  [![Language](https://img.shields.io/badge/Java-6+-red.svg)]()
  
**customerly.io** is the perfect tool to getting closer to your customers. Help them where they are with the customer support widget. Manage your audience based on their behaviours, build campaigns and automations.

Deliver Surveys directly into your app and get the responses in one place. Study your Net Promote Score and Skyrocket your Online Business right now.

The Customerly Android SDK is really simple to integrate in your apps, and allow your users to contact you via chat.

## Features

- [x] Register your users
- [x] Set attributes
- [x] Track events
- [x] Support via chat in real time
- [x] Surveys
- [x] English & Italian localizations
- [x] Many more is coming....

## Requirements

- Android 2.3+ (API level 9+)
- Android Studio 2.0+
- Java 6+

## Integration via gradle dependency (Recommended)

To use the Customerly SDK we recommend to use Gradle Dependency

To integrate the Customerly SDK into your Android Studio project using Gradle dependency, open your module `build.gradle` file and add the following dependency:


```gradle
repositories {//When the repository will be hosted on jcenter this won't be necessary anymore
    maven {
        url 'https://dl.bintray.com/giannign1/maven/'
    }
}

dependencies {
    compile 'io.customerly:customerly-android-sdk:ALPHA-0.9.2'
}
```

## Manually (Discouraged)

We suggest to avoid manually integration of the SDK in your project, by the way it is still possible:

1. Download the `/customerly-android-sdk` folder and add it as module in your project.  
2. Congratulations!  

We recommend to use only the public methods of the class Customerly.

### Configuration

**1)** Create a new AndroidStudio project or open an existing one

**2)** If you already have defined a custom Application class in your project you can skip to step 3).  
Create a class that extends the default android Application class
```java
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //See step 3)
    }
}
```
Don't forget to declare this class in your `AndroidManifest.xml` file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="YOUR.PACKAGE">

    <application
        android:name=".CustomApplication" >
        //...
    </application>
</manifest>
```

**3)** Now you need to configure the Customerly SDK in your custom Application class onCreate method:

```java
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Customerly.with(this).configure("YOUR_CUSTOMERLY_SECRET_KEY");
    }
}
```
If you want to specify a custom widget_color for the application ignoring the server-defined one you have to specify it in the configure method:
```java
Customerly.with(this).configure("YOUR_CUSTOMERLY_SECRET_KEY", Color.RED);
```
Optionally, if you want to enable the logging in console you have to call the following method. Our suggest is to call it soon after the configure:
```java
Customerly.with(this).setVerboseLogging(BuildConfig.DEBUG);//Passing BuildConfig.DEBUG, logging will be automatically disabled for the release apk
```

**If in doubt, you can look at the examples in the demo application. (See at https://github.com/customerly/Customerly-Android-SDK-demo/blob/master/app/src/main/java/io/customerly/demo/CustomApplication.java)**

### User registration

You can register logged in users of your app into Customerly calling the method `registerUser:`. You’ll also need to register your user anywhere they log in.

Example:

```java
Customerly.with(Context.this).registerUser("axlrose@example.com", "Opt.UserID es 12345", name: "Opt.Name es Gianni");
```

or using a closure:

```java
Customerly.with(Context.this).registerUser("axlrose@example.com", "Opt.UserID es 12345", name: "Opt.Name es Gianni",
 new Customerly.Callback.Success() {
     @Override
     public void onSuccess(boolean newSurvey, boolean newMessage) {
        //...
     }
 }, new Customerly.Callback.Failure() {
         @Override
         public void onFailure() {
            //...
         }
     });
     
//Java8:
Customerly.with(Context.this).registerUser("axlrose@example.com", "Opt.UserID es 12345", name: "Opt.Name es Gianni",
 (newSurvey, newMessage) -> { /* ... */ }, () -> { /* ... */ });
```

You can pass custom attribute for the user as JSONObject. The JSONObject cannot contain other JSONObject or JSONArray:
```java
Customerly.with(Context.this).registerUser("axlrose@example.com", "Opt.UserID es 12345", name: "Opt.Name es Gianni",
new JSONObject().putString("attrKey", "attrValue"));
```

You can also logout users:

```java
Customerly.with(Context.this).logoutUser()
```

In this method, *user_id*, *name*, *attributes*, *success* and *failure* are optionals.

If you don't have a login method inside your apps don't worry, users can use the chat using their emails.

###Chat

You can open the support view controller calling the method `openSupport`:

```java
Customerly.with(Context.this).openSupport(Activity.this)
```

If you need to know in your app when a new message is coming, you can register the *realTimeMessages:* handler

```java
Customerly.with(Context.this).realTimeMessages(new Customerly.RealTimeMessagesListener() {
            @Override
            public void onMessage(Customerly.HtmlMessage messageContent) {
                //messageContent is a `SpannableStringBuilder` containing the message with the html-formatting
                //messageContent.toPlainTextString() returns the message in plain text
            }});
      
//Java8:
Customerly.with(Context.this).realTimeMessages((htmlMessage) -> { /* ... */ });
```

If you want to get a generic update and know about presence of any Survey or unread message, call `update`:

```java
Customerly.with(Context.this).update(new Customerly.Callback.Success() {
                                          @Override
                                          public void onSuccess(boolean newSurvey, boolean newMessage) {
                                             //...
                                          }
                                      }, new Customerly.Callback.Failure() {
                                              @Override
                                              public void onFailure() {
                                                 //...
                                              }
                                          });
//JAVA8:
Customerly.with(Context.this).update( (newSurvey, newMessage) -> { /* ... */ }, () -> { /* ... */ } );
```

###Surveys

With the Customerly SDK you can deliver surveys directly into your app.

You can present a survey in a dialogfragment from your activity support FragmentManager like this:

```java
if Customerly.with(Context.this).isSurveyAvailable(){
    Customerly.with(Context.this).openSurvey(Activity.this.getSupportFragmentManager(),
    new Customerly.SurveyListener.OnShow() {
        @Override
        public void onShow() {
            //Called if and when the Survey is actually showed
        }
    }, new Customerly.SurveyListener.OnDismiss() {
        @Override
        public void onDismiss(int pDismissMode) {
            //Called if the Survey has been disposed, the parameter pDismissMode indicates the state of the survey
            switch(pDismissMode) {
                case Customerly.SurveyListener.OnDismiss.COMPLETED:
                    //The Survey has been disposed after the completion
                    break;
                case Customerly.SurveyListener.OnDismiss.REJECTED:
                    //The Survey has been rejected
                    break;
                case Customerly.SurveyListener.OnDismiss.POSTPONED:
                default:
                    //The Survey dialog has been disposed but the survey is still available and can be continued with a new openSurvey call
                    break;
            }
        }
    });
}
     
//JAVA8:        
Customerly.with(Context.this).openSurvey(Activity.this.getSupportFragmentManager(),
    () -> { /* ... */ }, (pDismissMode) -> { /* ... */ });
```

The SurveyListener are totally optional, you can call `openSurvey` like this if you don't need them:

```java
Customerly.with(Context.this).openSurvey(Activity.this.getSupportFragmentManager())
```
Remember that you can get updates about new surveys available using the `update` method.

###Attributes

Inside attributes you can add every custom data you prefer to track. you can pass a JSONObject containing more attributes but no sub JSONObjects or JSONArray.

```java
// Eg. This attribute define what kind of pricing plan the user has purchased 
Customerly.with(Context.this).setAttributes(new JSONObject().putString("pricing_plan_type", "basic"));
```

###Events

Send to Customerly every event you want to segment users better

```java
// Eg. This send an event that track a potential purchase
Customerly.with(Context.this).trackEvent("added_to_cart")
```


## Contributing

- If you **need help** or you'd like to **ask a general question**, open an issue or contact our support on [Customerly.io](https://www.customerly.io)
- If you **found a bug**, open an issue.
- If you **have a feature request**, open an issue.
- If you **want to contribute**, submit a pull request.


## Acknowledgements

Made with ❤️ by [Gianni Genovesi](https://www.linkedin.com/in/ggenovesi/) for Customerly.


## License

Customerly Android SDK is available under the XXX license. See the LICENSE file for more info.