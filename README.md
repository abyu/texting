Texting
=========
[![Build Status](https://travis-ci.org/abyu/texting.png)](https://travis-ci.org/abyu/texting)

Texting android app built on Sony Small Apps SDK.

Small app to view your text messages. Useful when you want to quickly refer your messages.

Tools used:
---------------

1. IntelliJIdea Community edition(v~13)
2. Gradle v1.10
3. Sony small apps sdk 2.1

Command line Build Targets:
----------------------------

- `gradle build` will compile, test and generate apks
- use `gradle tasks` to get a list of available targets

Creating a Signed APK
----------------------

1. Create a gradle properties file with the following properties
    - RELEASE_KEYSTORE_FILE='path to your keystore'
    - RELEASE_KEYSTORE_PASSWORD='your keystore password'
    - RELEASE_KEY_ALIAS='your key alias'
    - RELEASE_KEY_PASSWORD='your key password'
2. Run `gradle build` or `gradle assembleSigned`
3. The signed apk is available in build/apk/Texting-Signed.apk
