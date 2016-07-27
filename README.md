# EventRobot
Android client for an Microsoft Graph "bot" that gets events from your O365 and Microsoft calendars

## Start EventActivity event using ADB

After the user signs in to the app and authenticates against the V2 endpoint, auth credentials are cached. The app can go to sleep at 
this point. 

To mock the Google Now voice prompt, execute the following syntax in the terminal: 

adb shell am start -a com.google.android.gms.actions.SEARCH_ACTION -e query MyNextMeeting com.microsoft.office.Even
tRobot
