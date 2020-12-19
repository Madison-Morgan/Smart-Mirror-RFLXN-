var MONTH_NAME = [
  "January",
  "Febuary",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December"
];
var DAY_NAME = [
  "Sunday",
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday"
];

function showTime() {
  var now = new Date();
  var date =
    DAY_NAME[now.getDay()] +
    ", " +
    MONTH_NAME[now.getMonth()] +
    " " +
    now.getDate() +
    ", " +
    now.getFullYear();

  var hours = now.getHours();
  var minutes = now.getMinutes();
  var seconds = now.getSeconds();
  var ampm = hours >= 12 ? "PM" : "AM";
  hours = hours % 12;
  hours = hours ? hours : 12; // the hour '0' should be '12'
  minutes = minutes < 10 ? "0" + minutes : minutes;
  seconds = seconds < 10 ? "0" + seconds : seconds;
  var time = hours + ":" + minutes + ":" + seconds + " " + ampm;

  if (document.getElementById("date-container") != null) {
    document.getElementById("date-container").innerHTML = date;
    document.getElementById("time-container").innerHTML = time;
  } else {
    var dateDiv = document.createElement("div");
    dateDiv.setAttribute("id", "date-container");
    dateDiv.className = "normal medium";
    var dateValue = document.createTextNode(date);
    dateDiv.appendChild(dateValue);

    var timeDiv = document.createElement("div");
    timeDiv.setAttribute("id", "time-container");
    timeDiv.className = "bright large light";
    var timeValue = document.createTextNode(time);
    timeDiv.appendChild(timeValue);

    document.getElementById("dateAndTime").appendChild(dateDiv);
    document.getElementById("dateAndTime").appendChild(timeDiv);
  }
}

setInterval(showTime, 1000);
