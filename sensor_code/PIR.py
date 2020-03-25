#Code for PIR sensor to detect if someone is in front of the mirror.

import RPi.GPIO as GPIO
import time

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(11, GPIO.IN)         #Read output from PIR motion sensor on pin 11
GPIO.setup(3, GPIO.OUT)         #LED output pin 3, might not need for actual project but good for testing

while True:                     #Forever
    i=GPIO.input(11)                #Read from pin 11
    if i==1:                        #When output from motion sensor is LOW
        print "Somebody in front of sensor",i