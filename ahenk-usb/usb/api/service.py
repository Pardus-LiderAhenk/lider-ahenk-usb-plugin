#!/usr/bin/python
# -*- coding: utf-8 -*-

"""
Style Guide is PEP-8
https://www.python.org/dev/peps/pep-0008/
"""

"""
Some operations of plugin work with using different command (or process) on different machine types,
 desktop environments, kernel versions, etc, ...
You have to create python classes which includes implementation of every action, for every dependent feature of system.
These classes have to be in api folder. You can create and serve instances of environment dependent operations to your
plugin from here. Ahenk Core provides necessary values of system like machine type, kernel version,
desktop environment,...
Naming of classes expected such as below examples:
<featureA1_featureB1.py>

Kde_Ltsp.py
Gnome_Ltsp.py
Kde_X2go.py
Gnome_X2go.py
Default_Ltsp.py
Default_X2go.py
Kde_default.py
Gnome_default.py
Default_default.py
Default_default.py
(It depends to number of kind of variation)

You can access some useful values using these statements:
SystemInfo.get_X(),SystemInfo.get_kernel(),SystemInfo.get_machine_type(),...
When you create valid instance according to running system environments, plugin works with valid actions.
"""