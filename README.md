# AnimatedFloatBottomNav
A beautiful animated bottom nav for compose


# Demo
![libpreview](https://user-images.githubusercontent.com/46859947/219660373-1c137d9f-7d78-4ed1-9690-c32b06d6ce58.gif)

# Usage

Step1

add maven { url 'https://jitpack.io' } to 

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }

in setting.gradle

or
 in allprojects{} your root build.gradle at the end of repositories
 

		repositories {
			...
			maven { url 'https://jitpack.io' }
		}


Step2

[![](https://jitpack.io/v/sahar-android/AnimatedFloatBottomNav.svg)](https://jitpack.io/#sahar-android/AnimatedFloatBottomNav)

add dependency

implementation 'com.github.sahar-android:AnimatedFloatBottomNav:1.0.0'

step3

in your compposable You can add any number of icons and tabs to the navigation by create list of title string and icon int


    val titleList = listOf("title1", "title2", "title3")

    val iconList = listOf(R.drawable.guests, R.drawable.traffic, R.drawable.settings)
    var s by remember { mutableStateOf("") }
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Text(
                    text = s,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 150.dp),
                )
            }
        },
        bottomBar = {
            SimyappBottomNav.MyNav(titleList, iconList, Color.Red) {

                when (it) {
                    0 -> s = "title1"
                    1 -> s = "title2"
                    2 -> s = "title3"

                }
            }
        }
    )

##about lib and me:

This is an early version of a library built for bottom navigation in compose project. I'm trying to make it in that way to everyone can use it easily. You just need to add icon and title.
I want to add some features and improve it so if you have any problems, ideas or criticisms I would really appritiate it if you leave me a comment :)
email: sahar.simyari@gmail.com


