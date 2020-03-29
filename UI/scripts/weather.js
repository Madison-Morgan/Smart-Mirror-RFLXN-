

function showWeather(){

    function displayWeather(data){
        var temperature = Math.round(parseFloat(data.main.temp)-273.15) + ' &#176 C';
        var description = data.weather[0].description;
        var location = data.name;
        var feelsLike = "Feels like " + Math.round(parseFloat(data.main.feels_like)-273.15) + ' &#176 C';
        var weatherIcon = null;

        if( description.indexOf('rain') > 0 ) {
            weatherIcon = "wi wi-day-rain";
        } else if( description.indexOf('cloud') > 0 ) {
            weatherIcon = "wi wi-day-cloudy";
        } else if( description.indexOf('sunny') > 0 ) {
            weatherIcon = "wi wi-day-sunny";
        }

        if(document.getElementById("location-container")!=null){
            document.getElementById("location-container").innerHTML=location;
            document.getElementById("weather-icon").className = weatherIcon;
            document.getElementById("temp-container").innerHTML= temperature;
            document.getElementById("feels_like-container").innerHTML = feelsLike;
        }
        else{
            var locationDiv = document.createElement('div');
            locationDiv.setAttribute("id", "location-container");
            locationDiv.className = "normal medium";
            var locationValue = document.createTextNode(location);
            locationDiv.appendChild(locationValue); 
      
            var horizontalLine = document.createElement('hr');
            horizontalLine.setAttribute("id", "horizontal-line");
            horizontalLine.className = "normal medium";

            var weatherDiv = document.createElement("i");
            weatherDiv.setAttribute("id", "weather-icon");
            weatherDiv.setAttribute("style", "font-size:60px;color:white");
            weatherDiv.className = weatherIcon;

            var tempDiv = document.createElement("div");
            tempDiv.setAttribute("id", "temp-container");
            tempDiv.className = "bright large light";
            var tempValue = document.createTextNode(temperature); 
            tempDiv.appendChild(weatherDiv);
            tempDiv.appendChild(tempValue); 

            
            var feelsDiv = document.createElement('div');
            feelsDiv.setAttribute("id", "feels_like-container");
            feelsDiv.className = "normal medium";
            var feelsValue = document.createTextNode(feelsLike);
            feelsDiv.appendChild(feelsValue);
     
            document.getElementById("weather").appendChild(locationDiv);  
            document.getElementById("weather").appendChild(horizontalLine);
            document.getElementById("weather").appendChild(tempDiv);
            document.getElementById("weather").appendChild(feelsDiv);
            
        }


    }


    var location = 6094817//"Ottawa";
    var key = "d282ff0e0f7c30f44d59d9d71eab1743";

    fetch("https://api.openweathermap.org/data/2.5/weather?id="+location+"&appid="+key)
    .then(function(resp) {return resp.json() }) //convert data to json
    .then(function(data){
        console.log(data);
        displayWeather(data);
    })
    .catch(function() {
        //catch any errors
    });



};

setInterval(showWeather, 1000);