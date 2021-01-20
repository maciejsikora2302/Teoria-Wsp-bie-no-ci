from pycsp import *
from random import random
from collections import defaultdict


order = defaultdict(lambda: "th", key="th")
order[1] = "st"
order[2] = "nd"
producer_counter = 0

@process
def producer(buffor_out, number_of_products):
   global producer_counter
   for i in range(number_of_products):
     buffor_out(1)
     producer_counter += 1
     print(f"Producer created a product.")
   retire(buffor_out)

@process
def buffor(buffor_in, buffor_out):
   while True:
       product=buffor_in()           #Get product from producer
       buffor_out(product)  #Forward product to consumer


@process
def consumer(buffor_out):
   global producer_counter
   i = 0
   try:
       while True:
           product = buffor_out()
           i += 1
           print(f"Consumer eats {i}{order[i%10]} product.")
   except ChannelRetireException:
       print(f"Consumer ate overall {i} products.")            #We are done - print result
       print(f"Producer created overall {producer_counter} products.")

buffor_producer_channel=Channel()
buffor_consumer_channel=Channel()

number_of_buffor_processes = 10

Parallel(
   producer( buffor_producer_channel.writer() , 50),
   number_of_buffor_processes * buffor( buffor_producer_channel.reader() ,buffor_consumer_channel.writer()),
   consumer(buffor_consumer_channel.reader()))

shutdown()