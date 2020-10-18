# Code for PIR sensor to detect if someone is in front of the mirror.

import RPi.GPIO as GPIO
from time import sleep
import datetime
from firebase import firebase
import Adafruit_DHT

import urllib2, urllib, httplib
import json
import os 
from functools import partial

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(11, GPIO.IN)         #Read output from PIR motion sensor on pin 11
GPIO.setup(3, GPIO.OUT)         #LED output pin 3, might not need for actual project but good for testing

def update_firebase():

	motion = GPIO.input(11) # Read from pin 11
	if motion==1 # When output from motion sensor is LOW
		sleep(5)
		print('Motion={}'.format(motion))	
			
	else:
		print('Failed to get reading. Try again!')	
		sleep(10)

	data = {"motion" : motion}
	firebase.post('/sensor/pir', data)
	

while True:
		update_firebase()
		sleep(5)