# Weather
This application uses the openweather api and displays the weather in Celcius. 
#MainActivity
Contains the input field where the input for entering the location of which we want to know the weather is given. On submitting
the details of the location then the location is sent on to the next Activity as String where the location is added to the URL of openweather.
#Weather
Gets the location sent as input by the user and initiates the process in background by getting all the details required. This uses the HandleJSON class for getting the values.
#HandleJSON
Contains the methods where the the data stored is converted using json objects and strings
