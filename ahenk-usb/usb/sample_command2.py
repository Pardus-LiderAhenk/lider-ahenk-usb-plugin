#!/usr/bin/python3
# -*- coding: utf-8 -*-

def handle_task(task, context):
    # Do what ever you want here
    # You can create command class but it is not necessary
    # You can use directly this method.
    context.put('my_data_name', 'my data')
