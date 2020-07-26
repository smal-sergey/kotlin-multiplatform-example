# kotlin-multiplatform-example

## Project structure
Project consists of 3 gradle sub-projects:
1. `app-server` - purely server side code. Kotlin compiled into JVM bytecode. Uses Ktor HTTP engine.
2. `app-client` - purely client side code. Kotlin compiled into JS. Uses React and Kotlin wrappers for it.
3. `app-common` - kotlin-multiplatform module. It has common data classes to be used in both `app-server` and `app-client` 
modules and thus needs to be compiled for both platforms. Just for fun I also added platform specific code 
though it is actually not required for the demo.

## What I have found

#### React in Kotlin
The simplest way is just to create separate Kotlin/JS sub-project, apply Kotlin JS plugin, 
specify compile target and add plain or npm dependencies.

Some useful links:
* [React in Kotlin/JS playground](https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction?_ga=2.12371764.1242346169.1594922809-1846489601.1584994797)
* [React in Kotlin/JS pitfalls](https://discuss.kotlinlang.org/t/react-in-kotlin-js-what-i-learned-long-but-useful-read/16168/15)

#### Gradle modules and dependencies
To import data classes from common multiplatform Gradle sub-project (`app-common`) into Korlin/JS sub-project (`app-client-web`) 
two things needs to be done:
1. In `app-client-web` add dependency to `app-common`:  
```implementation(project(":app-common"))```
2.  In `app-common`-s js target specify JS module
(see [Kotlin JS Modules](https://kotlinlang.org/docs/reference/js-modules.html)) to be used during compilation 
and platform into which to compile common Kotlin code:
```kotlin
kotlin {
    js {
         useCommonJs()
         browser()
     }
    //... other stuff    
}
```
