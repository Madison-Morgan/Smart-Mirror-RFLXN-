# Code to measure ambient temperature

import RPi.GPIO as GPIO
from time import sleep
import datetime
from firebase import firebase
import Adafruit_DHT

import urllib2, urllib, httplib
import json
import os 
from functools import partial

GPIO.setmode(GPIO.BCM)
GPIO.cleanup()
GPIO.setwarnings(False)

# Sensor is Adafruit_DHT.DHT11,
sensor = Adafruit_DHT.DHT11

# connected to pin pi pin 16 which is GPIO23.
pin = 23

# Try to grab a sensor reading.  Use the read_retry method which will retry up
# to 15 times to get a sensor reading (waiting 2 seconds between each retry).

humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)


firebase = firebase.FirebaseApplication('https://rflxn-8e183.firebaseio.com/', None)

def update_firebase():

	humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
	if humidity is not None and temperature is not None:
		sleep(5)
		str_temp = ' {0:0.2f} *C '.format(temperature)	
		str_hum  = ' {0:0.2f} %'.format(humidity)
		print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))	
			
	else:
		print('Failed to get reading. Try again!')	
		sleep(10)

	data = {"temp": temperature, "humidity": humidity}
	firebase.post('/sensor/dht', data)
	

while True:
		update_firebase()
		
        #sleepTime = int(sleepTime)
		sleep(5)