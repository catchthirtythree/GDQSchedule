# GDQSchedule
## GDQ Schedule for Android
Scrapes the GDQ Schedule page (https://gamesdonequick.com/schedule) and makes an okay looking table out of it.

## Bugs:
- ~~Doesn't work offline (OutOfMemory errors during SplashActivity)~~
- Sometimes reverses the order of the run lists (reproduceable without a network connection by refreshing a fragment)

## To-do:
- General run info table tweaks
- Run reminder (alarm)
- Settings page
- Links to relevant websites
- Links to twitch vods?
- ~~Remove the lazy truncate table and find a better way to update the table~~
- Searching
- Better font for action bar
- App/action bar styling

## Installation
1. [Download the APK](https://github.com/catchthirtythree/GDQSchedule/releases/tag/v1.0) on your device.
2. Locate your downloaded files.
3. Allow unknown sources in Security ("Allow this installation only" recommended).
4. Install the app.
5. Have an internet connection to acquire the initial list.
6. Enjoy GDQ for as long as it goes on for.

## Screenshots
![Previous Runs Fragment](http://i.imgur.com/ppY1Jt9.png "Previous Runs Fragment")
![Upcoming Runs Fragment + PullToRefresh](http://i.imgur.com/IAVIewY.png "Upcoming Runs Fragment + PullToRefresh")
