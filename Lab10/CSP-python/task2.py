from pycsp import *
from random import random
from collections import defaultdict

order = defaultdict(lambda: "th", key="th")
order[1] = "st"
order[2] = "nd"
producer_counter = 0
number_of_buffor_processes = 10
number_of_products_to_create_per_producer = 100


@process
def producer(buffor_out, number_of_products):
    global producer_counter
    for i in range(number_of_products):
        buffor_out(i+1)
        producer_counter += 1
        print(f"Producer created a product({i+1}).")
    retire(buffor_out)


@process
def buffor(buffor_in, buffor_out, number):
    global number_of_buffor_processes
    try:
        while True:
            print(f"Buffor({number}) awaits for {'producer' if number - 1 == 0 else f'buffor number {number-1}'}")
            product = buffor_in()  # Get product from producer or prev buffor cell
            print(f"Buffor({number}) sends to {'consumer' if number + 1 == number_of_buffor_processes else f'buffor number {number+1}'}")
            buffor_out(product)  # Forward product to consumer or next buffor cell
    except ChannelRetireException:
        retire(buffor_in)
        retire(buffor_out)


@process
def consumer(buffor_out):
    global producer_counter
    i = 0
    try:
        while True:
            product = buffor_out()
            i += 1
            print(f"Consumer eats {i}{order[i % 10]} product.")
    except ChannelRetireException:
        print(f"Consumer ate overall {i} products.")  # We are done - print result
        print(f"Producer created overall {producer_counter} products.")



buffor_channels = [Channel() for _ in range(number_of_buffor_processes)]
buffor_procesess = []

for i, channel in enumerate(buffor_channels[:-1]):
    buffor_procesess.append(buffor(channel.reader(), buffor_channels[i+1].writer(), i+1))

Parallel(
    producer(buffor_channels[0].writer(), number_of_products_to_create_per_producer),
    buffor_procesess,
    consumer(buffor_channels[-1].reader()))

shutdown()
