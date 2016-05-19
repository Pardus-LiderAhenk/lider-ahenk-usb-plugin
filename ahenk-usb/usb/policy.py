#!/usr/bin/python
# -*- coding: utf-8 -*-

"""
Style Guide is PEP-8
https://www.python.org/dev/peps/pep-0008/
"""

from base.plugin.AbstractCommand import AbstractCommand


class PluginName(AbstractCommand):
    def __init__(self, data, context):
        super(PluginName, self).__init__()
        self.data = data
        self.context = context

    def handle_policy(self):
        print('Handling policy')
        #self.context.put('name', 'anything')
        #self.context.get('something')
        # TODO plugin policy works here


def handle_policy(profile_data, context):
        # Do what ever you want here
    # You can create command class but it is not necessary
    # You can use directly this method.
    plugin = PluginName(profile_data, context)
    plugin.handle_policy()
